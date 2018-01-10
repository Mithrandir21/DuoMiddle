package com.duopoints.models.posts;

import com.duopoints.models.RequestParameters;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class NewRelationshipRequest {

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_sender_user_uuid)
    public UUID senderUserID;

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_name)
    public String recipientUserName;

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_email)
    public String recipientUserEmail;

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_db_id)
    public UUID recipientUserID;

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_comment)
    public String requestComment;


    /************
     * GETTERS
     ************/

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_sender_user_uuid)
    public UUID getSenderUserID() {
        return senderUserID;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_name)
    public String getRecepientUserName() {
        return recipientUserName;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_email)
    public String getRecepientUserEmail() {
        return recipientUserEmail;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_db_id)
    public UUID getRecepientUserID() {
        return recipientUserID;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_comment)
    public String getRequestComment() {
        return requestComment;
    }


    /************
     * SETTERS
     ************/

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_sender_user_uuid)
    public void setSenderUserID(UUID senderUserID) {
        this.senderUserID = senderUserID;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_name)
    public void setRecepientUserName(String recepientUserName) {
        this.recipientUserName = recepientUserName;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_email)
    public void setRecepientUserEmail(String recepientUserEmail) {
        this.recipientUserEmail = recepientUserEmail;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_db_id)
    public void setRecepientUserID(UUID recepientUserID) {
        this.recipientUserID = recepientUserID;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_comment)
    public void setRequestComment(String requestComment) {
        this.requestComment = requestComment;
    }
}