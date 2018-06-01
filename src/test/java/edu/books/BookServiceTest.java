package edu.books;

import edu.books.entities.Author;
import edu.books.entities.Book;
import edu.books.services.authors.AuthorDao;
import edu.books.services.books.BookDao;
import edu.books.services.books.BookService;
import edu.books.utils.AuthorBuilder;
import edu.books.utils.BookBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class BookServiceTest {
    private static ApplicationContext ctx;

    @BeforeClass
    public static void init() {
        GenericXmlApplicationContext xmlContext =
                new GenericXmlApplicationContext();
        xmlContext.load("classpath:META-INF/context/test-ctx.xml");
        xmlContext.refresh();

        ctx = xmlContext;
    }

    @Test
    public void serviceTest() {
        initData();
        shouldFindBooks();
    }

    private void initData() {
        BookService bookService = getBookService();

        Book b1 = new BookBuilder()
                .title("Raccoon")
                .genre("Fiction", "deTECtIve")
                .rating(8.9f).build();

        Book b2 = new BookBuilder()
                .title("Bionic Beaver")
                .genre("fiction", "fantasy")
                .rating(7.7f).build();

        Book b3 = new BookBuilder()
                .title("True Detective")
                .genre("FICTION", "DETECTIVE")
                .rating(10.0f).build();

        Book b4 = new BookBuilder()
                .title("Not True Detective")
                .genre("Fiction", null)
                .rating(9.5f).build();

        Book b5 = new BookBuilder()
                .title("Why So Serious?")
                .genre("FICTION", "Criminal")
                .rating(9.0f).build();

        Book b6 = new BookBuilder()
                .title("Why Do We Fall?")
                .genre("FICTION", "crIminal")
                .rating(8.7f).build();

        Book b7 = new BookBuilder()
                .title("The Adventures of Sherlock Holmes")
                .genre("FICTION", "DetEctive")
                .rating(7.8f).build();

        Book b8 = new BookBuilder()
                .title("Java Programming Language")
                .genre("NON-FICTION", "PROGRAMMING")
                .rating(8.2f).build();

        Book b9 = new BookBuilder()
                .title("Romeo and Juliet")
                .genre("Fiction", "TRagedy")
                .rating(9.0f).build();

        Book b10 = new BookBuilder()
                .title("Design Patterns: Elements of Reusable Object-Oriented Software")
                .genre("NON-FICTION", "PROGRAMMING")
                .rating(8.7f).build();


        Author a1 = new AuthorBuilder()
                .name("Erich", "Gamma").build();

        Author a2 = new AuthorBuilder()
                .name("Richard", "Helm").build();

        Author a3 = new AuthorBuilder()
                .name("Ralph", "Johnson").build();

        Author a4 = new AuthorBuilder()
                .name("John", "Vlissides").build();

        Author a5 = new AuthorBuilder()
                .name("Arthur", "Conan Doyle").build();

        Author a6 = new AuthorBuilder()
                .name("Herbert", "Schildt").build();

        Author a7 = new AuthorBuilder()
                .name("William", "Shakespeare").build();

        Author a8 = new AuthorBuilder()
                .name("Nikolay", "Kromov").build();

        Author a9 = new AuthorBuilder()
                .name("Juliet", "Ronald").build();

        b1.addAuthor(a8);
        b2.addAuthor(a8);
        b3.addAuthor(a9);
        b4.addAuthor(a9);

        b5.addAuthor(a8);
        b5.addAuthor(a9);

        b6.addAuthor(a8);
        b6.addAuthor(a9);

        b7.addAuthor(a5);
        b8.addAuthor(a6);
        b9.addAuthor(a7);

        b10.addAuthor(a1);
        b10.addAuthor(a2);
        b10.addAuthor(a3);
        b10.addAuthor(a4);

        bookService.save(b1);
        bookService.save(b2);
        bookService.save(b3);
        bookService.save(b4);
        bookService.save(b5);
        bookService.save(b6);
        bookService.save(b7);
        bookService.save(b8);
        bookService.save(b9);
        bookService.save(b10);
    }

    private void shouldFindBooks() {
        BookService bookService = getBookService();
        AuthorDao authorDao = getAuthorDao();

        Book b1 = bookService.findById(1);

        assertNotNull(b1);
        assertEquals("Raccoon", b1.getTitle());

        List<Book> books = bookService.findByTitle("Detective");

        assertNotNull(books);
        assertEquals(2, books.size());

        Book.Genre genre = new Book.Genre();
        genre.setGenre("fiction");
        genre.setSubGenre("criminal");

        books = bookService.findByGenre(genre);

        assertNotNull(books);
        assertEquals(2, books.size());

        books = bookService.findByRating(10.0f);
        assertNotNull(books);
        assertEquals(1, books.size());

        books = bookService.findByRating(9.0f);
        assertNotNull(books);
        assertEquals(2, books.size());

        books = bookService.findByRating(8.7f);
        assertNotNull(books);
        assertEquals(2, books.size());

        List<Author> author = authorDao.findByName("Herbert", "Schildt");
        assertNotNull(author);


        books = bookService.findByAuthor(author.get(0));
        assertNotNull(books);
        assertEquals(1, books.size());
    }

    private BookDao getBookDao() {
        return ctx.getBean("bookDao", BookDao.class);
    }
    private AuthorDao getAuthorDao() {
        return ctx.getBean("authorDao", AuthorDao.class);
    }
    private BookService getBookService() { return ctx.getBean("bookService", BookService.class);}
}
