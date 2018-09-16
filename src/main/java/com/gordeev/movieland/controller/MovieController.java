package com.gordeev.movieland.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MovieController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    protected String showHello() {
        return "Hello Movie Land";
    }
}
