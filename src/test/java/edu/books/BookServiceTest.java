package edu.books;

import edu.books.entities.Author;
import edu.books.entities.Book;
import edu.books.services.BookManager;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BookServiceTest {
    private static ApplicationContext ctx;

    @BeforeClass
    public static void init() {
        GenericXmlApplicationContext xmlContext =
                new GenericXmlApplicationContext();
        xmlContext.load("classpath:META-INF/context/app-ctx.xml");
        xmlContext.refresh();

        ctx = xmlContext;
    }

    @Test
    public void shouldSaveBook() {
        BookManager bm = getBookManager();

        Book book1 = new Book();
        Book book2 = new Book();
        Book book3 = new Book();
        Book book4 = new Book();
        Book book5 = new Book();
        Book book6 = new Book();
        Book book7 = new Book();
        Book book8 = new Book();
        Book book9 = new Book();

        Author author1 = new Author();
        Author author2 = new Author();
        Author author3 = new Author();
        Author author4 = new Author();
        Author author5 = new Author();

        author1.setFirstName("Pavel");
        author1.setLastName("Budantsev");

        author2.setFirstName("Arthur");
        author2.setLastName("Yashchenko");

        author3.setFirstName("Nicolas");
        author3.setLastName("Opaque");

        author4.setFirstName("Timmy");
        author4.setLastName("Trumpet");

        author5.setFirstName("Nastya");
        author5.setLastName("Garayeva");

        book1.setTitle("First Book");
        book1.setPublishDate(new Date());
        book1.setRating(Book.Rating.OKAY);
        book1.addAuthor(author1);

        author1.addBook(book1);

        book2.setTitle("Second Book");
        book2.setPublishDate(new Date());
        book2.setRating(Book.Rating.BAD);
        book2.addAuthor(author2);
        book2.addAuthor(author1);
        book2.addAuthor(author3);

        author1.addBook(book2);
        author2.addBook(book2);
        author3.addBook(book2);

        book3.setTitle("Third Book");
        book3.setPublishDate(new Date());
        book3.setRating(Book.Rating.OKAY);
        book3.addAuthor(author1);
        book3.addAuthor(author5);

        author1.addBook(book3);
        author5.addBook(book3);

        book4.setTitle("Fourth Book");
        book4.setPublishDate(new Date());
        book4.setRating(Book.Rating.OKAY);
        book4.addAuthor(author1);

        author1.addBook(book4);

        book5.setTitle("Fifth Book");
        book5.setPublishDate(new Date());
        book5.setRating(Book.Rating.OKAY);
        book5.addAuthor(author2);

        author2.addBook(book5);

        book6.setTitle("Sixth Book");
        book6.setPublishDate(new Date());
        book6.setRating(Book.Rating.OKAY);
        book6.addAuthor(author3);

        author3.addBook(book6);

        book7.setTitle("Seventh Book");
        book7.setPublishDate(new Date());
        book7.setRating(Book.Rating.OKAY);
        book7.addAuthor(author4);

        author4.addBook(book7);

        book8.setTitle("Eighth Book");
        book8.setPublishDate(new Date());
        book8.setRating(Book.Rating.OKAY);
        book8.addAuthor(author3);

        author3.addBook(book8);

        book9.setTitle("Ninth Book");
        book9.setPublishDate(new Date());
        book9.setRating(Book.Rating.OKAY);
        book9.addAuthor(author5);

        author5.addBook(book9);

        bm.save(book1);
        bm.save(book2);
        bm.save(book3);
        bm.save(book4);
        bm.save(book5);
        bm.save(book6);
        bm.save(book7);
        bm.save(book8);
        bm.save(book9);
    }

    @Test
    public void shouldDeleteBook() {

    }

    @Test
    public void shouldFindBooks() {
        BookManager bm = getBookManager();

        List<Book> bookList = bm.findAll();

        assertEquals(9, bookList.size());

        Book book = bm.findByTitle("First Book");

        assertNotNull(book);

        assertEquals(book.getTitle(), "First Book");

        Author author = new Author();
        author.setFirstName("Pavel");
        author.setLastName("Budantsev");

        Book book1 = bm.findByAuthor(author);

        assertNotNull(book1);
        assertEquals(book1.getAuthors().get(0), author);
    }

    private BookManager getBookManager() {
        return ctx.getBean("defaultBookManager", BookManager.class);
    }
}
