package edu.library.services.books;

import edu.library.entities.Author;
import edu.library.entities.Book;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface BookService {
    Book findById(long id);

    List<Book> findAll();
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(Author author);
    List<Book> findByPublishDate(Date date);
    List<Book> findByRating(Book.RatingShortcut ratingShortcut);
    List<Book> findByRating(float rating);
    List<Book> findByGenre(Book.Genre genre);
    List<Book> findByTags(Set<String> tags);

    void delete(Book book);
    Book save(Book book);
    Book update(Book book);
}
