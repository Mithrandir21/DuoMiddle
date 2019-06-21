package com.duopoints.service

import com.duopoints.RequestParameters
import com.duopoints.db.tables.pojos.FriendRequest
import com.duopoints.db.tables.pojos.FriendRights
import com.duopoints.db.tables.pojos.Friendship
import com.duopoints.db.tables.records.FriendRequestRecord
import com.duopoints.errorhandling.ConflictException
import com.duopoints.errorhandling.NoMatchingRowException
import com.duopoints.models.composites.CompositeFriendship
import com.duopoints.models.composites.CompositeFriendshipRequest
import com.duopoints.models.posts.NewFriendshipRequest
import org.jooq.Condition
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.validation.constraints.NotNull
import java.util.ArrayList
import java.util.UUID
import java.util.stream.Collectors
import java.util.stream.Stream

import com.duopoints.db.tables.FriendRequest.FRIEND_REQUEST
import com.duopoints.db.tables.FriendRights.FRIEND_RIGHTS
import com.duopoints.db.tables.Friendship.FRIENDSHIP

@Service
class FriendService {

    @Autowired
    @Qualifier("dsl")
    lateinit var duo: DefaultDSLContext

    @Autowired
    lateinit var userService: UserService


    /*****************
     * FRIEND REQUEST
     *****************/

    fun getFriendRequest(friendRequestID: UUID): FriendRequest? =
            duo.selectFrom(FRIEND_REQUEST).where(FRIEND_REQUEST.FRIEND_REQUEST_UUID.eq(friendRequestID)).fetchOneInto(FriendRequest::class.java)

    fun getCompositeFriendRequest(friendRequestID: UUID): CompositeFriendshipRequest =
            getFriendRequest(friendRequestID)?.let { getCompositeFriendRequest(it) }
                    ?: throw NoMatchingRowException("No FriendRequest found for friendRequestID='$friendRequestID'")

    fun getCompositeFriendRequest(req: FriendRequest): CompositeFriendshipRequest =
            CompositeFriendshipRequest(req, userService.getUser(req.friendRequestSenderUserUuid), userService.getUser(req.friendRequestRecipientUserUuid))

    fun getAllActiveCompositeFriendRequests(userID: UUID): List<CompositeFriendshipRequest> {
        val userFriendRequest = duo.selectFrom(FRIEND_REQUEST)
                .where(FRIEND_REQUEST.FRIEND_REQUEST_RECIPIENT_USER_UUID.eq(userID))
                .or(FRIEND_REQUEST.FRIEND_REQUEST_SENDER_USER_UUID.eq(userID))
                .and(FRIEND_REQUEST.FRIEND_REQUEST_STATUS.eq(RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient))
                .fetchInto(FriendRequest::class.java)

        val userCompositeFriendshipRequest = ArrayList<CompositeFriendshipRequest>()

        for (singleFriendRequest in userFriendRequest) {
            userCompositeFriendshipRequest.add(getCompositeFriendRequest(singleFriendRequest))
        }

        return userCompositeFriendshipRequest
    }

    fun createCompositeFriendRequest(newFriendshipRequest: NewFriendshipRequest): CompositeFriendshipRequest {
        // First check if there already exists a friend request for the given Sender and Recipient that is not WAITING_FOR_RECIPIENT
        val friendrequest = duo.selectFrom(FRIEND_REQUEST)
                .where(FRIEND_REQUEST.FRIEND_REQUEST_SENDER_USER_UUID.eq(newFriendshipRequest.requestSenderID),
                        FRIEND_REQUEST.FRIEND_REQUEST_RECIPIENT_USER_UUID.eq(newFriendshipRequest.requestRecipientID))
                .and(FRIEND_REQUEST.FRIEND_REQUEST_STATUS.eq(RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient))
                .fetchOneInto(FriendRequest::class.java)

        if (friendrequest != null) {
            throw ConflictException("A friend request already exists with the status:" + friendrequest.friendRequestStatus)
        }

        val friendRequest = duo.insertInto(FRIEND_REQUEST)
                .columns(FRIEND_REQUEST.FRIEND_REQUEST_SENDER_USER_UUID, FRIEND_REQUEST.FRIEND_REQUEST_RECIPIENT_USER_UUID,
                        FRIEND_REQUEST.FRIEND_REQUEST_COMMENT, FRIEND_REQUEST.FRIEND_REQUEST_STATUS)
                .values(newFriendshipRequest.requestSenderID, newFriendshipRequest.requestRecipientID, newFriendshipRequest.requestComment,
                        RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient)
                .returning()
                .fetchOne()
                .into(FriendRequest::class.java)

        return CompositeFriendshipRequest(friendRequest, userService.getUser(friendRequest.friendRequestSenderUserUuid), userService.getUser(friendRequest.friendRequestRecipientUserUuid))
    }

