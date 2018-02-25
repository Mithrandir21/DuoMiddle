package com.duopoints.models.composites;

import com.duopoints.db.tables.pojos.MediaObject;
import com.duopoints.db.tables.pojos.PointEvent;
import com.duopoints.db.tables.pojos.PointEventCommentdata;
import com.duopoints.db.tables.pojos.Pointdata;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CompositePointEvent extends PointEvent {

    private CompositeRelationship relationship;
    private List<Pointdata> pointdata;
    private List<PointEventCommentdata> pointEventComments;
    private List<MediaObject> mediaObjects;
    private int pointEventTotalPoints = 0;

    public CompositePointEvent(@NotNull PointEvent value, @NotNull CompositeRelationship relationship, @NotNull List<Pointdata> pointdata, @NotNull List<PointEventCommentdata> pointEventComments, List<MediaObject> mediaObjects) {
        super(value);
        this.relationship = relationship;
        this.pointdata = pointdata;
        this.pointEventComments = pointEventComments;
        this.mediaObjects = mediaObjects;
        for (Pointdata point : pointdata) {
            pointEventTotalPoints += point.getPointValue();
        }
    }

    @NotNull
    public CompositeRelationship getRelationship() {
        return relationship;
    }

    public void setRelationship(@NotNull CompositeRelationship relationship) {
        this.relationship = relationship;
    }

    @NotNull
    public List<Pointdata> getPointdata() {
        return pointdata;
    }

    public void setPointdata(@NotNull List<Pointdata> pointdata) {
        this.pointdata = pointdata;
    }

    @NotNull
    public List<PointEventCommentdata> getPointEventComments() {
        return pointEventComments;
    }

    public void setPointEventComments(@NotNull List<PointEventCommentdata> pointEventComments) {
        this.pointEventComments = pointEventComments;
    }

    public List<MediaObject> getMediaObjects() {
        return mediaObjects;
    }

    public int getPointEventTotalPoints() {

        return pointEventTotalPoints;
    }

    public void setPointEventTotalPoints(int pointEventTotalPoints) {
        this.pointEventTotalPoints = pointEventTotalPoints;
    }
}