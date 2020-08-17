package com.duopoints.service.fcm

import com.duopoints.db.tables.pojos.PointEventLike
import com.duopoints.db.tables.pojos.Relationship
import com.duopoints.db.tables.pojos.Userdata
import com.duopoints.errorhandling.auth.TokenNoValidException
import com.duopoints.errorhandling.auth.UserTokenNotMatchedException
import com.duopoints.models.auth.UserAuthInfo
import com.duopoints.models.auth.UserAuthWrapper
import com.duopoints.models.composites.*
import com.duopoints.models.messages.SendMessage
import com.duopoints.service.PointService
import com.duopoints.service.RelationshipService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseToken
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.gson.Gson
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.ServerErrorException

import java.util.HashMap
import java.util.logging.Level
import java.util.logging.Logger

@Service
class FcmService(private val settings: FcmSettings) {

    @Autowired
    lateinit var pointService: PointService

    @Autowired
    lateinit var relationshipService: RelationshipService

    @Autowired
    @Qualifier("chatDB")
    lateinit var chatDB: DatabaseReference

    private val webClient: WebClient = WebClient.create()


    /****************
     * DATABASE
     ****************/

    fun sendPointEventPushNotification(event: CompositePointEvent): CompositePointEvent {
        val rel = event.relationship

        // Send to the other user in Relationship
        for (user in listOf(rel.userOne, rel.userTwo)) {
            if (user.userUuid != event.pointGiverUserUuid) { // Send to the other user in Relationship
                val messageData = HashMap<String, Any>()
                messageData[FcmData.NOTIFICATION_TYPE] = FcmData.NEW_POINTS_TYPE
                messageData[FcmData.POINT_EVENT_ID] = event.pointEventUuid.toString()
                messageData[FcmData.POINT_EVENT_TITLE] = event.pointEventTitle
                messageData[FcmData.POINT_SUM] = event.pointEventTotalPoints

                send(user.userAuthId, messageData)
            }
        }

        // TODO - Send Notifications to Friends

        // TODO - Send Notifications to Followers

        return event
    }

    fun sendLikeNotification(like: PointEventLike): PointEventLike {
        // Get the Event liked
        val likedEvent = pointService.getCompositePointEvent(like.pointEventUuid)

        // Send to the other user in Relationship
        for (user in listOf(likedEvent.relationship.userOne, likedEvent.relationship.userTwo)) {
            if (user.userUuid != like.pointEventLikeUserUuid) {
                val messageData = HashMap<String, Any>()
                messageData[FcmData.NOTIFICATION_TYPE] = FcmData.NEW_LIKE_TYPE
                messageData[FcmData.POINT_EVENT_ID] = likedEvent.pointEventUuid.toString()

                send(user.userAuthId, messageData)
            }
        }

        return like
    }

    fun sendFriendRequestNotification(request: CompositeFriendshipRequest): CompositeFriendshipRequest {
        val messageData = HashMap<String, Any>()
        messageData[FcmData.NOTIFICATION_TYPE] = FcmData.NEW_FRIEND_REQUEST_TYPE
        messageData[FcmData.FRIEND_REQUEST_ID] = request.friendRequestUuid.toString()
        messageData[FcmData.FRIEND_REQUEST_SENDER_NAME] = request.senderUser.userFirstname

        send(request.recipientUser.userAuthId, messageData)

        return request
    }

    fun sendRelationshipRequestNotification(request: CompositeRelationshipRequest): CompositeRelationshipRequest {
        val messageData = HashMap<String, Any>()
        messageData[FcmData.NOTIFICATION_TYPE] = FcmData.NEW_RELATIONSHIP_REQUEST_TYPE
        messageData[FcmData.RELATIONSHIP_REQUEST_ID] = request.relationshipRequestUuid.toString()
        messageData[FcmData.RELATIONSHIP_REQUEST_SENDER_NAME] = request.senderUser.userFirstname

        send(request.recipientUser.userAuthId, messageData)

        return request
    }