    @Transactional
    fun setFinalCompositeFriendRequestStatus(requestID: UUID, status: String): CompositeFriendshipRequest {
        // First we retrieve the Request
        val friendrequest = duo.selectFrom(FRIEND_REQUEST)
                .where(FRIEND_REQUEST.FRIEND_REQUEST_UUID.eq(requestID)).fetchOneInto(FriendRequest::class.java)
                ?: throw NoMatchingRowException("No FriendRequest found matching requestID='$requestID'")

        // If the Request is anything other than REQUESTED, fail.
        if (!friendrequest.friendRequestStatus.equals(RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient, ignoreCase = true)) {
            throw ConflictException("RelationshipRequest has status:'" + friendrequest.friendRequestStatus + "'")
        }

        if (status.equals(RequestParameters.FRIEND_REQUEST_friend_request_status_completed, ignoreCase = true)) {
            if (createFriend(friendrequest.friendRequestSenderUserUuid, friendrequest.friendRequestRecipientUserUuid) == null) {
                throw NoMatchingRowException("No Friendship created! Error!")
            }
        }

        // Now set the Status of the Request
        val friendrequestRecord = duo.update(FRIEND_REQUEST)
                .set(FRIEND_REQUEST.FRIEND_REQUEST_STATUS, status)
                .where(FRIEND_REQUEST.FRIEND_REQUEST_UUID.eq(requestID))
                .and(FRIEND_REQUEST.FRIEND_REQUEST_STATUS.eq(RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient))
                .returning()
                .fetchOne()

        return if (friendrequestRecord != null) {
            getCompositeFriendRequest(friendrequestRecord.friendRequestUuid)
        } else {
            throw NoMatchingRowException("No FriendRequest found matching requestID='" + requestID + "' having status " + RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient)
        }
    }


    /*****************
     * FRIENDSHIP
     *****************/

    fun getActiveFriendship(userOne: UUID, userTwo: UUID): Friendship? {
        val friends1 = FRIENDSHIP.USER_ONE_UUID.eq(userOne).and(FRIENDSHIP.USER_TWO_UUID.eq(userTwo))
        val friends2 = FRIENDSHIP.USER_ONE_UUID.eq(userTwo).and(FRIENDSHIP.USER_TWO_UUID.eq(userOne))

        return duo.selectFrom(FRIENDSHIP).where(friends1).or(friends2)
                .and(FRIENDSHIP.FRIENDSHIP_STATUS.eq(RequestParameters.FRIEND_friendship_status_active))
                .fetchOneInto(Friendship::class.java)
    }


    fun getActiveCompositeFriendship(userOne: UUID, userTwo: UUID): CompositeFriendship =
            getActiveFriendship(userOne, userTwo)?.let { getActiveCompositeFriendship(it) }
                    ?: throw NoMatchingRowException("No Friendship found for userOne='$userOne' and userTwo='$userTwo'")


    fun getActiveCompositeFriendship(friendship: Friendship): CompositeFriendship =
            CompositeFriendship(friendship, userService.getUser(friendship.userOneUuid), userService.getUser(friendship.userTwoUuid))

    fun getAllActiveFriendships(userID: UUID): List<Friendship> =
            duo.selectFrom(FRIENDSHIP)
                    .where(FRIENDSHIP.USER_ONE_UUID.eq(userID))
                    .or(FRIENDSHIP.USER_TWO_UUID.eq(userID))
                    .and(FRIENDSHIP.FRIENDSHIP_STATUS.eq(RequestParameters.FRIEND_friendship_status_active))
                    .fetchInto(Friendship::class.java)

    fun getAllActiveFriendshipsUUIDs(userID: UUID): List<UUID> {
        val userOneIsFriend = duo.select(FRIENDSHIP.USER_ONE_UUID)
                .from(FRIENDSHIP)
                .where(FRIENDSHIP.USER_TWO_UUID.eq(userID))
                .and(FRIENDSHIP.FRIENDSHIP_STATUS.eq(RequestParameters.FRIEND_friendship_status_active))
                .fetchInto(UUID::class.java)

        val userTwoIsFriend = duo.select(FRIENDSHIP.USER_TWO_UUID)
                .from(FRIENDSHIP)
                .where(FRIENDSHIP.USER_ONE_UUID.eq(userID))
                .and(FRIENDSHIP.FRIENDSHIP_STATUS.eq(RequestParameters.FRIEND_friendship_status_active))
                .fetchInto(UUID::class.java)

        return userOneIsFriend.toMutableList().apply { addAll(userTwoIsFriend) }
    }

    fun getAllActiveCompositeFriendships(userID: UUID): List<CompositeFriendship> {
        val userFriendships = getAllActiveFriendships(userID)

        val userCompositeFriendships = ArrayList<CompositeFriendship>()

        for (singleFriendship in userFriendships) {
            userCompositeFriendships.add(getActiveCompositeFriendship(singleFriendship))
        }

        return userCompositeFriendships
    }

    private fun createFriend(userOne: UUID, userTwo: UUID): CompositeFriendship? {
        // First check if users already friends
        if (getActiveFriendship(userOne, userTwo) != null) {
            throw ConflictException("Friendship already exists between userOne('$userOne') and userTwo('$userTwo')")
        }

        // At this point, we need to create FriendRights for each friend in this relationship
        val rightsOne = duo.insertInto(FRIEND_RIGHTS).defaultValues().returning().fetchOne().into(FriendRights::class.java)
        val rightsTwo = duo.insertInto(FRIEND_RIGHTS).defaultValues().returning().fetchOne().into(FriendRights::class.java)


        val friendship = duo.insertInto(FRIENDSHIP)
                .columns(FRIENDSHIP.USER_ONE_UUID, FRIENDSHIP.USER_TWO_UUID, FRIENDSHIP.FRIEND_ONE_RIGHTS_UUID, FRIENDSHIP.FRIEND_TWO_RIGHTS_UUID, FRIENDSHIP.FRIENDSHIP_STATUS)
                .values(userOne, userTwo, rightsOne.friendRightsUuid, rightsTwo.friendRightsUuid, RequestParameters.FRIEND_friendship_status_active)
                .returning()
                .fetchOne()
                .into(Friendship::class.java)

        return if (friendship == null) null else CompositeFriendship(friendship, userService.getUser(friendship.userOneUuid), userService.getUser(friendship.userTwoUuid))
    }
}