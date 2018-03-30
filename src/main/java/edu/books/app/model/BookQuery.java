package edu.books.app.model;

public class BookQuery {
    private String title;
    private String authorsJson;
    private String genre;
    private String subGenre;
    private String tags;
    private String rating;

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getAuthorsJson() { return authorsJson; }

    public void setAuthorsJson(String authorsJson) { this.authorsJson = authorsJson; }

    public String getGenre() { return genre; }

    public void setGenre(String genre) { this.genre = genre; }

    public String getSubGenre() { return subGenre; }

    public void setSubGenre(String subGenre) { this.subGenre = subGenre; }

    public String getTags() { return tags; }

    public void setTags(String tags) { this.tags = tags; }

    public String getRating() { return rating; }

    public void setRating(String rating) { this.rating = rating; }
}
