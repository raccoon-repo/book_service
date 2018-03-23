package edu.books.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class AppController {

    @RequestMapping(method = RequestMethod.GET)
    public String homePage() { return "homepage"; }
}
