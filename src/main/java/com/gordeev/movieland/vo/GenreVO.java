package com.gordeev.movieland.vo;

import java.util.List;

public class GenreVO {
    private int movieId;
    private List<Integer> intGenres;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public List<Integer> getIntGenres() {
        return intGenres;
    }

    public void setIntGenres(List<Integer> intGenres) {
        this.intGenres = intGenres;
    }
}
