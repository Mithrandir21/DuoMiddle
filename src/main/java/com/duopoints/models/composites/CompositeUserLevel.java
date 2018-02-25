package com.duopoints.models.composites;

import com.duopoints.db.tables.pojos.UserLevel;
import com.duopoints.db.tables.pojos.Userdata;

public class CompositeUserLevel extends UserLevel {

    private Userdata user;

    public CompositeUserLevel(UserLevel value, Userdata user) {
        super(value);
        this.user = user;
    }

    public Userdata getUser() {
        return user;
    }

    public void setUser(Userdata user) {
        this.user = user;
    }
}
