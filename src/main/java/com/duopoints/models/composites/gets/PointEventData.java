package com.duopoints.models.composites.gets;

import com.duopoints.db.tables.pojos.PointEvent;
import com.duopoints.db.tables.pojos.PointEventComment;
import com.duopoints.db.tables.pojos.Pointdata;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class PointEventData extends PointEvent {

    private List<Pointdata> pointdata;
    private List<PointEventComment> pointEventComments;

    public PointEventData() {
    }

    public PointEventData(PointEvent value, List<Pointdata> pointdata, List<PointEventComment> pointEventComments) {
        super(value);
        this.pointdata = pointdata;
    }

    public PointEventData(UUID pointEventUuid, UUID pointGiverUserUuid, UUID relationshipUuid, Short pointEventEmotionNumber, String pointEventTitle, String pointEventSubtitle, String pointEventType, String pointEventStatus, String pointEventComment, Short pointEventLikes, Timestamp createdUtc, Timestamp lastModifiedUtc, List<Pointdata> pointdata, List<PointEventComment> pointEventComments) {
        super(pointEventUuid, pointGiverUserUuid, relationshipUuid, pointEventEmotionNumber, pointEventTitle, pointEventSubtitle, pointEventType, pointEventStatus, pointEventComment, pointEventLikes, createdUtc, lastModifiedUtc);
        this.pointdata = pointdata;
        this.pointEventComments = pointEventComments;
    }

    public List<Pointdata> getPointdata() {
        return pointdata;
    }

    public void setPointdata(List<Pointdata> pointdata) {
        this.pointdata = pointdata;
    }

    public List<PointEventComment> getPointEventComments() {
        return pointEventComments;
    }

    public void setPointEventComments(List<PointEventComment> pointEventComments) {
        this.pointEventComments = pointEventComments;
    }
}