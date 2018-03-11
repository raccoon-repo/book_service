package edu.books.services.books;

import edu.books.entities.Author;
import edu.books.entities.Book;

import java.util.Date;
import java.util.List;

public interface BookDao {
    List<Book> findAll();
    Book findById(long id);

    List<Book> findByTitle(String title);
    List<Book> findByAuthor(Author author);
    List<Book> findByGenre(Book.Genre genre);
    List<Book> findByRating(Book.Rating rating);
    List<Book> findByPublishDate(Date publishDate);

    Book save(Book book);
    Book update(Book book);
    void delete(Book book);
    void delete(long id);
}
