package edu.books;

import edu.books.entities.Book;
import edu.books.services.BookManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

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
        BookManager bm = ctx.getBean("defaultBookManager", BookManager.class);

        for(Book book: bm.findAll()) {
            System.out.println(book);
        }
    }
}
