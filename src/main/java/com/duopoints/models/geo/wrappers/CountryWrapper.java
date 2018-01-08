package com.duopoints.models.geo.wrappers;

import com.duopoints.models.geo.Country;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryWrapper {
    @JsonProperty("geonames")
    private Country[] countries;

    @JsonProperty("geonames")
    public Country[] getCountries() {
        return countries;
    }

    @JsonProperty("geonames")
    public void setCountries(Country[] countries) {
        this.countries = countries;
    }
}
