package com.duopoints.models.geo.wrappers;

import com.duopoints.models.geo.City;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CityWrapper {
    @JsonProperty("totalResultsCount")
    private int resultCount;

    @JsonProperty("geonames")
    private City[] cities;

    @JsonProperty("totalResultsCount")
    public int getResultCount() {
        return resultCount;
    }

    @JsonProperty("geonames")
    public City[] getCities() {
        return cities;
    }

    @JsonProperty("totalResultsCount")
    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    @JsonProperty("geonames")
    public void setCities(City[] cities) {
        this.cities = cities;
    }
}
