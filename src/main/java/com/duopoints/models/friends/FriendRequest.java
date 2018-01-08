package com.duopoints.models.friends;

import com.duopoints.models.RequestParameters;
import com.duopoints.models.core.Timestamp;
import com.duopoints.models.enums.FriendRequestStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class FriendRequest extends Timestamp {
    @JsonProperty(RequestParameters.FRIEND_REQUEST_friend_request_uuid)
    private UUID friendRequestUUID;

    @JsonProperty(RequestParameters.FRIEND_REQUEST_friend_request_sender_user_uuid)
    private UUID senderUUID;

    @JsonProperty(RequestParameters.FRIEND_REQUEST_friend_request_recipient_user_uuid)
    private UUID recipientUUID;

    @JsonProperty(RequestParameters.FRIEND_REQUEST_friend_request_comment)
    private String requestComment;

    @JsonProperty(RequestParameters.FRIEND_REQUEST_friend_request_status)
    private FriendRequestStatus friendRequestStatus;


    /************
     * GETTERS
     ************/

    @JsonProperty(RequestParameters.FRIEND_REQUEST_friend_request_uuid)
    public UUID getFriendRequestUUID() {
        return friendRequestUUID;
    }

    @JsonProperty(RequestParameters.FRIEND_REQUEST_friend_request_sender_user_uuid)
    public UUID getSenderUUID() {
        return senderUUID;
    }

    @JsonProperty(RequestParameters.FRIEND_REQUEST_friend_request_recipient_user_uuid)
    public UUID getRecipientUUID() {
        return recipientUUID;
    }

    @JsonProperty(RequestParameters.FRIEND_REQUEST_friend_request_comment)
    public String getRequestComment() {
        return requestComment;
    }

    @JsonProperty(RequestParameters.FRIEND_REQUEST_friend_request_status)
    public FriendRequestStatus getFriendRequestStatus() {
        return friendRequestStatus;
    }


    /************
     * SETTERS
     ************/

    @JsonProperty(RequestParameters.FRIEND_REQUEST_friend_request_uuid)
    public void setFriendRequestUUID(UUID friendRequestUUID) {
        this.friendRequestUUID = friendRequestUUID;
    }

    @JsonProperty(RequestParameters.FRIEND_REQUEST_friend_request_sender_user_uuid)
    public void setSenderUUID(UUID senderUUID) {
        this.senderUUID = senderUUID;
    }

    @JsonProperty(RequestParameters.FRIEND_REQUEST_friend_request_recipient_user_uuid)
    public void setRecipientUUID(UUID recipientUUID) {
        this.recipientUUID = recipientUUID;
    }

    @JsonProperty(RequestParameters.FRIEND_REQUEST_friend_request_comment)
    public void setRequestComment(String requestComment) {
        this.requestComment = requestComment;
    }

    @JsonProperty(RequestParameters.FRIEND_REQUEST_friend_request_status)
    public void setFriendRequestStatus(FriendRequestStatus friendRequestStatus) {
        this.friendRequestStatus = friendRequestStatus;
    }
}
