package com.duopoints.models.posts;

import java.util.UUID;

public class NewRelationshipBreakupRequest {

    public UUID requestingUserUUID;
    public UUID relationshipUUID;
    public String requestComment;

    public NewRelationshipBreakupRequest() {
        super();
    }

    public NewRelationshipBreakupRequest(UUID requestingUserUUID, UUID relationshipUUID, String requestComment) {
        this.requestingUserUUID = requestingUserUUID;
        this.relationshipUUID = relationshipUUID;
        this.requestComment = requestComment;
    }

    /************
     * GETTERS
     ************/

    public UUID getRequestingUserUUID() {
        return requestingUserUUID;
    }

    public UUID getRelationshipUUID() {
        return relationshipUUID;
    }

    public String getRequestComment() {
        return requestComment;
    }


    /************
     * SETTERS
     ************/
    public void setRequestingUserUUID(UUID requestingUserUUID) {
        this.requestingUserUUID = requestingUserUUID;
    }

    public void setRelationshipUUID(UUID relationshipUUID) {
        this.relationshipUUID = relationshipUUID;
    }

    public void setRequestComment(String requestComment) {
        this.requestComment = requestComment;
    }
}