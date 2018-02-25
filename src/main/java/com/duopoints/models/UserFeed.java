package com.duopoints.models;

import com.duopoints.models.composites.CompositePointEvent;
import com.duopoints.models.composites.CompositeUserLevel;

import java.util.List;

public class UserFeed {

    private List<CompositeUserLevel> compositeUserLevels;
    private List<CompositePointEvent> compositePointEvents;

    public UserFeed(List<CompositeUserLevel> userLevels, List<CompositePointEvent> pointEvents) {
        this.compositeUserLevels = userLevels;
        this.compositePointEvents = pointEvents;
    }

    public List<CompositeUserLevel> getCompositeUserLevels() {
        return compositeUserLevels;
    }

    public void setCompositeUserLevels(List<CompositeUserLevel> compositeUserLevels) {
        this.compositeUserLevels = compositeUserLevels;
    }

    public List<CompositePointEvent> getCompositePointEvents() {
        return compositePointEvents;
    }

    public void setCompositePointEvents(List<CompositePointEvent> compositePointEvents) {
        this.compositePointEvents = compositePointEvents;
    }
}