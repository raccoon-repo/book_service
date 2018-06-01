package edu.library.springmvc.controllers;

import edu.library.springmvc.model.BookQuery;
import edu.library.springmvc.model.BookQueryHandler;
import edu.library.entities.Book;
import edu.library.services.books.BookService;
import edu.library.pagination.PaginationService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/books")
public class BookController implements ApplicationContextAware {

    private static final String LOG_DELIM = "\n-------------------------------------------------------\n";
    private static final Logger log = Logger.getLogger(BookController.class);

    private ApplicationContext ctx;

    private BookService bookService;

    private PaginationService<Book> paginationService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listAll(HttpServletRequest request, Model uiModel) {
        StopWatch stopWatch = new StopWatch();
        ModelAndView modelAndView = new ModelAndView("library/all");
        int page = 1;

        if(request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        if (page <= 1)
            page = 1;

        stopWatch.start();
        List<Book> books = paginationService.get(page, 7);
        stopWatch.stop();
        request.setAttribute("currentPage", page);
        uiModel.addAttribute("books", books);
        return modelAndView;
    }

    @RequestMapping(params = "find", method = RequestMethod.GET)
    public String getForm(Model uiModel) {
        BookQuery bookQuery = new BookQuery();

        uiModel.addAttribute("bookQuery", bookQuery);
        return "library?find";
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

        return "library/all";
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setPaginationService(PaginationService<Book> paginationService) {
        this.paginationService = paginationService;
    }

    public BookService getBookService() {
        return bookService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
