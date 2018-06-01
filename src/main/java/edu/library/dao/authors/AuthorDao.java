package edu.library.dao.authors;

import edu.library.entities.Author;

import java.util.List;

public interface AuthorDao {
    Author findById(long id);

    List<Author> findAll();
    List<Author> findByName(String firstName, String lastName);
}
