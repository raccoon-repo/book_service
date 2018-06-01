package edu.library.services.books;

import edu.library.entities.Book;
import edu.library.pagination.PagedDao;
import edu.library.pagination.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("bookPaginationService")
@Transactional
public class BookPaginationService
implements PaginationService<Book> {

    private PagedDao pagedDao;

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<Book> get(int page, int pageSize) {
        return (List<Book>) pagedDao.get(page, pageSize);
    }

    @Autowired
    @Qualifier("pagedBookDao")
    public void setPagedDao(PagedDao pagedDao) {
        this.pagedDao = pagedDao;
    }

    public PagedDao getPagedDao() {
        return pagedDao;
    }
}
