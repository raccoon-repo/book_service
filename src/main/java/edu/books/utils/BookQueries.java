package edu.books.utils;

public class BookQueries {
    public static final String FIND_ALL = "Book.findAll";
    public static final String FIND_ALL_QUERY =
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a";

    public static final String FIND_BY_ID = "Book.findById";
    public static final String FIND_BY_ID_QUERY =
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a WHERE b.id=:id";

    public static final String FIND_BY_AUTHOR = "Book.findByAuthor";
    public static final String FIND_BY_AUTHOR_QUERY =
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a WHERE a=:author";

    public static final String FIND_BY_TITLE = "Book.findByTitle";
    public static final String FIND_BY_TITLE_QUERY =
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a WHERE b.title=:title";
}
