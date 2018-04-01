package edu.books.utils;

import edu.books.entities.Book;

import java.util.Collection;

public final class BookUtils {
    public static String booksAsString(Collection<Book> books) {
        StringBuilder builder = new StringBuilder();
        builder.append("Books:[");
        for (Book book : books) {
            builder.append(bookAsString(book));
        }
        builder.append("]");
        return builder.toString();
    }

    public static String bookAsString(Book book) {
        char q = '\"';
        char c = ',';
        StringBuilder builder = new StringBuilder();
        builder.append("{\"id\":").append(q).append(book.getId()).append(q).append(c)
                .append("\"title\":").append(q).append(book.getTitle()).append(q).append(c)
                .append("\"genre\":").append(q).append(book.getGenre().getGenre()).append(q).append(c)
                .append("\"subGenre\":").append(q).append(book.getGenre().getSubGenre()).append(q)
                .append("}");

        return builder.toString();
    }
}
