package com.gordeev.movieland.service;

import com.gordeev.movieland.entity.Country;

import java.util.List;
import java.util.Map;

public interface CountryService {
    Map<Integer,Country> getAllCountriesMap();
    List<Country> getAllCountries();
}
