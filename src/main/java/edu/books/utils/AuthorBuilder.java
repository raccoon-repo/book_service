package edu.books.utils;

import edu.books.entities.Author;
import edu.books.entities.Book;

import java.util.LinkedList;
import java.util.List;

public class AuthorBuilder {

    private String firstName;

    private String lastName;

    public AuthorBuilder name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

        return this;
    }

    public Author build() {
        Author author = new Author();

        author.setFirstName(firstName);
        author.setLastName(lastName);

        return author;
    }

}
