package com.duopoints.models.core;

import com.duopoints.models.RequestParameters;
import com.duopoints.models.enums.PointEventStatus;
import com.duopoints.models.enums.PointEventType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class PointEvent extends Timestamp {
    @JsonProperty(RequestParameters.POINT_EVENT_point_event_uuid)
    private UUID pointEventUUID;

    @JsonProperty(RequestParameters.POINT_EVENT_point_giver_user_uuid)
    private UUID pointGiverUserUUID;

    @JsonProperty(RequestParameters.POINT_EVENT_relationship_uuid)
    private UUID relationshipUUID;

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_emotion_number)
    private String pointEventEmotionNumber;

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_type)
    private PointEventType pointEventType;

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_status)
    private PointEventStatus pointEventStatus;

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_status_comment)
    private String pointEventStatusComment;

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_title)
    private String point_event_title;

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_comment)
    private String point_event_comment;

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_timestamp)
    private String pointEventTimestamp;

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_timezone)
    private String pointEventTimezone;


    /************
     * GETTERS
     ************/

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_uuid)
    public UUID getPointEventUUID() {
        return pointEventUUID;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_giver_user_uuid)
    public UUID getPointGiverUserUUID() {
        return pointGiverUserUUID;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_relationship_uuid)
    public UUID getRelationshipUUID() {
        return relationshipUUID;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_emotion_number)
    public String getPointEventEmotionNumber() {
        return pointEventEmotionNumber;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_type)
    public PointEventType getPointEventType() {
        return pointEventType;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_status)
    public PointEventStatus getPointEventStatus() {
        return pointEventStatus;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_status_comment)
    public String getPointEventStatusComment() {
        return pointEventStatusComment;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_title)
    public String getPoint_event_title() {
        return point_event_title;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_comment)
    public String getPoint_event_comment() {
        return point_event_comment;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_timestamp)
    public String getPointEventTimestamp() {
        return pointEventTimestamp;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_timezone)
    public String getPointEventTimezone() {
        return pointEventTimezone;
    }


    /************
     * SETTERS
     ************/

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_uuid)
    public void setPointEventUUID(UUID pointEventUUID) {
        this.pointEventUUID = pointEventUUID;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_giver_user_uuid)
    public void setPointGiverUserUUID(UUID pointGiverUserUUID) {
        this.pointGiverUserUUID = pointGiverUserUUID;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_relationship_uuid)
    public void setRelationshipUUID(UUID relationshipUUID) {
        this.relationshipUUID = relationshipUUID;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_emotion_number)
    public void setPointEventEmotionNumber(String pointEventEmotionNumber) {
        this.pointEventEmotionNumber = pointEventEmotionNumber;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_type)
    public void setPointEventType(PointEventType pointEventType) {
        this.pointEventType = pointEventType;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_status)
    public void setPointEventStatus(PointEventStatus pointEventStatus) {
        this.pointEventStatus = pointEventStatus;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_status_comment)
    public void setPointEventStatusComment(String pointEventStatusComment) {
        this.pointEventStatusComment = pointEventStatusComment;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_title)
    public void setPoint_event_title(String point_event_title) {
        this.point_event_title = point_event_title;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_comment)
    public void setPoint_event_comment(String point_event_comment) {
        this.point_event_comment = point_event_comment;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_timestamp)
    public void setPointEventTimestamp(String pointEventTimestamp) {
        this.pointEventTimestamp = pointEventTimestamp;
    }

    @JsonProperty(RequestParameters.POINT_EVENT_point_event_timezone)
    public void setPointEventTimezone(String pointEventTimezone) {
        this.pointEventTimezone = pointEventTimezone;
    }
}
