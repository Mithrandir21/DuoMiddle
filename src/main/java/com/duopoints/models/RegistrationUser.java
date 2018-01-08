package com.duopoints.models;

import com.duopoints.models.enums.AuthProvider;
import com.duopoints.models.enums.UserGender;

/**
 * This class is used when a user wishes to Login with any social login API.
 * The information should be populated from the response from the success login and then passed
 * to whatever registration process that will determine if the user already exists and should
 * simply be logged in or should be registered.
 */
public class RegistrationUser {
    public AuthProvider authProvider;

    public String user_auth_ID;
    public String user_email;
    public String user_firstname;
    public String user_lastname;
    public String user_nickname;
    public UserGender userGender;
    public int user_age;
}
