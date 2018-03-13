package edu.books.services.books;

import edu.books.entities.Author;
import edu.books.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByTitle(String title) {
        return bookDao.findByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByAuthor(Author author) {
        return bookDao.findByAuthor(author);
    }

    @Override
    @Transactional(readOnly =  true)
    public List<Book> findByPublishDate(Date date) {
        return bookDao.findByPublishDate(date);
    }

    @Override
    public void delete(Book book) {
        bookDao.delete(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByRating(Book.Rating rating) {
        return bookDao.findByRating(rating);
    }

    @Override
    public List<Book> findByGenre(Book.Genre genre) {
        return bookDao.findByGenre(genre);
    }

    @Override
    public Book save(Book book) {
        return bookDao.save(book);
    }

    @Override
    public Book update(Book book) {
        return bookDao.update(book);
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    @Autowired
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
