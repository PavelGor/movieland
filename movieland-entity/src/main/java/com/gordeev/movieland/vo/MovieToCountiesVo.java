package com.gordeev.movieland.vo;

import com.gordeev.movieland.entity.Country;

import java.util.List;

public class MovieToCountiesVo {
    private int movieId;
    private List<Country> countries;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
