package com.duopoints.models.composites.gets;

import com.duopoints.db.tables.pojos.Pointdata;
import com.duopoints.db.tables.pojos.Pointevent;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class PointEventData extends Pointevent {

    private List<Pointdata> pointdata;

    public PointEventData() {
    }

    public PointEventData(List<Pointdata> pointdata) {
        this.pointdata = pointdata;
    }

    public PointEventData(Pointevent value, List<Pointdata> pointdata) {
        super(value);
        this.pointdata = pointdata;
    }

    public PointEventData(UUID pointeventdbId, UUID pointgiveruserdbId, UUID relationshipdbId, Short pointeventemotionNumber, String pointeventtype, String pointeventstatus, String pointeventComment, Timestamp createdUtc, Timestamp lastModifiedUtc, List<Pointdata> pointdata) {
        super(pointeventdbId, pointgiveruserdbId, relationshipdbId, pointeventemotionNumber, pointeventtype, pointeventstatus, pointeventComment, createdUtc, lastModifiedUtc);
        this.pointdata = pointdata;
    }

    public List<Pointdata> getPointdata() {
        return pointdata;
    }

    public void setPointdata(List<Pointdata> pointdata) {
        this.pointdata = pointdata;
    }
}