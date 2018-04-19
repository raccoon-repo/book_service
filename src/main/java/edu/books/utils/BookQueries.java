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
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a WHERE b.title LIKE :title";

    public static final String FIND_BY_GENRE = "Book.findByGenre";
    public static final String FIND_BY_GENRE_QUERY =
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a WHERE b.genre=:genre";

    public static final String FIND_BY_RATING = "Book.findByRating";
    public static final String FIND_BY_RATING_QUERY =
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a WHERE b.rating=:rating";

    public static final String FIND_BY_RATING_SHORTCUT = "Book.findByRatingShortcut";
    public static final String FIND_BY_RATING_SHORTCUT_QUERY =
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a WHERE b.rating >= :left AND b.rating <= :right";

    public static final String FIND_BY_DATE = "Book.findByDate";
    public static final String FIND_BY_DATE_QUERY =
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors a WHERE b.publishDate=:date";

    public static final String FIND_ID_BY_TAG_QUERY =
            "SELECT DISTINCT book_id FROM tag AS t WHERE t.tag=:tag";

}
