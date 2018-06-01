package edu.library.utils;

public final class AuthorQueries {
    private AuthorQueries(){
    }

    public static final String FIND_ALL = "Author.findAll";
    public static final String FIND_ALL_QUERY =
            "SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.library b";

    public static final String FIND_BY_NAME = "Author.findByName";
    public static final String FIND_BY_NAME_QUERY =
            "SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.library b WHERE a.firstName=:firstName AND a.lastName=:lastName";

}
