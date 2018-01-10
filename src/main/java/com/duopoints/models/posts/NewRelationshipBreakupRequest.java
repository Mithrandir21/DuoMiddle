package com.duopoints.models.posts;

import com.duopoints.models.RequestParameters;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class NewRelationshipBreakupRequest {

    @JsonProperty(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_requesting_user_uuid)
    public UUID requestingUserID;

    @JsonProperty(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_relationship_uuid)
    public UUID relID;

    @JsonProperty(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_comment)
    public String comment;


    /************
     * GETTERS
     ************/

    @JsonProperty(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_requesting_user_uuid)
    public UUID getRequestingUserID() {
        return requestingUserID;
    }

    @JsonProperty(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_relationship_uuid)
    public UUID getRelID() {
        return relID;
    }

    @JsonProperty(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_comment)
    public String getComment() {
        return comment;
    }


    /************
     * SETTERS
     ************/

    @JsonProperty(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_requesting_user_uuid)
    public void setRequestingUserID(UUID requestingUserID) {
        this.requestingUserID = requestingUserID;
    }

    @JsonProperty(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_relationship_uuid)
    public void setRelID(UUID relID) {
        this.relID = relID;
    }

    @JsonProperty(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_comment)
    public void setComment(String comment) {
        this.comment = comment;
    }
}
