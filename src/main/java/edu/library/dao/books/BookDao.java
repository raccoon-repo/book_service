package edu.library.dao.books;

import edu.library.entities.Author;
import edu.library.entities.Book;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface BookDao {
    List<Book> findAll();
    Book findById(long id);

    List<Book> findByTitle(String title);
    List<Book> findByAuthor(Author author);
    List<Book> findByGenre(Book.Genre genre);
    List<Book> findByRating(Book.RatingShortcut ratingShortcut);
    List<Book> findByRating(float rating);
    List<Book> findByRating(float from, float to);
    List<Book> findByPublishDate(Date publishDate);
    List<Book> findByTags(Set<String> tags);

    Book save(Book book);
    Book update(Book book);
    void delete(Book book);
    void delete(long id);
}
