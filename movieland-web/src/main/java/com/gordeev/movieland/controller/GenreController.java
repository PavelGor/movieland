package com.gordeev.movieland.controller;

import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {
    private GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected List<Genre> getAllGenre() {
        return genreService.getAll();
    }
}
