package edu.books.services.books;

import edu.books.entities.Author;
import edu.books.entities.Book;

import java.util.Date;
import java.util.List;

public interface BookService {
    List<Book> findAll();
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(Author author);
    List<Book> findByPublishDate(Date date);
    List<Book> findByRating(Book.Rating rating);
    List<Book> findByGenre(Book.Genre genre);

    void delete(Book book);
    Book save(Book book);
    Book update(Book book);
}
