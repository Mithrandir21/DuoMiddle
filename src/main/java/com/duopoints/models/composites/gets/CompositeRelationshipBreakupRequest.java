package com.duopoints.models.composites.gets;

import com.duopoints.db.tables.pojos.RelationshipBreakupRequest;
import com.duopoints.db.tables.pojos.Userdata;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public class CompositeRelationshipBreakupRequest extends RelationshipBreakupRequest {

    private CompositeRelationship relationship;
    private Userdata user;

    public CompositeRelationshipBreakupRequest(@NotNull RelationshipBreakupRequest value, @NotNull CompositeRelationship relationship, @Nullable Userdata user) {
        super(value);
        this.relationship = relationship;
        this.user = user;
    }

    @NotNull
    public CompositeRelationship getRelationship() {
        return relationship;
    }

    public void setRelationship(@NotNull CompositeRelationship relationship) {
        this.relationship = relationship;
    }

    @NotNull
    public Userdata getUser() {
        return user;
    }

    public void setUser(@NotNull Userdata user) {
        this.user = user;
    }
}
