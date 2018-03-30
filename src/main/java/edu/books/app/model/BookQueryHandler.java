package edu.books.app.model;

import edu.books.entities.Author;
import edu.books.entities.Book;
import edu.books.services.authors.AuthorDao;
import edu.books.services.books.BookService;
import edu.books.utils.BookUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;


@Component
public class BookQueryHandler {
    private Set<String> tags     = new HashSet<>();
    private Book.Genre  genre    = new Book.Genre();
    private Set<Author> authors  = new HashSet<>();
    private Book.Rating rating;

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

    public Book.Rating getRating() { return rating; }

    public void setRating(Book.Rating rating) { this.rating = rating; }

    public Set<Book> handle() {
        StopWatch timer = new StopWatch();
        log.info("Handling a request");
        timer.start();
        parseAuthors();
        parseGenre();
        parseRating();
        parseTags();

        Set<Book> resultSet = new HashSet<>();

        resultSet.addAll(bookService.findByTitle(bookQuery.getTitle()));
        log.info(BookUtils.booksAsString(resultSet));
        resultSet.addAll(bookService.findByRating(rating));
        log.info(BookUtils.booksAsString(resultSet));
        resultSet.addAll(bookService.findByTags(tags));
        log.info(BookUtils.booksAsString(resultSet));
        resultSet.addAll(bookService.findByGenre(genre));
        log.info(BookUtils.booksAsString(resultSet));
        authors.forEach(author -> {
            resultSet.addAll(bookService.findByAuthor(author));
            log.info(BookUtils.booksAsString(resultSet));
        });

        timer.stop();
        log.info("handling took " + timer.getTotalTimeMillis() + "ms");
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
                    this.rating = Book.Rating.GOOD;
                    break;
                case "okay":
                    this.rating = Book.Rating.OKAY;
                    break;
                case "best":
                    this.rating = Book.Rating.BEST;
                    break;
                case "bad":
                    this.rating = Book.Rating.BAD;
                    break;
                case "worst":
                    this.rating = Book.Rating.WORST;
                    break;
            }
        }
    }

    private void parseAuthors(){
        log.info("\tparsing " + bookQuery.getAuthorsJson());
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

        log.info("\tparsing took " + timer.getTotalTimeMillis());
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
