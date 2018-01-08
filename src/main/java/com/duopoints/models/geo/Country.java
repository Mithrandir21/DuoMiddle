package com.duopoints.models.geo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country {
    @JsonProperty("continent")
    private String continent;

    @JsonProperty("capital")
    private String capital;

    @JsonProperty("languages")
    private String languages;

    @JsonProperty("geonameId")
    private int geonameId;

    @JsonProperty("isoAlpha3")
    private String isoAlpha3;

    @JsonProperty("fipsCode")
    private String fipsCode;

    @JsonProperty("population")
    private String population;

    @JsonProperty("isoNumeric")
    private String isoNumeric;

    @JsonProperty("areaInSqKm")
    private String areaInSqKm;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("countryName")
    private String countryName;

    @JsonProperty("continentName")
    private String continentName;

    @JsonProperty("currencyCode")
    private String currencyCode;

    @JsonProperty("south")
    private double south;

    @JsonProperty("north")
    private double north;

    @JsonProperty("east")
    private double east;

    @JsonProperty("west")
    private double west;


    /************
     * GETTERS
     ************/

    @JsonProperty("continent")
    public String getContinent() {
        return continent;
    }

    @JsonProperty("capital")
    public String getCapital() {
        return capital;
    }

    @JsonProperty("languages")
    public String getLanguages() {
        return languages;
    }

    @JsonProperty("geonameId")
    public int getGeonameId() {
        return geonameId;
    }

    @JsonProperty("isoAlpha3")
    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    @JsonProperty("fipsCode")
    public String getFipsCode() {
        return fipsCode;
    }

    @JsonProperty("population")
    public String getPopulation() {
        return population;
    }

    @JsonProperty("isoNumeric")
    public String getIsoNumeric() {
        return isoNumeric;
    }

    @JsonProperty("areaInSqKm")
    public String getAreaInSqKm() {
        return areaInSqKm;
    }

    @JsonProperty("countryCode")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("countryName")
    public String getCountryName() {
        return countryName;
    }

    @JsonProperty("continentName")
    public String getContinentName() {
        return continentName;
    }

    @JsonProperty("currencyCode")
    public String getCurrencyCode() {
        return currencyCode;
    }

    @JsonProperty("south")
    public double getSouth() {
        return south;
    }

    @JsonProperty("north")
    public double getNorth() {
        return north;
    }

    @JsonProperty("east")
    public double getEast() {
        return east;
    }

    @JsonProperty("west")
    public double getWest() {
        return west;
    }


    /************
     * SETTERS
     ************/

    @JsonProperty("continent")
    public void setContinent(String continent) {
        this.continent = continent;
    }

    @JsonProperty("capital")
    public void setCapital(String capital) {
        this.capital = capital;
    }

    @JsonProperty("languages")
    public void setLanguages(String languages) {
        this.languages = languages;
    }

    @JsonProperty("geonameId")
    public void setGeonameId(int geonameId) {
        this.geonameId = geonameId;
    }

    @JsonProperty("isoAlpha3")
    public void setIsoAlpha3(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    @JsonProperty("fipsCode")
    public void setFipsCode(String fipsCode) {
        this.fipsCode = fipsCode;
    }

    @JsonProperty("population")
    public void setPopulation(String population) {
        this.population = population;
    }

    @JsonProperty("isoNumeric")
    public void setIsoNumeric(String isoNumeric) {
        this.isoNumeric = isoNumeric;
    }

    @JsonProperty("areaInSqKm")
    public void setAreaInSqKm(String areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    @JsonProperty("countryCode")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonProperty("countryName")
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @JsonProperty("continentName")
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    @JsonProperty("currencyCode")
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @JsonProperty("south")
    public void setSouth(double south) {
        this.south = south;
    }

    @JsonProperty("north")
    public void setNorth(double north) {
        this.north = north;
    }

    @JsonProperty("east")
    public void setEast(double east) {
        this.east = east;
    }

    @JsonProperty("west")
    public void setWest(double west) {
        this.west = west;
    }
}
