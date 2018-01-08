package com.duopoints.models.core;

import com.duopoints.models.RequestParameters;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User extends Timestamp {
    @JsonProperty(RequestParameters.USERS_user_uuid)
    private UUID User_UUID;

    @JsonProperty(RequestParameters.USERS_user_auth_provider)
    private String User_AuthProvider;

    @JsonProperty(RequestParameters.USERS_user_auth_id)
    private String User_Auth_ID;

    @JsonProperty(RequestParameters.USERS_user_email)
    private String User_Email;

    @JsonProperty(RequestParameters.USERS_user_firstname)
    private String User_Firstname;

    @JsonProperty(RequestParameters.USERS_user_lastname)
    private String User_Lastname;

    @JsonProperty(RequestParameters.USERS_user_nickname)
    private String User_Nickname;

    @JsonProperty(RequestParameters.USERS_user_gender)
    private String User_Gender;

    @JsonProperty(RequestParameters.USERS_user_age)
    private int User_Age;

    @JsonProperty(RequestParameters.USERS_user_status)
    private String User_Status;

    @JsonProperty(RequestParameters.USERS_user_joined)
    private String User_Joined;

    @JsonProperty(RequestParameters.USERS_user_logged_in_last)
    private String User_Logged_in_last;

    @JsonProperty(RequestParameters.USERS_user_total_points)
    private int User_Total_Points;

    @JsonProperty(RequestParameters.USERS_user_image_uuid)
    private UUID User_ImageDB_ID;

    @JsonProperty(RequestParameters.USERS_address_uuid)
    private UUID AddressDB_ID;

    @JsonProperty(RequestParameters.USERS_friends_list_uuid)
    private UUID FriendsListDB_ID;

    @JsonProperty(RequestParameters.USERS_user_level_uuid)
    private UUID User_LevelDB_ID;


    /************
     * GETTERS
     ************/

    @JsonProperty(RequestParameters.USERS_user_uuid)
    public UUID getUser_UUID() {
        return User_UUID;
    }

    @JsonProperty(RequestParameters.USERS_user_auth_provider)
    public String getUser_AuthProvider() {
        return User_AuthProvider;
    }

    @JsonProperty(RequestParameters.USERS_user_auth_id)
    public String getUser_Auth_ID() {
        return User_Auth_ID;
    }

    @JsonProperty(RequestParameters.USERS_user_email)
    public String getUser_Email() {
        return User_Email;
    }

    @JsonProperty(RequestParameters.USERS_user_firstname)
    public String getUser_Firstname() {
        return User_Firstname;
    }

    @JsonProperty(RequestParameters.USERS_user_lastname)
    public String getUser_Lastname() {
        return User_Lastname;
    }

    @JsonProperty(RequestParameters.USERS_user_nickname)
    public String getUser_Nickname() {
        return User_Nickname;
    }

    @JsonProperty(RequestParameters.USERS_user_gender)
    public String getUser_Gender() {
        return User_Gender;
    }

    @JsonProperty(RequestParameters.USERS_user_age)
    public int getUser_Age() {
        return User_Age;
    }

    @JsonProperty(RequestParameters.USERS_user_status)
    public String getUser_Status() {
        return User_Status;
    }

    @JsonProperty(RequestParameters.USERS_user_joined)
    public String getUser_Joined() {
        return User_Joined;
    }

    @JsonProperty(RequestParameters.USERS_user_logged_in_last)
    public String getUser_Logged_in_last() {
        return User_Logged_in_last;
    }

    @JsonProperty(RequestParameters.USERS_user_total_points)
    public int getUser_Total_Points() {
        return User_Total_Points;
    }

    @JsonProperty(RequestParameters.USERS_user_image_uuid)
    public UUID getUser_ImageDB_ID() {
        return User_ImageDB_ID;
    }

    @JsonProperty(RequestParameters.USERS_address_uuid)
    public UUID getAddressDB_ID() {
        return AddressDB_ID;
    }

    @JsonProperty(RequestParameters.USERS_friends_list_uuid)
    public UUID getFriendsListDB_ID() {
        return FriendsListDB_ID;
    }

    @JsonProperty(RequestParameters.USERS_user_level_uuid)
    public UUID getUser_LevelDB_ID() {
        return User_LevelDB_ID;
    }


    /************
     * SETTERS
     ************/

    @JsonProperty(RequestParameters.USERS_user_uuid)
    public void setUser_UUID(UUID user_UUID) {
        User_UUID = user_UUID;
    }

    @JsonProperty(RequestParameters.USERS_user_auth_provider)
    public void setUser_AuthProvider(String user_AuthProvider) {
        User_AuthProvider = user_AuthProvider;
    }

    @JsonProperty(RequestParameters.USERS_user_auth_id)
    public void setUser_Auth_ID(String user_Auth_ID) {
        User_Auth_ID = user_Auth_ID;
    }

    @JsonProperty(RequestParameters.USERS_user_email)
    public void setUser_Email(String user_Email) {
        User_Email = user_Email;
    }

    @JsonProperty(RequestParameters.USERS_user_firstname)
    public void setUser_Firstname(String user_Firstname) {
        User_Firstname = user_Firstname;
    }

    @JsonProperty(RequestParameters.USERS_user_lastname)
    public void setUser_Lastname(String user_Lastname) {
        User_Lastname = user_Lastname;
    }

    @JsonProperty(RequestParameters.USERS_user_nickname)
    public void setUser_Nickname(String user_Nickname) {
        User_Nickname = user_Nickname;
    }

    @JsonProperty(RequestParameters.USERS_user_gender)
    public void setUser_Gender(String user_Gender) {
        User_Gender = user_Gender;
    }

    @JsonProperty(RequestParameters.USERS_user_age)
    public void setUser_Age(int user_Age) {
        User_Age = user_Age;
    }

    @JsonProperty(RequestParameters.USERS_user_status)
    public void setUser_Status(String user_Status) {
        User_Status = user_Status;
    }

    @JsonProperty(RequestParameters.USERS_user_joined)
    public void setUser_Joined(String user_Joined) {
        User_Joined = user_Joined;
    }

    @JsonProperty(RequestParameters.USERS_user_logged_in_last)
    public void setUser_Logged_in_last(String user_Logged_in_last) {
        User_Logged_in_last = user_Logged_in_last;
    }

    @JsonProperty(RequestParameters.USERS_user_total_points)
    public void setUser_Total_Points(int user_Total_Points) {
        User_Total_Points = user_Total_Points;
    }

    @JsonProperty(RequestParameters.USERS_user_image_uuid)
    public void setUser_ImageDB_ID(UUID user_ImageDB_ID) {
        User_ImageDB_ID = user_ImageDB_ID;
    }

    @JsonProperty(RequestParameters.USERS_address_uuid)
    public void setAddressDB_ID(UUID addressDB_ID) {
        AddressDB_ID = addressDB_ID;
    }

    @JsonProperty(RequestParameters.USERS_friends_list_uuid)
    public void setFriendsListDB_ID(UUID friendsListDB_ID) {
        FriendsListDB_ID = friendsListDB_ID;
    }

    @JsonProperty(RequestParameters.USERS_user_level_uuid)
    public void setUser_LevelDB_ID(UUID user_LevelDB_ID) {
        User_LevelDB_ID = user_LevelDB_ID;
    }
}
