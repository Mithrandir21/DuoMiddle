package com.duopoints.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessJson {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("success")
    public boolean isSuccess() {
        return success;
    }

    @JsonProperty("success")
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
