package com.gordeev.movieland.vo;

import com.gordeev.movieland.entity.Genre;

import java.util.List;

public class MovieToGenresVO {
    private int movieId;
    private List<Genre> genres;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
