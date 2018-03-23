package edu.books.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "book-service/welcome";
    }
}
