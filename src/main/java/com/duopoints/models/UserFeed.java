package com.duopoints.models;

import com.duopoints.db.tables.pojos.UserLevel;
import com.duopoints.models.composites.CompositePointEvent;

import java.util.List;

public class UserFeed {

    private List<UserLevel> userLevels;
    private List<CompositePointEvent> pointEvents;

    public UserFeed(List<UserLevel> userLevels, List<CompositePointEvent> pointEvents) {
        this.userLevels = userLevels;
        this.pointEvents = pointEvents;
    }

    public List<UserLevel> getUserLevels() {
        return userLevels;
    }

    public void setUserLevels(List<UserLevel> userLevels) {
        this.userLevels = userLevels;
    }

    public List<CompositePointEvent> getPointEvents() {
        return pointEvents;
    }

    public void setPointEvents(List<CompositePointEvent> pointEvents) {
        this.pointEvents = pointEvents;
    }
}