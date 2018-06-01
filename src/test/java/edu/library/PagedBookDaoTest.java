package edu.library;

import edu.library.entities.Author;
import edu.library.entities.Book;
import edu.library.utils.AuthorBuilder;
import edu.library.services.books.BookService;
import edu.library.utils.BookBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;


public class PagedBookDaoTest {
    public static ApplicationContext ctx;

    @BeforeClass
    public static void init() {
        GenericXmlApplicationContext xmlCtx = new GenericXmlApplicationContext();

        xmlCtx.load("classpath:META-INF/context/test-ctx.xml");
        xmlCtx.refresh();
        ctx = xmlCtx;

        uploadData();
    }

    private static void uploadData() {
        BookService bookService = getBookService();

        Book b1 = new BookBuilder()
                .title("b1")
                .genre("SCIENCE", "CRIMINAL")
                .rating(7.3f).build();

        Book b2 = new BookBuilder()
                .title("b2")
                .genre("SCIENCE", "DETECTIVE")
                .rating(9.2f).build();

        Book b3 = new BookBuilder()
                .title("b3")
                .genre("HELLO", "WORLD")
                .rating(10.0f).build();

        Book b4 = new BookBuilder()
                .title("b4")
                .genre("BIONIC", "BEAVER")
                .rating(8.0f).build();

        Book b5 = new BookBuilder()
                .title("b5")
                .genre("NOTHING ELSE", "MATTERS")
                .rating(7.5f).build();

        Book b6 = new BookBuilder()
                .title("b6")
                .genre("FICTION", "FANTASY")
                .rating(8.8f).build();

        Book b7 = new BookBuilder()
                .title("b7")
                .genre("EDUCATION", "OPERATING SYSTEMS")
                .rating(9.1f).build();

        Book b8 = new BookBuilder()
                .title("b8")
                .genre("EDUCATION", "PROGRAMMING")
                .rating(7.8f).build();

        Book b9 = new BookBuilder()
                .title("b9")
                .genre("EDUCATION", "MATH")
                .rating(8.2f).build();

        Book b10 = new BookBuilder()
                .title("b10")
                .genre("EDUCATION", "PHYSICS")
                .rating(7.0f).build();

        Book b11 = new BookBuilder()
                .title("b11")
                .genre("EDUCATION", "PROGRAMMING")
                .rating(9.9f).build();

        Book b12 = new BookBuilder()
                .title("b12")
                .genre("SCIENCE", "BIOLOGY")
                .rating(7.8f).build();


        Author a1 = new AuthorBuilder()
                .name("Vasiliy", "Morozov")
                .build();

        Author a2 = new AuthorBuilder()
                .name("Petr", "Vorona")
                .build();

        Author a3 = new AuthorBuilder()
                .name("Kirill", "Vasiliev")
                .build();

        Author a4 = new AuthorBuilder()
                .name("Sergey", "Kromov")
                .build();

        Author a5 = new AuthorBuilder()
                .name("Yuriy", "Semenov")
                .build();

        Author a6 = new AuthorBuilder()
                .name("Roman", "Lomonosov")
                .build();

        Author a7 = new AuthorBuilder()
                .name("Alexander", "Novikov")
                .build();

        b1.addAuthor(a1);
        b1.addAuthor(a2);

        b2.addAuthor(a1);
        b2.addAuthor(a5);

        b3.addAuthor(a1);

        b4.addAuthor(a3);
        b4.addAuthor(a4);

        b5.addAuthor(a6);

        b6.addAuthor(a6);
        b6.addAuthor(a1);
        b6.addAuthor(a2);

        b7.addAuthor(a4);
        b7.addAuthor(a1);
        b7.addAuthor(a7);

        b8.addAuthor(a2);
        b8.addAuthor(a1);

        b9.addAuthor(a3);

        b10.addAuthor(a1);
        b10.addAuthor(a7);

        b11.addAuthor(a2);

        b12.addAuthor(a3);
        b12.addAuthor(a2);

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
        bookService.save(b11);
        bookService.save(b12);
    }

    @Test
    public void shouldReturnSecondPageOfBooksOrderedByRating() {

    }

    private static BookService getBookService() {
        return ctx.getBean("bookService", BookService.class);
    }

}
