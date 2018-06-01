package edu.library.utils;

import edu.library.entities.Book;

public class BookBuilder {

    private String title;

    private float rating;

    private Book.Genre genre;

    public BookBuilder title(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder rating(float rating) {
        this.rating = rating;
        return this;
    }

    public BookBuilder genre(String genre, String subGenre) {
        this.genre = new Book.Genre();
        this.genre.setGenre(genre);
        this.genre.setSubGenre(subGenre);

        return this;
    }


    public Book build() {
        Book book = new Book();

        book.setTitle(title);
        book.setGenre(genre);
        book.setRating(rating);

        return book;
    }

}
