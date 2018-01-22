package com.duopoints.models;

import com.duopoints.db.tables.pojos.Relationship;
import com.duopoints.db.tables.pojos.Userdata;
import com.duopoints.models.composites.CompositePointEvent;
import com.duopoints.models.composites.CompositeRelationship;

import java.util.List;

public class FullRelationshipData extends CompositeRelationship {

    private List<CompositePointEvent> compositePointEvents;

    public FullRelationshipData(Relationship value, Userdata userOne, Userdata userTwo, List<CompositePointEvent> compositePointEvents) {
        super(value, userOne, userTwo);
        this.compositePointEvents = compositePointEvents;
    }

    public List<CompositePointEvent> getCompositePointEvents() {
        return compositePointEvents;
    }

    public void setCompositePointEvents(List<CompositePointEvent> compositePointEvents) {
        this.compositePointEvents = compositePointEvents;
    }
}
