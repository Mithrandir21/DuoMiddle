package com.duopoints.models.geo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class City {
    @JsonProperty("adminCode1")
    private String adminCode1;

    @JsonProperty("lng")
    private String lng;

    @JsonProperty("geonameId")
    private String geonameId;

    @JsonProperty("toponymName")
    private String toponymName;

    @JsonProperty("countryId")
    private String countryId;

    @JsonProperty("fcl")
    private String fcl;

    @JsonProperty("population")
    private int population;

    @JsonProperty("numberOfChildren")
    private int numberOfChildren;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("name")
    private String name;

    @JsonProperty("fclName")
    private String fclName;

    @JsonProperty("countryName")
    private String countryName;

    @JsonProperty("fcodeName")
    private String fcodeName;

    @JsonProperty("adminName1")
    private String adminName1;

    @JsonProperty("lat")
    private String lat;

    @JsonProperty("fcode")
    private String fcode;


    /************
     * GETTERS
     ************/

    @JsonProperty("adminCode1")
    public String getAdminCode1() {
        return adminCode1;
    }

    @JsonProperty("lng")
    public String getLng() {
        return lng;
    }

    @JsonProperty("geonameId")
    public String getGeonameId() {
        return geonameId;
    }

    @JsonProperty("toponymName")
    public String getToponymName() {
        return toponymName;
    }

    @JsonProperty("countryId")
    public String getCountryId() {
        return countryId;
    }

    @JsonProperty("fcl")
    public String getFcl() {
        return fcl;
    }

    @JsonProperty("population")
    public int getPopulation() {
        return population;
    }

    @JsonProperty("numberOfChildren")
    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    @JsonProperty("countryCode")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("fclName")
    public String getFclName() {
        return fclName;
    }

    @JsonProperty("countryName")
    public String getCountryName() {
        return countryName;
    }

    @JsonProperty("fcodeName")
    public String getFcodeName() {
        return fcodeName;
    }

    @JsonProperty("adminName1")
    public String getAdminName1() {
        return adminName1;
    }

    @JsonProperty("lat")
    public String getLat() {
        return lat;
    }

    @JsonProperty("fcode")
    public String getFcode() {
        return fcode;
    }


    /************
     * SETTERS
     ************/

    @JsonProperty("adminCode1")
    public void setAdminCode1(String adminCode1) {
        this.adminCode1 = adminCode1;
    }

    @JsonProperty("lng")
    public void setLng(String lng) {
        this.lng = lng;
    }

    @JsonProperty("geonameId")
    public void setGeonameId(String geonameId) {
        this.geonameId = geonameId;
    }

    @JsonProperty("toponymName")
    public void setToponymName(String toponymName) {
        this.toponymName = toponymName;
    }

    @JsonProperty("countryId")
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @JsonProperty("fcl")
    public void setFcl(String fcl) {
        this.fcl = fcl;
    }

    @JsonProperty("population")
    public void setPopulation(int population) {
        this.population = population;
    }

    @JsonProperty("numberOfChildren")
    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    @JsonProperty("countryCode")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("fclName")
    public void setFclName(String fclName) {
        this.fclName = fclName;
    }

    @JsonProperty("countryName")
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @JsonProperty("fcodeName")
    public void setFcodeName(String fcodeName) {
        this.fcodeName = fcodeName;
    }

    @JsonProperty("adminName1")
    public void setAdminName1(String adminName1) {
        this.adminName1 = adminName1;
    }

    @JsonProperty("lat")
    public void setLat(String lat) {
        this.lat = lat;
    }

    @JsonProperty("fcode")
    public void setFcode(String fcode) {
        this.fcode = fcode;
    }
}