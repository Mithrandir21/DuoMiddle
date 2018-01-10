package com.duopoints.models.posts;

import com.duopoints.models.RequestParameters;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class NewRelationship {

    @JsonProperty(RequestParameters.RELATIONSHIP_user_uuid_1)
    public UUID userOne;

    @JsonProperty(RequestParameters.RELATIONSHIP_user_uuid_2)
    public UUID userTwo;

    @JsonProperty(RequestParameters.RELATIONSHIP_status)
    public String relStatus;

    @JsonProperty(RequestParameters.RELATIONSHIP_is_secret)
    public boolean isSecret;


    /************
     * GETTERS
     ************/

    @JsonProperty(RequestParameters.RELATIONSHIP_user_uuid_1)
    public UUID getUserOne() {
        return userOne;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_user_uuid_2)
    public UUID getUserTwo() {
        return userTwo;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_status)
    public String getRelStatus() {
        return relStatus;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_is_secret)
    public boolean getIsSecret() {
        return isSecret;
    }


    /************
     * SETTERS
     ************/

    @JsonProperty(RequestParameters.RELATIONSHIP_user_uuid_1)
    public void setUserOne(UUID userOne) {
        this.userOne = userOne;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_user_uuid_2)
    public void setUserTwo(UUID userTwo) {
        this.userTwo = userTwo;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_status)
    public void setRelStatus(String relStatus) {
        this.relStatus = relStatus;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_is_secret)
    public void setIsSecret(boolean isSecret) {
        this.isSecret = isSecret;
    }
}