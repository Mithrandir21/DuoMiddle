package com.duopoints.models.auth;

public class UserAuthWrapper {
    private String encryptedUserAuthInfo;

    public String getEncryptedUserAuthInfo() {
        return encryptedUserAuthInfo;
    }

    public void setEncryptedUserAuthInfo(String encryptedUserAuthInfo) {
        this.encryptedUserAuthInfo = encryptedUserAuthInfo;
    }
}
