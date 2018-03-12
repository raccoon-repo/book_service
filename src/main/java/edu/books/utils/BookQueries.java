package edu.books.utils;

public final class BookQueries {
    private BookQueries(){
    }

    public static final String FIND_ALL = "Book.findAll";
    public static final String FIND_ALL_QUERY =
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a";

    public static final String FIND_BY_ID = "Book.findById";
    public static final String FIND_BY_ID_QUERY =
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a WHERE b.id=:id";

    public static final String FIND_BY_AUTHOR = "Book.findByAuthor";
    public static final String FIND_BY_AUTHOR_QUERY =
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a WHERE :author_id=a.id";

    public static final String FIND_BY_TITLE = "Book.findByTitle";
    public static final String FIND_BY_TITLE_QUERY =
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a WHERE b.title=:title";

    public static final String FIND_BY_GENRE = "Book.findByGenre";
    public static final String FIND_BY_GENRE_QUERY =
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a WHERE b.genre=:genre";


    public static final String FIND_BY_RATING = "Book.findByRating";
    public static final String FIND_BY_RATING_QERY =
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a WHERE b.rating=:rating";

    public static final String FIND_BY_DATE = "Book.findByDate";
    public static final String FIND_BY_DATE_QUERY =
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a WHERE b.publishDate=:date";
}
