package com.duopoints.models.composites.gets;

import com.duopoints.db.tables.pojos.PointEvent;
import com.duopoints.db.tables.pojos.Pointdata;
import com.duopoints.db.tables.pojos.Pointeventcommentdata;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CompositePointEvent extends PointEvent {

    private CompositeRelationship relationship;
    private List<Pointdata> pointdata;
    private List<Pointeventcommentdata> pointEventComments;

    public CompositePointEvent(@NotNull PointEvent value, @NotNull CompositeRelationship relationship, @NotNull List<Pointdata> pointdata, @NotNull List<Pointeventcommentdata> pointEventComments) {
        super(value);
        this.relationship = relationship;
        this.pointdata = pointdata;
        this.pointEventComments = pointEventComments;
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
    public List<Pointeventcommentdata> getPointEventComments() {
        return pointEventComments;
    }

    public void setPointEventComments(@NotNull List<Pointeventcommentdata> pointEventComments) {
        this.pointEventComments = pointEventComments;
    }
}