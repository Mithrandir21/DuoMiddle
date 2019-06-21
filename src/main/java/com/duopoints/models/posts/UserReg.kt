package com.duopoints.models.posts

import com.duopoints.RequestParameters
import com.fasterxml.jackson.annotation.JsonProperty

data class UserReg(
        @JsonProperty(RequestParameters.USER_REG_user_auth_provider) val auth_provider: String,
        @JsonProperty(RequestParameters.USER_REG_user_auth_id) val auth_id: String,
        @JsonProperty(RequestParameters.USER_REG_user_email) val email: String,
        @JsonProperty(RequestParameters.USER_REG_user_firstname) val firstname: String,
        @JsonProperty(RequestParameters.USER_REG_user_lastname) val lastname: String,
        @JsonProperty(RequestParameters.USER_REG_user_nickname) val nickname: String,
        @JsonProperty(RequestParameters.USER_REG_user_gender) val gender: Char? = null,
        @JsonProperty(RequestParameters.USER_REG_user_age) val age: Short = 0,
        @JsonProperty(RequestParameters.USER_REG_user_country) val country: String,
        @JsonProperty(RequestParameters.USER_REG_user_city) val city: String
)