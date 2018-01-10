package com.duopoints.models.composites;

import com.duopoints.db.tables.pojos.Point;
import com.duopoints.db.tables.pojos.Pointevent;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CombinedPointEvent extends Pointevent {
    @JsonProperty("point")
    private Point[] points;

    @JsonProperty("relationship")
    private CombinedRelationshipUsers relationshipUsers;

    @JsonProperty("point")
    public Point[] getPoints() {
        return points;
    }

    @JsonProperty("relationship")
    public CombinedRelationshipUsers getRelationshipUsers() {
        return relationshipUsers;
    }

    @JsonProperty("point")
    public void setPoints(Point[] points) {
        this.points = points;
    }

    @JsonProperty("relationship")
    public void setRelationshipUsers(CombinedRelationshipUsers relationshipUsers) {
        this.relationshipUsers = relationshipUsers;
    }
}