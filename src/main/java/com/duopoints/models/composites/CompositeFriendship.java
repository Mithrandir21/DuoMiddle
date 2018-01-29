package com.duopoints.models.composites;

import com.duopoints.db.tables.pojos.Friendship;
import com.duopoints.db.tables.pojos.Userdata;

import javax.validation.constraints.NotNull;

public class CompositeFriendship extends Friendship {

    private Userdata userOne;
    private Userdata userTwo;

    public CompositeFriendship(@NotNull Friendship value, @NotNull Userdata userOne, @NotNull Userdata userTwo) {
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
