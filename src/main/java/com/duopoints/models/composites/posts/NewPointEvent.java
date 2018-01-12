package com.duopoints.models.composites.posts;

import com.duopoints.db.tables.pojos.Point;
import com.duopoints.db.tables.pojos.Pointevent;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class NewPointEvent extends Pointevent {

    private List<Point> points;

    public NewPointEvent() {
    }

    public NewPointEvent(List<Point> points) {
        this.points = points;
    }

    public NewPointEvent(Pointevent value, List<Point> points) {
        super(value);
        this.points = points;
    }

    public NewPointEvent(UUID pointeventdbId, UUID pointgiveruserdbId, UUID relationshipdbId, Short pointeventemotionNumber, String pointeventtype, String pointeventstatus, String pointeventComment, Timestamp createdUtc, Timestamp lastModifiedUtc, List<Point> points) {
        super(pointeventdbId, pointgiveruserdbId, relationshipdbId, pointeventemotionNumber, pointeventtype, pointeventstatus, pointeventComment, createdUtc, lastModifiedUtc);
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}