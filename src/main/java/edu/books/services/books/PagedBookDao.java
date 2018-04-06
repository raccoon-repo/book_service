package edu.books.services.books;

import edu.books.entities.Author;
import edu.books.entities.Book;
import edu.books.utils.OrderBy;

import java.util.*;

public interface PagedBookDao extends BookDao {
    List<Book> findAll(int offset, int quantity, Optional<OrderBy> orderBy);

    List<Book> findByTitle(String title, int offset, int quantity, Optional<OrderBy> orderBy);
    List<Book> findByAuthor(Author author, int offset, int quantity, Optional<OrderBy> orderBy);
    List<Book> findByGenre(Book.Genre genre, int offset, int quantity, Optional<OrderBy> orderBy);
    List<Book> findByRating(Book.RatingShortcut ratingShortcut, int offset, int quantity, Optional<OrderBy> orderBy);
    List<Book> findByRating(float from, float to, int offset, int quantity, Optional<OrderBy> orderBy);
    List<Book> findByPublishDate(Date publishDate, int offset, int quantity,Optional<OrderBy> orderBy);
    List<Book> findByTags(Set<String> tags, int offset, int quantity, Optional<OrderBy> orderBy);

}
