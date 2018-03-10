package edu.books.services;

import edu.books.entities.Author;
import edu.books.entities.Book;
import edu.books.utils.BookQueries;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("defaultBookManager")
@Transactional
public class BookManagerImpl implements BookManager{

    @Resource(name = "hibernateSessionFactory")
    private SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        Session session = sessionFactory.getCurrentSession();

        return (List<Book>) session.getNamedQuery(BookQueries.FIND_ALL).list();
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(long id) {
        Session session = sessionFactory.getCurrentSession();

        Book b = (Book) session.getNamedQuery(BookQueries.FIND_BY_ID)
                .setParameter("id", id).uniqueResult();

        return b;
    }

    @Override
    @Transactional(readOnly = true)
    public Book findByTitle(String title) {
        return (Book) sessionFactory.getCurrentSession()
                .getNamedQuery(BookQueries.FIND_BY_TITLE)
                .setParameter("title", title).uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Book findByAuthor(Author author) {
        Session session = sessionFactory.getCurrentSession();

        return (Book) session.getNamedQuery(BookQueries.FIND_BY_AUTHOR)
                      .setParameter("author", author).uniqueResult();
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
    public void delete(Book book) {
        sessionFactory.getCurrentSession().delete(book);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
