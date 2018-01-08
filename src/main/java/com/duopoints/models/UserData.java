package com.duopoints.models;

import com.duopoints.models.core.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * This class will represent a single User retrieved from the Userdata view on the DB.
 * This class will contain information that are essential to the user, such as UUID for
 * address, level and friends list (junction table).
 */
public class UserData extends User {
    @JsonProperty(RequestParameters.USER_ADDRESS_adr_country)
    private String Adr_Country;

    @JsonProperty(RequestParameters.USER_ADDRESS_adr_city)
    private String Adr_City;

    @JsonProperty(RequestParameters.USER_ADDRESS_adr_region)
    private String Adr_Region;

    @JsonProperty(RequestParameters.USER_LEVEL_user_level_number)
    private int User_Level_Number;

    @JsonProperty(RequestParameters.RELATIONSHIP_relationship_uuid)
    private UUID Relationship_uuid;


    /************
     * GETTERS
     ************/

    @JsonProperty(RequestParameters.USER_ADDRESS_adr_country)
    public String getAdr_Country() {
        return Adr_Country;
    }

    @JsonProperty(RequestParameters.USER_ADDRESS_adr_city)
    public String getAdr_City() {
        return Adr_City;
    }

    @JsonProperty(RequestParameters.USER_ADDRESS_adr_region)
    public String getAdr_Region() {
        return Adr_Region;
    }

    @JsonProperty(RequestParameters.USER_LEVEL_user_level_number)
    public int getUser_Level_Number() {
        return User_Level_Number;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_relationship_uuid)
    public UUID getRelationship_uuid() {
        return Relationship_uuid;
    }

    /************
     * SETTERS
     ************/

    @JsonProperty(RequestParameters.USER_ADDRESS_adr_country)
    public void setAdr_Country(String adr_Country) {
        Adr_Country = adr_Country;
    }

    @JsonProperty(RequestParameters.USER_ADDRESS_adr_city)
    public void setAdr_City(String adr_City) {
        Adr_City = adr_City;
    }

    @JsonProperty(RequestParameters.USER_ADDRESS_adr_region)
    public void setAdr_Region(String adr_Region) {
        Adr_Region = adr_Region;
    }

    @JsonProperty(RequestParameters.USER_LEVEL_user_level_number)
    public void setUser_Level_Number(int user_Level_Number) {
        User_Level_Number = user_Level_Number;
    }

    @JsonProperty(RequestParameters.RELATIONSHIP_relationship_uuid)
    public void setRelationship_uuid(UUID relationship_uuid) {
        Relationship_uuid = relationship_uuid;
    }
}
