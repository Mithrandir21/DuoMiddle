package com.duopoints.models.posts;

import com.duopoints.db.tables.pojos.Point;
import com.duopoints.db.tables.pojos.PointEvent;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class NewPointEvent extends PointEvent {

    private List<Point> points;
    private int mediaCount;

    public NewPointEvent() {
    }

    public NewPointEvent(List<Point> points, int mediaCount) {
        this.points = points;
        this.mediaCount = mediaCount;
    }

    public NewPointEvent(PointEvent value, List<Point> points, int mediaCount) {
        super(value);
        this.points = points;
        this.mediaCount = mediaCount;
    }

    public NewPointEvent(UUID pointEventUuid, UUID pointGiverUserUuid, UUID relationshipUuid, Short pointEventEmotionNumber, String pointEventTitle, String pointEventSubtitle, String pointEventType, String pointEventStatus, String pointEventComment, Short pointEventLikes, Timestamp createdUtc, Timestamp lastModifiedUtc, List<Point> points, int mediaCount) {
        super(pointEventUuid, pointGiverUserUuid, relationshipUuid, pointEventEmotionNumber, pointEventTitle, pointEventSubtitle, pointEventType, pointEventStatus, pointEventComment, pointEventLikes, createdUtc, lastModifiedUtc);
        this.points = points;
        this.mediaCount = mediaCount;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public int getMediaCount() {
        return mediaCount;
    }

    public void setMediaCount(int mediaCount) {
        this.mediaCount = mediaCount;
    }
}