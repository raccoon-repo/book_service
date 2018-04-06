package edu.books.app.model;

import edu.books.entities.Book;
import edu.books.services.books.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("pagedBoolListHolder")
@Scope("prototype")
public class PagedBookListHolder implements PagedListHolder<Book> {
    private BookService bookServices;

    @Override
    public void setPageSize(int size) {

    }

    @Override
    public int getPageSize() {
        return 0;
    }

    @Override
    public List<Book> getPagedList(int page) {
        return null;
    }

    @Autowired
    public void setBookServices(BookService bookServices) {
        this.bookServices = bookServices;
    }
}
