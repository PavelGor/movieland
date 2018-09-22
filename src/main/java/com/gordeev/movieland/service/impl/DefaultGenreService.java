package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.cache.GenreCache;
import com.gordeev.movieland.dao.GenreDao;
import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultGenreService implements GenreService {
    private GenreCache genreCache;

    @Autowired
    public DefaultGenreService(GenreCache genreCache) {
        this.genreCache = genreCache;
    }

    @Override
    public List<Genre> getAllGenre() {
        return genreCache.getAllGenre();
    }

    @Override
    public Map<Integer, Genre> getAllGenresMap() {
        Map<Integer, Genre> genresMap = new HashMap<>();
        List<Genre> genres = getAllGenre();

        for (Genre genre : genres) {
            genresMap.put(genre.getId(), genre);
        }

        return genresMap;
    }


}