package edu.books.app.controllers;

import edu.books.app.model.BookQuery;
import edu.books.entities.Book;
import edu.books.services.books.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    @RequestMapping(params = "find", method = RequestMethod.GET)
    public String findForm(Model uiModel) {
        BookQuery bookQuery = new BookQuery();
        uiModel.addAttribute("bookQuery", bookQuery);

        return "books?find";
    }



    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public BookService getBookService() {
        return bookService;
    }
}
