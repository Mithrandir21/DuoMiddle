package com.duopoints.models;

import com.duopoints.models.core.Timestamp;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RelAchReq extends Timestamp {
    @JsonProperty(RequestParameters.RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_rel_ach_req_id)
    private int relAchReqID;

    @JsonProperty(RequestParameters.RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_rel_ach_req_title)
    private String relAchReqTitle;

    @JsonProperty(RequestParameters.RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_rel_ach_req_description)
    private String relAchReqDesc;


    /************
     * GETTERS
     ************/

    @JsonProperty(RequestParameters.RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_rel_ach_req_id)
    public int getRelAchReqID() {
        return relAchReqID;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_rel_ach_req_title)
    public String getRelAchReqTitle() {
        return relAchReqTitle;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_rel_ach_req_description)
    public String getRelAchReqDesc() {
        return relAchReqDesc;
    }

    /************
     * SETTERS
     ************/

    @JsonProperty(RequestParameters.RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_rel_ach_req_id)
    public void setRelAchReqID(int relAchReqID) {
        this.relAchReqID = relAchReqID;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_rel_ach_req_title)
    public void setRelAchReqTitle(String relAchReqTitle) {
        this.relAchReqTitle = relAchReqTitle;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_rel_ach_req_description)
    public void setRelAchReqDesc(String relAchReqDesc) {
        this.relAchReqDesc = relAchReqDesc;
    }
}