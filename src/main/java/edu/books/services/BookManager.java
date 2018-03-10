package edu.books.services;

import edu.books.entities.Author;
import edu.books.entities.Book;

import java.util.List;

public interface BookManager {
    List<Book> findAll();
    Book findById(long id);
    Book findByTitle(String title);
    Book findByAuthor(Author author);

    Book save(Book book);
    Book update(Book book);
    void delete(Book book);
}
