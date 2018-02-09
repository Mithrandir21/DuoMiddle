package com.duopoints.models.posts;

import com.duopoints.db.tables.pojos.Point;
import com.duopoints.db.tables.pojos.PointEvent;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class NewPointEvent extends PointEvent {

    private List<Point> points;

    public NewPointEvent() {
    }

    public NewPointEvent(List<Point> points) {
        this.points = points;
    }

    public NewPointEvent(PointEvent value, List<Point> points) {
        super(value);
        this.points = points;
    }

    public NewPointEvent(UUID pointEventUuid, UUID pointGiverUserUuid, UUID relationshipUuid, Short pointEventEmotionNumber, String pointEventTitle, String pointEventSubtitle, String pointEventType, String pointEventStatus, String pointEventComment, Short pointEventLikes, Timestamp createdUtc, Timestamp lastModifiedUtc, List<Point> points) {
        super(pointEventUuid, pointGiverUserUuid, relationshipUuid, pointEventEmotionNumber, pointEventTitle, pointEventSubtitle, pointEventType, pointEventStatus, pointEventComment, pointEventLikes, createdUtc, lastModifiedUtc);
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}