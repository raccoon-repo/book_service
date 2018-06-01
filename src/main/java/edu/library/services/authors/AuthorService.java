package edu.library.services.authors;

import edu.library.entities.Author;

import java.util.List;

public interface AuthorService {
    Author findById(long id);

    List<Author> findAll();
    List<Author> findByName(String firstName, String lastName);
}
