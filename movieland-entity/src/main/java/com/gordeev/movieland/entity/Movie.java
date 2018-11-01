package com.gordeev.movieland.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Objects;

public class Movie {
    private int id;
    private String nameRussian;
    private String nameNative;
    private int yearOfRelease;
    private String description;
    private double rating;
    private double price;
    private String picturePath;
    private List<Country> countries;
    private List<Genre> genres;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Review> reviews;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameRussian() {
        return nameRussian;
    }

    public void setNameRussian(String nameRussian) {
        this.nameRussian = nameRussian;
    }

    public String getNameNative() {
        return nameNative;
    }

    public void setNameNative(String nameNative) {
        this.nameNative = nameNative;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                yearOfRelease == movie.yearOfRelease &&
                Double.compare(movie.rating, rating) == 0 &&
                Double.compare(movie.price, price) == 0 &&
                Objects.equals(nameRussian, movie.nameRussian) &&
                Objects.equals(nameNative, movie.nameNative) &&
                Objects.equals(description, movie.description) &&
                Objects.equals(picturePath, movie.picturePath) &&
                Objects.equals(countries, movie.countries) &&
                Objects.equals(genres, movie.genres);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, nameRussian, nameNative, yearOfRelease, description, rating, price, picturePath, countries, genres);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Movie{");
        sb.append("id=").append(id);
        sb.append(", nameRussian='").append(nameRussian).append('\'');
        sb.append(", nameNative='").append(nameNative).append('\'');
        sb.append(", yearOfRelease=").append(yearOfRelease);
        sb.append(", description='").append(description).append('\'');
        sb.append(", rating=").append(rating);
        sb.append(", price=").append(price);
        sb.append(", picturePath='").append(picturePath).append('\'');
        sb.append(", countries=").append(countries);
        sb.append(", genres=").append(genres);
        sb.append('}');
        return sb.toString();
    }
}
