package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.dao.CountryDao;
import com.gordeev.movieland.entity.Country;
import com.gordeev.movieland.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultCountryService implements CountryService {
    private CountryDao countryDao; //TODO: do it with cache - as  a genres

    @Autowired
    public DefaultCountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public Map<Integer, Country> getAllCountriesMap() {
        Map<Integer, Country> countriesMap = new HashMap<>();
        List<Country> countries = getAllCountries();

        for (Country country : countries) {
            countriesMap.put(country.getId(), country);
        }

        return countriesMap;
    }

    public List<Country> getAllCountries() {
        return countryDao.getAllCountries();
    }
}
