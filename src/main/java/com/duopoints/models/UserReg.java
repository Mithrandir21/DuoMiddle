package com.duopoints.models;

import com.duopoints.models.enums.UserGender;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserReg {

    @JsonProperty(RequestParameters.USER_REG_user_country)
    public String country;

    @JsonProperty(RequestParameters.USER_REG_user_city)
    public String city;

    @JsonProperty(RequestParameters.USER_REG_user_auth_provider)
    public String auth_provider;

    @JsonProperty(RequestParameters.USER_REG_user_auth_id)
    public String auth_id;

    @JsonProperty(RequestParameters.USER_REG_user_email)
    public String email;

    @JsonProperty(RequestParameters.USER_REG_user_firstname)
    public String firstname;

    @JsonProperty(RequestParameters.USER_REG_user_lastname)
    public String lastname;

    @JsonProperty(RequestParameters.USER_REG_user_nickname)
    public String nickname;

    @JsonProperty(RequestParameters.USER_REG_user_gender)
    public UserGender gender;

    @JsonProperty(RequestParameters.USER_REG_user_age)
    public short age;


    /************
     * GETTERS
     ************/

    @JsonProperty(RequestParameters.USER_REG_user_country)
    public String getCountry() {
        return country;
    }

    @JsonProperty(RequestParameters.USER_REG_user_city)
    public String getCity() {
        return city;
    }

    @JsonProperty(RequestParameters.USER_REG_user_auth_provider)
    public String getAuth_provider() {
        return auth_provider;
    }

    @JsonProperty(RequestParameters.USER_REG_user_auth_id)
    public String getAuth_id() {
        return auth_id;
    }

    @JsonProperty(RequestParameters.USER_REG_user_email)
    public String getEmail() {
        return email;
    }

    @JsonProperty(RequestParameters.USER_REG_user_firstname)
    public String getFirstname() {
        return firstname;
    }

    @JsonProperty(RequestParameters.USER_REG_user_lastname)
    public String getLastname() {
        return lastname;
    }

    @JsonProperty(RequestParameters.USER_REG_user_nickname)
    public String getNickname() {
        return nickname;
    }

    @JsonProperty(RequestParameters.USER_REG_user_gender)
    public UserGender getGender() {
        return gender;
    }

    @JsonProperty(RequestParameters.USER_REG_user_age)
    public short getAge() {
        return age;
    }


    /************
     * SETTERS
     ************/

    @JsonProperty(RequestParameters.USER_REG_user_country)
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty(RequestParameters.USER_REG_user_city)
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty(RequestParameters.USER_REG_user_auth_provider)
    public void setAuth_provider(String auth_provider) {
        this.auth_provider = auth_provider;
    }

    @JsonProperty(RequestParameters.USER_REG_user_auth_id)
    public void setAuth_id(String auth_id) {
        this.auth_id = auth_id;
    }

    @JsonProperty(RequestParameters.USER_REG_user_email)
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty(RequestParameters.USER_REG_user_firstname)
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @JsonProperty(RequestParameters.USER_REG_user_lastname)
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @JsonProperty(RequestParameters.USER_REG_user_nickname)
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @JsonProperty(RequestParameters.USER_REG_user_gender)
    public void setGender(UserGender gender) {
        this.gender = gender;
    }

    @JsonProperty(RequestParameters.USER_REG_user_age)
    public void setAge(short age) {
        this.age = age;
    }
}