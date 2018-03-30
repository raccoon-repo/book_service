package edu.books.services.books;

import edu.books.entities.Author;
import edu.books.entities.Book;
import edu.books.services.authors.AuthorDao;
import edu.books.utils.BookQueries;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

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

        return (List<Book>) getSession()
               .getNamedQuery(BookQueries.FIND_ALL)
               .list();
    }

    @Override
    public Book findById(long id) {

        return getSession().get(Book.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByTitle(String title) {

        return (List<Book>) getSession()
               .getNamedQuery(BookQueries.FIND_BY_TITLE)
               .setParameter("title", title)
               .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByAuthor(Author author) {
        if(author.getId() <= 0)
            return null;
        return (List<Book>) getSession()
               .getNamedQuery(BookQueries.FIND_BY_AUTHOR)
               .setParameter("author_id", author.getId())
               .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByPublishDate(Date publishDate) {
        return (List<Book>) getSession()
               .getNamedQuery(BookQueries.FIND_BY_DATE)
               .setParameter("date", publishDate)
               .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByTags(Set<String> tags) {
        Session session = getSession();

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
        getSession().saveOrUpdate(book);
        return book;
    }

    @Override
    public Book update(Book book) {
        getSession().update(book);
        return book;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByGenre(Book.Genre genre) {
        return (List<Book>) getSession()
               .getNamedQuery(BookQueries.FIND_BY_GENRE)
               .setParameter("genre", genre)
               .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByRating(Book.Rating rating) {
        return (List<Book>) getSession()
               .getNamedQuery(BookQueries.FIND_BY_RATING)
               .setParameter("rating", rating)
               .list();
    }

    @Override
    public void delete(long id) {
        Book book = findById(id);
        delete(book);
    }

    @Override
    public void delete(Book book) {
        if(book.getId() <= 0) {
            return;
        }
        getSession().delete(book);
    }
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        Session session;
        log.debug("trying to obtain current session");
        try {
            session = sessionFactory.getCurrentSession();
            log.debug("obtained session successfully");
        } catch (HibernateException e) {
            log.error("Exception:", e);
            session = sessionFactory.openSession();
        }

        return session;
    }
}
