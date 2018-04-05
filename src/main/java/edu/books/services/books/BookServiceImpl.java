package edu.books.services.books;

import edu.books.entities.Author;
import edu.books.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    @Override
    public Book findById(long id) {
        return bookDao.findById(id);
    }

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
    @Transactional(readOnly = true)
    public List<Book> findByRating(Book.Rating rating) {
        return bookDao.findByRating(rating);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByGenre(Book.Genre genre) {
        return bookDao.findByGenre(genre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByTags(Set<String> tags) {
        return bookDao.findByTags(tags);
    }

    @Override
    public void delete(Book book) {
        bookDao.delete(book);
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
