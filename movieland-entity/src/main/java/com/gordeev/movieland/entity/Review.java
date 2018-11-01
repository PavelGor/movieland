package com.gordeev.movieland.entity;

import java.util.Objects;

public class Review {
    private long id;
    private int movieId;
    private User user;
    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id &&
                movieId == review.movieId &&
                Objects.equals(text, review.text);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, movieId, text);
    }
}
