import edu.books.entities.Author;
import edu.books.entities.Book;
import edu.books.services.books.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Date;

public class Main {
    private static ApplicationContext ctx;

    static {
        GenericXmlApplicationContext xmlContext =
                new GenericXmlApplicationContext();
        xmlContext.load("classpath:META-INF/context/app-ctx.xml");
        xmlContext.refresh();

        ctx = xmlContext;
    }

    public static void main(String[] args) {
        BookService bookService = ctx.getBean("bookService", BookService.class);

        Book book1 = new Book();
        Book book2 = new Book();
        Book book3 = new Book();

        Book.Genre casual = new Book.Genre();
        casual.setGenre("Casual");

        Author author1 = new Author();
        Author author2 = new Author();

        author1.setId(1);
        author1.setFirstName("Pavel");
        author1.setLastName("Budantsev");

        author2.setId(2);
        author2.setFirstName("Matthew");
        author2.setLastName("McConaughey");

        System.out.println(author1);
        System.out.println(author2);

        book1.setTitle("Book 1");
        book1.setGenre(casual);
        book1.setPublishDate(new Date());
        book1.addAuthor(author1);
        book1.addAuthor(author2);

        System.out.println(author1);
        System.out.println(author2);

        bookService.save(book1);

        System.out.println(author1);
        System.out.println(author2);
        System.out.println(book1);

        book2.setTitle("Book 2");
        book2.setGenre(casual);
        book2.setPublishDate(new Date());
        book2.addAuthor(author1);

        bookService.save(book2);

        System.out.println(author1);
        System.out.println(book2);

        book3.setTitle("Book 3");
        book3.setGenre(casual);
        book3.setPublishDate(new Date());
        book3.addAuthor(author2);

        bookService.save(book3);

        System.out.println(author2);
        System.out.println(book3);

    }
}
