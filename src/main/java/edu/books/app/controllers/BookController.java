package edu.books.app.controllers;

import edu.books.app.model.BookQuery;
import edu.books.app.model.BookQueryHandler;
import edu.books.entities.Book;
import edu.books.services.books.BookService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/books")
public class BookController implements ApplicationContextAware {

    private static final String LOG_DELIM = "\n-------------------------------------------------------\n";
    private static final Logger log = Logger.getLogger(BookController.class);

    private ApplicationContext ctx;
    private BookService bookService;

    @RequestMapping(method = RequestMethod.GET)
    public String listAll(Model uiModel) {
        List<Book> books = bookService.findAll();

        StringBuilder model = new StringBuilder();
        for (Map.Entry<String, Object> e : uiModel.asMap().entrySet()) {
            model.append(e);
        }

        log.info("Model: " + model.toString());
        uiModel.addAttribute("books", books);
        model = new StringBuilder();

        for (Map.Entry<String, Object> e : uiModel.asMap().entrySet()) {
            model.append(e);
        }
        return "books/all";
    }

    @RequestMapping(params = "find", method = RequestMethod.GET)
    public String getForm(Model uiModel) {
        BookQuery bookQuery = new BookQuery();

        uiModel.addAttribute("bookQuery", bookQuery);
        return "books?find";
    }

    @RequestMapping(params = "find", method = RequestMethod.POST)
    public String findBook(@ModelAttribute("bookQuery") BookQuery query,
                           BindingResult bindingResult, Model uiModel)
    {
        BookQueryHandler handler = ctx.getBean("bookQueryHandler", BookQueryHandler.class);
        handler.setBookQuery(query);
        Set<Book> resultSet = handler.handle();

        uiModel.asMap().clear();
        uiModel.addAttribute("books", resultSet);

        return "books/all";
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public BookService getBookService() {
        return bookService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
