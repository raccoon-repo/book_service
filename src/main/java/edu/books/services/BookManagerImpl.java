package edu.books.services;

import edu.books.entities.Author;
import edu.books.entities.Book;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Set;

@Service("defaultBookManager")
@Transactional
public class BookManagerImpl implements BookManager{

    @Resource(name = "hibernateSessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public Set<Book> findAll() {
        return null;
    }

    @Override
    public Book findById(long id) {
        return null;
    }

    @Override
    public Book findByTitle(String title) {
        return null;
    }

    @Override
    public Book findByAuthor(Author author) {
        return null;
    }

    @Override
    public Book save(Book book) {
        return null;
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public void delete(Book book) {

    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
