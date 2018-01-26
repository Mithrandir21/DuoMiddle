package com.duopoints.models.posts;

import com.duopoints.RequestParameters;
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

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_desired_status)
    public String requestRelDesiredStatus;

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_is_secret)
    public boolean requestRelisSecret;


    /************
     * GETTERS
     ************/

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_sender_user_uuid)
    public UUID getSenderUserID() {
        return senderUserID;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_name)
    public String getRecipientUserName() {
        return recipientUserName;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_email)
    public String getRecipientUserEmail() {
        return recipientUserEmail;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_db_id)
    public UUID getRecipientUserID() {
        return recipientUserID;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_comment)
    public String getRequestComment() {
        return requestComment;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_desired_status)
    public String getRequestRelDesiredStatus() {
        return requestRelDesiredStatus;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_is_secret)
    public boolean isRequestRelisSecret() {
        return requestRelisSecret;
    }

    /************
     * SETTERS
     ************/

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_sender_user_uuid)
    public void setSenderUserID(UUID senderUserID) {
        this.senderUserID = senderUserID;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_name)
    public void setRecipientUserName(String recepientUserName) {
        this.recipientUserName = recepientUserName;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_email)
    public void setRecipientUserEmail(String recepientUserEmail) {
        this.recipientUserEmail = recepientUserEmail;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_db_id)
    public void setRecipientUserID(UUID recepientUserID) {
        this.recipientUserID = recepientUserID;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_comment)
    public void setRequestComment(String requestComment) {
        this.requestComment = requestComment;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_desired_status)
    public void setRequestRelDesiredStatus(String requestRelDesiredStatus) {
        this.requestRelDesiredStatus = requestRelDesiredStatus;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_is_secret)
    public void setRequestRelisSecret(boolean requestRelisSecret) {
        this.requestRelisSecret = requestRelisSecret;
    }
}