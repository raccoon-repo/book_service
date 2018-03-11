package edu.books.services.books;

import edu.books.entities.Author;
import edu.books.entities.Book;
import edu.books.services.authors.AuthorDao;
import edu.books.utils.BookQueries;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("bookDao")
public class BookDaoImpl implements BookDao {

    @Resource(name = "hibernateSessionFactory")
    private SessionFactory sessionFactory;

    @Resource(name = "authorDao")
    private AuthorDao authorDao;

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findAll() {
        Session session = sessionFactory.getCurrentSession();

        return (List<Book>) session.getNamedQuery(BookQueries.FIND_ALL).list();
    }

    @Override
    public Book findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")

    public List<Book> findByTitle(String title) {
        return (List<Book>) sessionFactory.getCurrentSession()
                .getNamedQuery(BookQueries.FIND_BY_TITLE)
                .setParameter("title", title).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByAuthor(Author author) {
        Session session = sessionFactory.getCurrentSession();

        if(author.getId() <= 0) {
            List<Author> authors = authorDao.find(author);
            List<Book> books = new ArrayList<>();

            for (Author a : authors)
                books.addAll(session.getNamedQuery(BookQueries.FIND_BY_AUTHOR)
                        .setParameter("author", a).list());
            return books;
        } else
            return session.getNamedQuery(BookQueries.FIND_BY_AUTHOR)
                    .setParameter("author", author).list();
    }

    @Override
    public List<Book> findByPublishDate(Date publishDate) {
        return null;
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
    public List<Book> findByGenre(Book.Genre genre) {
        return null;
    }

    @Override
    public List<Book> findByRating(Book.Rating rating) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void delete(Book book) {
        sessionFactory.getCurrentSession().delete(book);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
