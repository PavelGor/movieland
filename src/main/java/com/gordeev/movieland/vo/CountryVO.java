package com.gordeev.movieland.vo;

import java.util.List;

public class CountryVO {
    private int movieId;
    private List<Integer> intCountries;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public List<Integer> getIntCountries() {
        return intCountries;
    }

    public void setIntCountries(List<Integer> intCountries) {
        this.intCountries = intCountries;
    }
}
