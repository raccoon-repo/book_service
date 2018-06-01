package edu.books.services.books;

import edu.books.entities.Author;
import edu.books.entities.Book;
import edu.books.services.authors.AuthorDao;
import edu.books.utils.BookQueries;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

import static edu.books.entities.Book.RatingShortcut.*;

@Repository("bookDao")
@Transactional
public class BookDaoImpl implements BookDao {

    private static final Logger log = Logger.getLogger(BookDaoImpl.class);

    @Resource(name = "hibernateSessionFactory")
    private SessionFactory sessionFactory;

    @Resource(name = "authorDao")
    private AuthorDao authorDao;

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findAll() {
        return (List<Book>) sessionFactory.getCurrentSession()
               .getNamedQuery(BookQueries.FIND_ALL)
               .list();
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(long id) {

        return sessionFactory.getCurrentSession()
               .get(Book.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByTitle(String title) {
        return (List<Book>) sessionFactory.getCurrentSession()
               .getNamedQuery(BookQueries.FIND_BY_TITLE)
               .setParameter("title", '%' + title + '%')
               .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByAuthor(Author author) {
        if(author.getId() <= 0) {
            return null;
        }
        return (List<Book>) sessionFactory.getCurrentSession()
               .getNamedQuery(BookQueries.FIND_BY_AUTHOR)
               .setParameter("author_id", author.getId())
               .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByPublishDate(Date publishDate) {
        return (List<Book>) sessionFactory.getCurrentSession()
               .getNamedQuery(BookQueries.FIND_BY_DATE)
               .setParameter("date", publishDate)
               .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByTags(Set<String> tags) {
        Session session = sessionFactory.getCurrentSession();

        Set<BigInteger> ids = new HashSet<>();
        for(String tag: tags) {
            tag = tag.toLowerCase().trim();
            ids.addAll(
                session
                .createNativeQuery(BookQueries.FIND_ID_BY_TAG_QUERY)
                .setParameter("tag", tag)
                .getResultList()
            );
        }

        List<Book> books = new ArrayList<>();

        for(BigInteger id: ids) {
            books.add(findById(id.longValue()));
        }

        return books;
    }

    @Override
    public Book save(Book book) {
        sessionFactory.getCurrentSession().saveOrUpdate(book);
        return book;
    }

    @Override
    public Book update(Book book) {
        sessionFactory.getCurrentSession().update(book);
        return book;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByGenre(Book.Genre genre) {
        return (List<Book>) sessionFactory.getCurrentSession()
               .getNamedQuery(BookQueries.FIND_BY_GENRE)
               .setParameter("genre", genre)
               .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByRating(Book.RatingShortcut ratingShortcut) {
        switch (ratingShortcut) {
            case WORST:
                return findByRating(0.0f, 2.0f);
            case BAD:
                return findByRating(2.0f, 4.0f);
            case OKAY:
                return findByRating(4.0f, 6.0f);
            case GOOD:
                return findByRating(6.0f, 8.0f);
            case BEST:
                return findByRating(8.0f, 10.0f);
        }

        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByRating(float rating) {
        return (List<Book>) sessionFactory.getCurrentSession()
                .getNamedQuery(BookQueries.FIND_BY_RATING)
                .setParameter("rating", rating)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByRating(float from, float to) {
        if(from < 0 || to < 0 || from < to || from > 10 || to > 10) {
            return null;
        }

        return (List<Book>) sessionFactory.getCurrentSession()
                .getNamedQuery(BookQueries.FIND_BY_RATING_SHORTCUT)
                .setParameter("left", from).setParameter("right", to)
                .list();
    }

    @Override
    public void delete(long id) {
        Book book = findById(id);
        delete(book);
    }

    @Override
    public void delete(Book book) {
        if(book.getId() <= 0)
            return;
        sessionFactory.getCurrentSession().delete(book);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setAuthorDao(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public AuthorDao getAuthorDao() {
        return authorDao;
    }
}
