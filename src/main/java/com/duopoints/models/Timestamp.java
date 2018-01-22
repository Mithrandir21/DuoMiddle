package com.duopoints.models;

import com.duopoints.RequestParameters;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Timestamp {
    @JsonProperty(RequestParameters.TIMESTAMP_CREATED)
    private String created_UTC;

    @JsonProperty(RequestParameters.TIMESTAMP_LAST_MODIFIED)
    private String last_modified_UTC;


    @JsonProperty(RequestParameters.TIMESTAMP_CREATED)
    public String getCreated_UTC() {
        return created_UTC;
    }

    @JsonProperty(RequestParameters.TIMESTAMP_LAST_MODIFIED)
    public String getLast_modified_UTC() {
        return last_modified_UTC;
    }


    @JsonProperty(RequestParameters.TIMESTAMP_CREATED)
    public void setCreated_UTC(String created_UTC) {
        this.created_UTC = created_UTC;
    }

    @JsonProperty(RequestParameters.TIMESTAMP_LAST_MODIFIED)
    public void setLast_modified_UTC(String last_modified_UTC) {
        this.last_modified_UTC = last_modified_UTC;
    }
}
