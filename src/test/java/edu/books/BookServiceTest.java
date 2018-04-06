package edu.books;

import edu.books.entities.Author;
import edu.books.entities.Book;
import edu.books.services.authors.AuthorDao;
import edu.books.services.books.BookDao;
import edu.books.services.books.BookService;
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
        xmlContext.getEnvironment().setActiveProfiles("test");
        xmlContext.refresh();

        ctx = xmlContext;
    }

    @Test
    public void serviceTest() {
        shouldSaveBook();
        shouldFindBooks();
        shouldUpdateAndDeleteBooks();
    }

    private void shouldSaveBook() {
        BookService bm = getBookService();

        String tag_science = "Science";
        String tag_detective = "detective";
        String tag_detective_upper = "DETECTIVE";

        Book.Genre genre1 = new Book.Genre();
        Book.Genre genre2 = new Book.Genre();
        Book.Genre genre3 = new Book.Genre();

        genre1.setGenre("Detective");
        genre1.setSubGenre("Historical Detective");

        genre2.setGenre("Science");
        genre2.setSubGenre("Physics");

        genre3.setGenre("Classical");

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
        Author author6 = new Author();

        author1.setFirstName("Oliver");
        author1.setLastName("Sykes");

        author2.setFirstName("Matthew");
        author2.setLastName("McConaughey");

        author3.setFirstName("Jon");
        author3.setLastName("Snow");

        author4.setFirstName("Jaime");
        author4.setLastName("Lannister");

        author5.setFirstName("Stannis");
        author5.setLastName("Baratheon");

        author6.setFirstName("James");
        author6.setLastName("Hetfield");

        book1.setTitle("First Book");
        book1.setPublishDate(new Date());
        book1.setRating(7.3f);
        book1.setGenre(genre1);
        book1.addAuthor(author1);
        book1.addTag(tag_detective);

        book2.setTitle("Second Book");
        book2.setPublishDate(new Date());
        book2.setRating(9.2f);
        book2.setGenre(genre1);
        book2.addAuthor(author2);
        book2.addAuthor(author1);
        book2.addAuthor(author3);
        book2.addTag(tag_detective_upper);

        book3.setTitle("Third Book");
        book3.setPublishDate(new Date());
        book3.setRating(7.7f);
        book3.setGenre(genre2);
        book3.addAuthor(author1);
        book3.addAuthor(author5);
        book3.addTag(tag_science);

        book4.setTitle("Fourth Book");
        book4.setPublishDate(new Date());
        book4.setRating(7.7f);
        book4.setGenre(genre3);
        book4.addAuthor(author1);

        book5.setTitle("Fifth Book");
        book5.setPublishDate(new Date());
        book5.setRating(7.3f);
        book5.setGenre(genre2);
        book5.addAuthor(author2);

        book6.setTitle("Sixth Book");
        book6.setPublishDate(new Date());
        book6.setRating(7.2f);
        book6.setGenre(genre1);
        book6.addAuthor(author3);

        book7.setTitle("Seventh Book");
        book7.setPublishDate(new Date());
        book7.setRating(8.0f);
        book7.setGenre(genre3);
        book7.addAuthor(author4);

        book8.setTitle("Eighth Book");
        book8.setPublishDate(new Date());
        book8.setRating(9.1f);
        book8.setGenre(genre3);
        book8.addAuthor(author3);
        book8.addAuthor(author4);

        book9.setTitle("Ninth Book");
        book9.setPublishDate(new Date());
        book9.setRating(3.2f);
        book9.addAuthor(author5);
        book9.setGenre(genre1);

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

    private void shouldFindBooks() {

        final String line = "*************************************************************************";


        String tag_science = "Science";
        String tag_detective = "detective";
        String tag_detective_upper = "DETECTIVE";

        Book.Genre genre1 = new Book.Genre();
        Book.Genre genre2 = new Book.Genre();
        Book.Genre genre3 = new Book.Genre();

        genre1.setGenre("Detective");
        genre1.setSubGenre("Historical Detective");

        genre2.setGenre("Science");
        genre2.setSubGenre("Physic");

        genre3.setGenre("Classical");

        BookService bookService = getBookService();
        List<Book> books = bookService.findAll();

        assertNotNull(books);
        assertEquals(9, books.size());

        AuthorDao authorDao = getAuthorDao();

        Author author = authorDao.findByName("Oliver", "Sykes").get(0);

        assertNotNull(authorDao);
        assertEquals("Oliver", author.getFirstName());

        books = bookService.findByAuthor(author);
        assertNotNull(books);
        assertEquals(4, books.size());

        books = bookService.findByTitle("First Book");

        assertNotNull(books);
        assertEquals(1, books.size());

        Book book = books.get(0);

        assertNotNull(book.getAuthors());

        List<Author> authors = book.getAuthors();

        assertEquals(1, authors.size());

        Author bookAuthor = book.getAuthors().get(0);

        assertEquals(author.getFirstName(), bookAuthor.getFirstName());
        assertEquals(author.getLastName(), bookAuthor.getLastName());

        List<Book> authorsBooks = bookAuthor.getBooks();

        //Throws org.hibernate.LazyInitializationException
        //when FetchType.LAZY used
        //but when FetchType.EAGER used
        //it doesn't
        /*
        for(Book b: authorsBooks) {
            System.out.println(b);
        }

        assertEquals(4, authorsBooks.size());

        System.out.println(line);
        book = authorsBooks.stream().filter(n -> n.getTitle().equals("First Book"))
                .findFirst().get();

        assertNotNull(book);

        */

        books = bookService.findByPublishDate(new Date());
        assertNotNull(books);

        System.out.println("\n" + line);
        System.out.println("Found by publish date" );
        books.forEach(System.out::println);
        System.out.println();

        books = bookService.findByRating(Book.RatingShortcut.OKAY);

        assertNotNull(books);
        System.out.println("\n" + line);
        System.out.println("Found by rating ");
        books.forEach(System.out::println);
        System.out.println(line);

        books = bookService.findByGenre(genre1);
        assertNotNull(books);
        System.out.println("\n" + line);
        System.out.println("Found by genre ");
        books.forEach(System.out::println);
        System.out.println(line);

        List<Book> tag_books = bookService.findByTags(
            Stream.of(tag_detective).collect(Collectors.toSet())
        );

        assertEquals(2, tag_books.size());
    }

    private void shouldUpdateAndDeleteBooks() {
        BookService bs = getBookService();
        List<Book> books;
        Book book;
        Book book1;

        Book.Genre detective = new Book.Genre();
        detective.setGenre("Detective");

        books = bs.findByTitle("First Book");

        assertNotNull(books);
        assertEquals(1, books.size());

        book = books.get(0);

        assertNotNull(book);
        assertEquals("First Book", book.getTitle());

        book.setGenre(detective);
        //bs.update(book);

        books = bs.findByTitle("First Book");

        assertNotNull(books);
        assertEquals(1, books.size());

        book = books.get(0);

        assertNotNull(book);
        assertEquals("First Book", book.getTitle());
        assertEquals("Detective", book.getGenre().getGenre());
        bs.delete(book);

        books = bs.findByTitle("First Book");

        assertNotNull(books);
        assertEquals(0, books.size());
    }

    private BookDao getBookDao() {
        return ctx.getBean("bookDao", BookDao.class);
    }
    private AuthorDao getAuthorDao() {
        return ctx.getBean("authorDao", AuthorDao.class);
    }
    private BookService getBookService() { return ctx.getBean("bookService", BookService.class);}
}