    fun sendNewRelationshipNotification(compositeRelationshipRequest: CompositeRelationshipRequest): CompositeRelationshipRequest {
        // Get the Active Relationship and send a Notification to both Users
        val senderRelationship = relationshipService.getActiveUserRelationship(compositeRelationshipRequest.senderUser.userUuid)
        val recipientRelationship = relationshipService.getActiveUserRelationship(compositeRelationshipRequest.recipientUser.userUuid)

        if (senderRelationship == null) {
            Logger.getGlobal().log(Level.SEVERE, "Request Sender null! Error!")
        } else if (recipientRelationship == null) {
            Logger.getGlobal().log(Level.SEVERE, "Recipient Sender null! Error!")
        } else if (recipientRelationship.relationshipUuid != senderRelationship.relationshipUuid) {
            Logger.getGlobal().log(Level.SEVERE, "Sender relationship does not match recipient relationship! Error!")
        } else {
            // Send to both users in the Relationship
            for (user in listOf(compositeRelationshipRequest.senderUser, compositeRelationshipRequest.recipientUser)) {
                val messageData = HashMap<String, Any>()
                messageData[FcmData.NOTIFICATION_TYPE] = FcmData.NEW_RELATIONSHIP_TYPE
                messageData[FcmData.NEW_RELATIONSHIP_ID] = senderRelationship.relationshipUuid.toString()
                messageData[FcmData.NEW_RELATIONSHIP_SENDER_NAME] = compositeRelationshipRequest.senderUser.userFirstname


                send(user.userAuthId, messageData)
            }
        }

        return compositeRelationshipRequest
    }

    fun sendRelationshipRequestNegativeNotification(compositeRelationshipRequest: CompositeRelationshipRequest): CompositeRelationshipRequest {
        // Send to both users in the Request
        for (user in listOf(compositeRelationshipRequest.senderUser, compositeRelationshipRequest.recipientUser)) {
            val messageData = HashMap<String, Any>()
            messageData[FcmData.NOTIFICATION_TYPE] = FcmData.RELATIONSHIP_REQUEST_UPDATED_TYPE

            send(user.userAuthId, messageData)
        }

        return compositeRelationshipRequest
    }

    fun sendRelationshipBreakupUpdate(compositeRelationshipBreakupRequest: CompositeRelationshipBreakupRequest): CompositeRelationshipBreakupRequest {
        // Send to both users in the Request
        for (user in listOf(compositeRelationshipBreakupRequest.relationship.userOne, compositeRelationshipBreakupRequest.relationship.userTwo)) {
            val messageData = HashMap<String, Any>()
            messageData[FcmData.NOTIFICATION_TYPE] = FcmData.RELATIONSHIP_BREAKUP_TYPE
            messageData[FcmData.BREAKUP_STATUS] = compositeRelationshipBreakupRequest.relationshipBreakupRequestStatus

            send(user.userAuthId, messageData)
        }

        return compositeRelationshipBreakupRequest
    }


    private fun send(userAuthID: String, data: Map<String, Any>) =
            getUserPushTokenRef(userAuthID).addListenerForSingleValueEvent(object : ConvenientListener() {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.value != null) {
                        val sendMessage = SendMessage()
                        sendMessage.to = dataSnapshot.value as String
                        sendMessage.data = data

                        send(sendMessage)
                    } else {
                        Logger.getGlobal().log(Level.INFO, "PushToken not found for userAuthID:$userAuthID")
                    }
                }
            })

    private fun send(sendMessage: SendMessage) =
            webClient.post()
                    .uri("https://fcm.googleapis.com/fcm/send")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, "key=" + this.settings.apiKey)
                    .syncBody(sendMessage).exchange().subscribe { clientResponse -> println("send: " + clientResponse.statusCode()) }

    private fun getUserPushTokenRef(userAuthID: String): DatabaseReference =
            chatDB.child("prod").child("users").child(userAuthID).child("meta").child("pushToken")


    /****************
     * AUTH
     ****************/

    fun UserAuthInfo(wrapper: UserAuthWrapper): UserAuthInfo {
        val decryptedUserAuthInfo = wrapper.encryptedUserAuthInfo // TODO - Currently unencrypted

        return Gson().fromJson(decryptedUserAuthInfo, UserAuthInfo::class.java)
    }

    fun verifyToken(authToken: String): FirebaseToken =
            try {
                FirebaseAuth.getInstance().verifyIdTokenAsync(authToken, true).get()
            } catch (e: FirebaseAuthException) {
                e.printStackTrace()
                throw TokenNoValidException(e.errorCode, e.message, e.cause)
            } catch (e: Exception) {
                e.printStackTrace()
                throw ServerErrorException(e.message ?: "")
            }

    fun createUserJWT(token: FirebaseToken, userUid: String, userEmail: String): String {
        if (token.uid != userUid) {
            throw UserTokenNotMatchedException("User UID does not match! Error!")
        }

        if (token.email != userEmail) {
            throw UserTokenNotMatchedException("User email does not match! Error!")
        }


        return Jwts.builder()
                .setSubject(userEmail)
                .signWith(SignatureAlgorithm.HS512, "U2VjcmV0")
                .compact()
    }
}