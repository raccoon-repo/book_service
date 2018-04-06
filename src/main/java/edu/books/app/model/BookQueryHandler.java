package edu.books.app.model;

import edu.books.entities.Author;
import edu.books.entities.Book;
import edu.books.services.authors.AuthorDao;
import edu.books.services.books.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.StringReader;
import java.util.*;

import org.apache.log4j.Logger;


@Component
public class BookQueryHandler {
    private Set<String> tags     = new HashSet<>();
    private Book.Genre  genre    = new Book.Genre();
    private Set<Author> authors  = new HashSet<>();
    private Book.RatingShortcut ratingShortcut;

    private static final String LOG_DELIM = "\n-------------------------------------------------------\n";
    private static final Logger log = Logger.getLogger(BookQueryHandler.class);

    private BookQuery bookQuery;

    private AuthorDao authorDao;

    private BookService bookService;

    public Set<Author> getAuthors() { return authors; }

    public void setAuthors(Set<Author> authors) { this.authors = authors; }

    public Book.Genre getGenre() { return genre; }

    public void setGenre(Book.Genre genre) { this.genre = genre; }

    public Set<String> getTags() { return tags; }

    public void setTags(Set<String> tags) { this.tags = tags; }

    public Book.RatingShortcut getRatingShortcut() { return ratingShortcut; }

    public void setRatingShortcut(Book.RatingShortcut ratingShortcut) { this.ratingShortcut = ratingShortcut; }

    public Set<Book> handle() {
        log.info(LOG_DELIM);
        log.info(new Date());
        log.info("\tHandling a search");
        StopWatch timer = new StopWatch();

        List<Book> byTitle;
        List<Book> byTags;
        List<Book> byRating;
        List<Book> byGenre;
        List<Book> byAuthors;

        timer.start();
        parseAuthors();
        parseGenre();
        parseRating();
        parseTags();

        Set<Book> resultSet = new HashSet<>();

        byTitle = bookService.findByTitle(bookQuery.getTitle());
        resultSet.addAll(byTitle);

        byTags = bookService.findByTags(tags);
        resultSet.addAll(byTags);

        byRating = bookService.findByRating(ratingShortcut);
        resultSet.addAll(byRating);

        byGenre = bookService.findByGenre(genre);
        resultSet.addAll(byGenre);

        for(Author a: authors) {
            byAuthors = bookService.findByAuthor(a);
            resultSet.addAll(byAuthors);
        }

        timer.stop();
        log.info("\tHandling a search took" + timer.getTotalTimeMillis());
        return resultSet;
    }

    private void parseGenre() {
        genre.setGenre(bookQuery.getGenre());
        genre.setSubGenre(bookQuery.getSubGenre());
    }

    private void parseTags() {
        String tagsStr = bookQuery.getTags();
        if(tagsStr != null) {
            tags.addAll(Arrays.asList(tagsStr.split(",")));
        }
    }

    private void parseRating() {
        String rating = bookQuery.getRating();
        if (rating != null) {
            switch (rating) {
                case "good":
                    this.ratingShortcut = Book.RatingShortcut.GOOD;
                    break;
                case "okay":
                    this.ratingShortcut = Book.RatingShortcut.OKAY;
                    break;
                case "best":
                    this.ratingShortcut = Book.RatingShortcut.BEST;
                    break;
                case "bad":
                    this.ratingShortcut = Book.RatingShortcut.BAD;
                    break;
                case "worst":
                    this.ratingShortcut = Book.RatingShortcut.WORST;
                    break;
            }
        }
    }

    private void parseAuthors(){
        log.info("\tParsing authors");
        StopWatch timer = new StopWatch();
        timer.start();

        StringReader sr = new StringReader(bookQuery.getAuthorsJson());
        JsonParser parser = Json.createParser(sr);
        JsonParser.Event e;

        while (
            parser.hasNext() &&
            !parser.next().equals(JsonParser.Event.START_ARRAY)
        );

        while (
            parser.hasNext() &&
            !(e = parser.next()).equals(JsonParser.Event.END_ARRAY)
        ) {
            if(e.equals(JsonParser.Event.START_OBJECT)) {
                boolean hasId = false;
                String firstName = "";
                String lastName = "";
                while(
                        parser.hasNext() &&
                                !(e = parser.next()).equals(JsonParser.Event.END_OBJECT)
                        ) {
                    if (e == JsonParser.Event.KEY_NAME) {
                        switch (parser.getString()) {
                            case "id":
                                parser.next();
                                long id = parser.getLong();

                                if (id != 0) {
                                    hasId = true;
                                    authors.add(authorDao.findById(id));
                                }
                                break;
                            case "firstName":
                                parser.next();
                                firstName = parser.getString();
                                break;
                            case "lastName":
                                parser.next();
                                lastName = parser.getString();
                                break;
                        }
                    }
                }
                if (!hasId) {
                    if(!firstName.equals("") && !lastName.equals(""))
                        authors.addAll(authorDao.findByName(firstName, lastName));
                }
            }
        }
        timer.stop();
        log.info("\t\tParsing took " + timer.getTotalTimeMillis());
    }

    public BookService getBookService() {
        return bookService;
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setAuthorDao(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public AuthorDao getAuthorDao() {
        return authorDao;
    }

    public BookQuery getBookQuery() {
        return bookQuery;
    }

    public void setBookQuery(BookQuery bookQuery) {
        this.bookQuery = bookQuery;
    }
}
