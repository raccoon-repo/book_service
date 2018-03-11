package edu.books.services.authors;

import edu.books.entities.Author;

import java.util.List;

public interface AuthorDao {
    Author findById(long id);

    List<Author> findAll();
    List<Author> find(Author author);
    List<Author> findByName(String firstName, String lastName);
}
