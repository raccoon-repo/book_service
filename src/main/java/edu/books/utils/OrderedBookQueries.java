package edu.books.utils;

public final class OrderedBookQueries {
    private OrderedBookQueries() {
    }

    private static final String ORDER_BY = " ORDER BY :orderBy";

    public static final String FIND_ALL = BookQueries.FIND_ALL + "Ordered";
    public static final String FIND_ALL_QUERY = BookQueries.FIND_ALL_QUERY + ORDER_BY;

    public static final String FIND_BY_TITLE = BookQueries.FIND_BY_TITLE + "Ordered";
    public static final String FIND_BY_TITLE_QUERY = BookQueries.FIND_BY_TITLE_QUERY + ORDER_BY;

    public static final String FIND_BY_AUTHOR = BookQueries.FIND_BY_AUTHOR + "Ordered";
    public static final String FIND_BY_AUTHOR_QUERY = BookQueries.FIND_BY_AUTHOR_QUERY + ORDER_BY;

    public static final String FIND_BY_RATING = BookQueries.FIND_BY_RATING + "Ordered";
    public static final String FIND_BY_RATING_QUERY = BookQueries.FIND_BY_RATING_QUERY + ORDER_BY;

    public static final String FIND_BY_DATE = BookQueries.FIND_BY_DATE + "Ordered";
    public static final String FIND_BY_DATE_QUERY = BookQueries.FIND_BY_DATE_QUERY + ORDER_BY;

    public static final String FIND_BY_GENRE = BookQueries.FIND_BY_GENRE + "Ordered";
    public static final String FIND_BY_GENRE_QUERY = BookQueries.FIND_BY_GENRE_QUERY + ORDER_BY;

}
