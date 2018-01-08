package com.duopoints.models.core;

import com.duopoints.models.RequestParameters;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Point extends Timestamp {
    @JsonProperty(RequestParameters.POINT_uuid)
    private UUID pointUUID;

    @JsonProperty(RequestParameters.POINT_event_uuid)
    private UUID pointEventUUID;

    @JsonProperty(RequestParameters.POINT_value)
    private int pointValue;

    @JsonProperty(RequestParameters.POINT_type_number)
    private int pointTypeNumber;

    @JsonProperty(RequestParameters.POINT_chain_position)
    private int pointChainPosition;

    @JsonProperty(RequestParameters.POINT_comment)
    private String pointComment;


    /************
     * GETTERS
     ************/

    @JsonProperty(RequestParameters.POINT_uuid)
    public UUID getPointUUID() {
        return pointUUID;
    }

    @JsonProperty(RequestParameters.POINT_event_uuid)
    public UUID getPointEventUUID() {
        return pointEventUUID;
    }

    @JsonProperty(RequestParameters.POINT_value)
    public int getPointValue() {
        return pointValue;
    }

    @JsonProperty(RequestParameters.POINT_type_number)
    public int getPointTypeNumber() {
        return pointTypeNumber;
    }

    @JsonProperty(RequestParameters.POINT_chain_position)
    public int getPointChainPosition() {
        return pointChainPosition;
    }

    @JsonProperty(RequestParameters.POINT_comment)
    public String getPointComment() {
        return pointComment;
    }


    /************
     * SETTERS
     ************/

    @JsonProperty(RequestParameters.POINT_uuid)
    public void setPointUUID(UUID pointUUID) {
        this.pointUUID = pointUUID;
    }

    @JsonProperty(RequestParameters.POINT_event_uuid)
    public void setPointEventUUID(UUID pointEventUUID) {
        this.pointEventUUID = pointEventUUID;
    }

    @JsonProperty(RequestParameters.POINT_value)
    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    @JsonProperty(RequestParameters.POINT_type_number)
    public void setPointTypeNumber(int pointTypeNumber) {
        this.pointTypeNumber = pointTypeNumber;
    }

    @JsonProperty(RequestParameters.POINT_chain_position)
    public void setPointChainPosition(int pointChainPosition) {
        this.pointChainPosition = pointChainPosition;
    }

    @JsonProperty(RequestParameters.POINT_comment)
    public void setPointComment(String pointComment) {
        this.pointComment = pointComment;
    }
}