package com.duopoints.models.auth;

public class UserAuthenticatedResponse {
    private String userJWT;

    public UserAuthenticatedResponse(String userJWT) {
        this.userJWT = userJWT;
    }

    public String getUserJWT() {
        return userJWT;
    }

    public void setUserJWT(String userJWT) {
        this.userJWT = userJWT;
    }
}
