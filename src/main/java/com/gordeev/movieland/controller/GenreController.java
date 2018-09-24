package com.gordeev.movieland.controller;

import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/v1/genre")
public class GenreController {
    private GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    protected List<Genre> getAllGenre() {
        return genreService.getAll();
    }
}
