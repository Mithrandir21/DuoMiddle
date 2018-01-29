package com.duopoints.models.posts;

import com.duopoints.RequestParameters;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class NewFriendshipRequest {

    @JsonProperty(RequestParameters.FRIENDSHIP_REQUEST_friend_request_sender_user_uuid)
    public UUID requestSenderID;

    @JsonProperty(RequestParameters.FRIENDSHIP_REQUEST_friend_request_recipient_user_uuid)
    public UUID requestRecipientID;

    @JsonProperty(RequestParameters.FRIENDSHIP_REQUEST_friend_request_comment)
    public String requestComment;


    /************
     * GETTERS
     ************/

    @JsonProperty(RequestParameters.FRIENDSHIP_REQUEST_friend_request_sender_user_uuid)
    public UUID getRequestSenderID() {
        return requestSenderID;
    }

    @JsonProperty(RequestParameters.FRIENDSHIP_REQUEST_friend_request_recipient_user_uuid)
    public UUID getRequestRecipientID() {
        return requestRecipientID;
    }

    @JsonProperty(RequestParameters.FRIENDSHIP_REQUEST_friend_request_comment)
    public String getRequestComment() {
        return requestComment;
    }


    /************
     * SETTERS
     ************/

    @JsonProperty(RequestParameters.FRIENDSHIP_REQUEST_friend_request_sender_user_uuid)
    public void setRequestSenderID(UUID requestSenderID) {
        this.requestSenderID = requestSenderID;
    }

    @JsonProperty(RequestParameters.FRIENDSHIP_REQUEST_friend_request_recipient_user_uuid)
    public void setRequestRecipientID(UUID requestRecipientID) {
        this.requestRecipientID = requestRecipientID;
    }

    @JsonProperty(RequestParameters.FRIENDSHIP_REQUEST_friend_request_comment)
    public void setRequestComment(String requestComment) {
        this.requestComment = requestComment;
    }
}