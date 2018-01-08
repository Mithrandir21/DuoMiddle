package com.duopoints.models.core;

import com.duopoints.models.RequestParameters;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Relationship extends Timestamp {
    @JsonProperty(RequestParameters.RELATIONSHIP_relationship_uuid)
    private UUID relationship_uuid;

    @JsonProperty(RequestParameters.RELATIONSHIP_user_uuid_1)
    private UUID user_uuid_1;

    @JsonProperty(RequestParameters.RELATIONSHIP_user_uuid_2)
    private UUID user_uuid_2;

    @JsonProperty(RequestParameters.RELATIONSHIP_rel_ach_list_uuid)
    private UUID rel_ach_list_uuid;

    @JsonProperty(RequestParameters.RELATIONSHIP_start_date)
    private String start_date;

    @JsonProperty(RequestParameters.RELATIONSHIP_end_date)
    private String end_date;

    @JsonProperty(RequestParameters.RELATIONSHIP_auto_accept_points)
    private boolean auto_accept_points;

    @JsonProperty(RequestParameters.RELATIONSHIP_relationship_image_uuid)
    private UUID relationship_image_uuid;

    @JsonProperty(RequestParameters.RELATIONSHIP_status)
    private String status;

    @JsonProperty(RequestParameters.RELATIONSHIP_is_secret)
    private boolean is_secret;

    @JsonProperty(RequestParameters.RELATIONSHIP_revivable)
    private boolean revivable;

    @JsonProperty(RequestParameters.RELATIONSHIP_rel_title)
    private String rel_title;

    @JsonProperty(RequestParameters.RELATIONSHIP_rel_desc)
    private String rel_desc;

    @JsonProperty(RequestParameters.RELATIONSHIP_rel_total_points)
    private String rel_total_points;

    /************
     * GETTERS
     ************/

    @JsonProperty(RequestParameters.RELATIONSHIP_relationship_uuid)
    public UUID getRelationship_uuid() {
        return relationship_uuid;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_user_uuid_1)
    public UUID getUser_uuid_1() {
        return user_uuid_1;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_user_uuid_2)
    public UUID getUser_uuid_2() {
        return user_uuid_2;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_rel_ach_list_uuid)
    public UUID getRel_ach_list_uuid() {
        return rel_ach_list_uuid;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_start_date)
    public String getStart_date() {
        return start_date;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_end_date)
    public String getEnd_date() {
        return end_date;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_auto_accept_points)
    public boolean isAuto_accept_points() {
        return auto_accept_points;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_relationship_image_uuid)
    public UUID getRelationship_image_uuid() {
        return relationship_image_uuid;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_status)
    public String getStatus() {
        return status;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_is_secret)
    public boolean is_secret() {
        return is_secret;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_revivable)
    public boolean isRevivable() {
        return revivable;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_rel_title)
    public String getRel_title() {
        return rel_title;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_rel_desc)
    public String getRel_desc() {
        return rel_desc;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_rel_total_points)
    public String getRel_total_points() {
        return rel_total_points;
    }

    /************
     * SETTERS
     ************/

    @JsonProperty(RequestParameters.RELATIONSHIP_relationship_uuid)
    public void setRelationship_uuid(UUID relationship_uuid) {
        this.relationship_uuid = relationship_uuid;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_user_uuid_1)
    public void setUser_uuid_1(UUID user_uuid_1) {
        this.user_uuid_1 = user_uuid_1;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_user_uuid_2)
    public void setUser_uuid_2(UUID user_uuid_2) {
        this.user_uuid_2 = user_uuid_2;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_rel_ach_list_uuid)
    public void setRel_ach_list_uuid(UUID rel_ach_list_uuid) {
        this.rel_ach_list_uuid = rel_ach_list_uuid;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_start_date)
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_end_date)
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_auto_accept_points)
    public void setAuto_accept_points(boolean auto_accept_points) {
        this.auto_accept_points = auto_accept_points;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_relationship_image_uuid)
    public void setRelationship_image_uuid(UUID relationship_image_uuid) {
        this.relationship_image_uuid = relationship_image_uuid;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_status)
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_is_secret)
    public void setIs_secret(boolean is_secret) {
        this.is_secret = is_secret;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_revivable)
    public void setRevivable(boolean revivable) {
        this.revivable = revivable;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_rel_title)
    public void setRel_title(String rel_title) {
        this.rel_title = rel_title;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_rel_desc)
    public void setRel_desc(String rel_desc) {
        this.rel_desc = rel_desc;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_rel_total_points)
    public void setRel_total_points(String rel_total_points) {
        this.rel_total_points = rel_total_points;
    }
}
