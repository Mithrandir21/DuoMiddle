package com.duopoints.models.composites.gets;

import com.duopoints.db.tables.pojos.Relationship;
import com.duopoints.db.tables.pojos.Userdata;

import javax.validation.constraints.NotNull;

public class CompositeRelationship extends Relationship {
    private Userdata userOne;
    private Userdata userTwo;

    public CompositeRelationship(@NotNull Relationship value, @NotNull Userdata userOne, @NotNull Userdata userTwo) {
        super(value);
        this.userOne = userOne;
        this.userTwo = userTwo;
    }

    @NotNull
    public Userdata getUserOne() {
        return userOne;
    }

    public void setUserOne(@NotNull Userdata userOne) {
        this.userOne = userOne;
    }

    @NotNull
    public Userdata getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(@NotNull Userdata userTwo) {
        this.userTwo = userTwo;
    }
}