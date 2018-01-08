package com.duopoints.models.composites;

import com.duopoints.models.core.Relationship;
import com.duopoints.models.core.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CombinedRelationshipUsers extends Relationship {
    @JsonProperty("user1")
    private User user1;

    @JsonProperty("user2")
    private User user2;


    @JsonProperty("user1")
    public User getUser1() {
        return user1;
    }

    @JsonProperty("user1")
    public void setUser1(User user1) {
        this.user1 = user1;
    }

    @JsonProperty("user2")
    public User getUser2() {
        return user2;
    }

    @JsonProperty("user2")
    public void setUser2(User user2) {
        this.user2 = user2;
    }
}
