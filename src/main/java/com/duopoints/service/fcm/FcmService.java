package com.duopoints.service.fcm;

import com.duopoints.db.tables.pojos.PointEventLike;
import com.duopoints.db.tables.pojos.Relationship;
import com.duopoints.db.tables.pojos.Userdata;
import com.duopoints.errorhandling.auth.TokenNoValidException;
import com.duopoints.errorhandling.auth.UserTokenNotMatchedException;
import com.duopoints.models.auth.UserAuthInfo;
import com.duopoints.models.auth.UserAuthWrapper;
import com.duopoints.models.composites.*;
import com.duopoints.models.messages.SendMessage;
import com.duopoints.service.PointService;
import com.duopoints.service.RelationshipService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerErrorException;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FcmService {

    @Autowired
    private PointService pointService;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    @Qualifier("chatDB")
    private DatabaseReference chatDB;

    private final WebClient webClient;
    private final FcmSettings settings;

    public FcmService(FcmSettings settings) {
        this.webClient = WebClient.create();
        this.settings = settings;
    }


    /****************
     * DATABASE
     ****************/

    public CompositePointEvent sendPointEventPushNotification(@NotNull CompositePointEvent event) {
        CompositeRelationship rel = event.getRelationship();

        // Send to the other user in Relationship
        for (Userdata user : Arrays.asList(rel.getUserOne(), rel.getUserTwo())) {
            if (!user.getUserUuid().equals(event.getPointGiverUserUuid())) { // Send to the other user in Relationship
                HashMap<String, Object> messageData = new HashMap<>();
                messageData.put(FcmData.NOTIFICATION_TYPE, FcmData.NEW_POINTS_TYPE);
                messageData.put(FcmData.POINT_EVENT_ID, event.getPointEventUuid().toString());
                messageData.put(FcmData.POINT_EVENT_TITLE, event.getPointEventTitle());
                messageData.put(FcmData.POINT_SUM, event.getPointEventTotalPoints());

                send(user.getUserAuthId(), messageData);
            }
        }

        // TODO - Send Notifications to Friends

        // TODO - Send Notifications to Followers

        return event;
    }

    public PointEventLike sendLikeNotification(@NotNull PointEventLike like) {
        // Get the Event liked
        CompositePointEvent likedEvent = pointService.getCompositePointEvent(like.getPointEventUuid());

        // Send to the other user in Relationship
        for (Userdata user : Arrays.asList(likedEvent.getRelationship().getUserOne(), likedEvent.getRelationship().getUserTwo())) {
            if (!user.getUserUuid().equals(like.getPointEventLikeUserUuid())) {
                HashMap<String, Object> messageData = new HashMap<>();
                messageData.put(FcmData.NOTIFICATION_TYPE, FcmData.NEW_LIKE_TYPE);
                messageData.put(FcmData.POINT_EVENT_ID, likedEvent.getPointEventUuid().toString());

                send(user.getUserAuthId(), messageData);
            }
        }

        return like;
    }

    public CompositeFriendshipRequest sendFriendRequestNotification(@NotNull CompositeFriendshipRequest request) {
        HashMap<String, Object> messageData = new HashMap<>();
        messageData.put(FcmData.NOTIFICATION_TYPE, FcmData.NEW_FRIEND_REQUEST_TYPE);
        messageData.put(FcmData.FRIEND_REQUEST_ID, request.getFriendRequestUuid().toString());
        messageData.put(FcmData.FRIEND_REQUEST_SENDER_NAME, request.getSenderUser().getUserFirstname());

        send(request.getRecipientUser().getUserAuthId(), messageData);

        return request;
    }

    public CompositeRelationshipRequest sendRelationshipRequestNotification(@NotNull CompositeRelationshipRequest request) {
        HashMap<String, Object> messageData = new HashMap<>();
        messageData.put(FcmData.NOTIFICATION_TYPE, FcmData.NEW_RELATIONSHIP_REQUEST_TYPE);
        messageData.put(FcmData.RELATIONSHIP_REQUEST_ID, request.getRelationshipRequestUuid().toString());
        messageData.put(FcmData.RELATIONSHIP_REQUEST_SENDER_NAME, request.getSenderUser().getUserFirstname());

        send(request.getRecipientUser().getUserAuthId(), messageData);

        return request;
    }

    public CompositeRelationshipRequest sendNewRelationshipNotification(CompositeRelationshipRequest compositeRelationshipRequest) {
        // Get the Active Relationship and send a Notification to both Users
        Relationship senderRelationship = relationshipService.getActiveUserRelationship(compositeRelationshipRequest.getSenderUser().getUserUuid());
        Relationship recipientRelationship = relationshipService.getActiveUserRelationship(compositeRelationshipRequest.getRecipientUser().getUserUuid());

        if (senderRelationship == null) {
            Logger.getGlobal().log(Level.SEVERE, "Request Sender null! Error!");
        } else if (recipientRelationship == null) {
            Logger.getGlobal().log(Level.SEVERE, "Recipient Sender null! Error!");
        } else if (!recipientRelationship.getRelationshipUuid().equals(senderRelationship.getRelationshipUuid())) {
            Logger.getGlobal().log(Level.SEVERE, "Sender relationship does not match recipient relationship! Error!");
        } else {
            // Send to both users in the Relationship
            for (Userdata user : Arrays.asList(compositeRelationshipRequest.getSenderUser(), compositeRelationshipRequest.getRecipientUser())) {
                HashMap<String, Object> messageData = new HashMap<>();
                messageData.put(FcmData.NOTIFICATION_TYPE, FcmData.NEW_RELATIONSHIP_TYPE);
                messageData.put(FcmData.NEW_RELATIONSHIP_ID, senderRelationship.getRelationshipUuid().toString());
                messageData.put(FcmData.NEW_RELATIONSHIP_SENDER_NAME, compositeRelationshipRequest.getSenderUser().getUserFirstname());


                send(user.getUserAuthId(), messageData);
            }
        }

        return compositeRelationshipRequest;
    }

    public CompositeRelationshipRequest sendRelationshipRequestNegativeNotification(CompositeRelationshipRequest compositeRelationshipRequest) {
        // Send to both users in the Request
        for (Userdata user : Arrays.asList(compositeRelationshipRequest.getSenderUser(), compositeRelationshipRequest.getRecipientUser())) {
            HashMap<String, Object> messageData = new HashMap<>();
            messageData.put(FcmData.NOTIFICATION_TYPE, FcmData.RELATIONSHIP_REQUEST_UPDATED_TYPE);

            send(user.getUserAuthId(), messageData);
        }

        return compositeRelationshipRequest;
    }

    public CompositeRelationshipBreakupRequest sendRelationshipBreakupUpdate(CompositeRelationshipBreakupRequest compositeRelationshipBreakupRequest) {
        // Send to both users in the Request
        for (Userdata user : Arrays.asList(compositeRelationshipBreakupRequest.getRelationship().getUserOne(), compositeRelationshipBreakupRequest.getRelationship().getUserTwo())) {
            HashMap<String, Object> messageData = new HashMap<>();
            messageData.put(FcmData.NOTIFICATION_TYPE, FcmData.RELATIONSHIP_BREAKUP_TYPE);
            messageData.put(FcmData.BREAKUP_STATUS, compositeRelationshipBreakupRequest.getRelationshipBreakupRequestStatus());

            send(user.getUserAuthId(), messageData);
        }

        return compositeRelationshipBreakupRequest;
    }


    private void send(@NotNull String userAuthID, @NotNull Map<String, Object> data) {
        getUserPushTokenRef(userAuthID).addListenerForSingleValueEvent(new ConvenientListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setTo((String) dataSnapshot.getValue());
                    sendMessage.setData(data);

                    send(sendMessage);
                } else {
                    Logger.getGlobal().log(Level.INFO, "PushToken not found for userAuthID:" + userAuthID);
                }
            }
        });
    }

    private void send(@NotNull SendMessage sendMessage) {
        webClient.post()
                .uri("https://fcm.googleapis.com/fcm/send")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "key=" + this.settings.getApiKey())
                .syncBody(sendMessage).exchange().subscribe(clientResponse -> System.out.println("send: " + clientResponse.statusCode()));
    }


    private DatabaseReference getUserPushTokenRef(@NotNull String userAuthID) {
        return chatDB.child("prod").child("users").child(userAuthID).child("meta").child("pushToken");
    }


    /****************
     * AUTH
     ****************/

    public UserAuthInfo UserAuthInfo(@NotNull UserAuthWrapper wrapper) {
        String decryptedUserAuthInfo = wrapper.getEncryptedUserAuthInfo(); // TODO - Currently unencrypted

        return new Gson().fromJson(decryptedUserAuthInfo, UserAuthInfo.class);
    }

    public FirebaseToken verifyToken(@NotNull String authToken) {
        try {
            return FirebaseAuth.getInstance().verifyIdTokenAsync(authToken, true).get();
        } catch (ExecutionException e) {

            Throwable cause = e.getCause(); // The FirebaseAuthException is always wrapped inside an ExecutionException.

            if (cause != null && cause instanceof FirebaseAuthException) {
                FirebaseAuthException firebaseAuthException = (FirebaseAuthException) cause;
                throw new TokenNoValidException(firebaseAuthException.getErrorCode(), firebaseAuthException.getMessage(), firebaseAuthException.getCause());
            }

            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    public String createUserJWT(@NotNull FirebaseToken token, @NotNull String userUid, @NotNull String userEmail) {
        if (!token.getUid().equals(userUid)) {
            throw new UserTokenNotMatchedException("User UID does not match! Error!");
        }

        if (!token.getEmail().equals(userEmail)) {
            throw new UserTokenNotMatchedException("User email does not match! Error!");
        }


        return Jwts.builder()
                .setSubject(userEmail)
                .signWith(SignatureAlgorithm.HS512, "U2VjcmV0")
                .compact();
    }
}