package edu.books.services.books;

import edu.books.entities.Author;
import edu.books.entities.Book;
import edu.books.services.authors.AuthorDao;
import edu.books.utils.OrderBy;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static edu.books.entities.Book.RatingShortcut;
import static edu.books.entities.Book.RatingShortcut.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("pagedBookDao")
@Transactional
public class PagedBookDaoImpl extends BookDaoImpl implements PagedBookDao  {

    private SessionFactory sessionFactory;
    private AuthorDao authorDao;

    @Override
    public List<Book> findAll(int offset, int quantity,
                              Optional<OrderBy> orderBy) {
        return null;
    }

    @Override
    public List<Book> findByTitle(String title, int offset,
            int quantity, Optional<OrderBy> orderBy) {
        return null;
    }

    @Override
    public List<Book> findByAuthor(Author author, int offset,
                                    int quantity, Optional<OrderBy> orderBy) {
        return null;
    }

    @Override
    public List<Book> findByGenre(Book.Genre genre, int offset,
                                  int quantity, Optional<OrderBy> orderBy) {
        return null;
    }

    @Override
    public List<Book> findByRating(RatingShortcut ratingShortcut, int offset,
                                   int quantity, Optional<OrderBy> orderBy) {
        return null;
    }

    @Override
    public List<Book> findByRating(float from, float to, int offset,
                                   int quantity, Optional<OrderBy> orderBy) {
        return null;
    }

    @Override
    public List<Book> findByPublishDate(Date publishDate, int offset,
                                        int quantity, Optional<OrderBy> orderBy) {
        return null;
    }

    @Override
    public List<Book> findByTags(Set<String> tags, int offset,
                                 int quantity, Optional<OrderBy> orderBy) {
        return null;
    }

    @Override
    @Resource(name = "authorDao")
    public void setAuthorDao(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public AuthorDao getAuthorDao() {
        return authorDao;
    }

    @Override
    @Resource(name = "hibernateSessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
