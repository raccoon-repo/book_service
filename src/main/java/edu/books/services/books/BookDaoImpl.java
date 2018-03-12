package edu.books.services.books;

import edu.books.entities.Author;
import edu.books.entities.Book;
import edu.books.services.authors.AuthorDao;
import edu.books.utils.BookQueries;
import edu.books.utils.NativeQueries;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("bookDao")
@Transactional
public class BookDaoImpl implements BookDao {

    @Resource(name = "hibernateSessionFactory")
    private SessionFactory sessionFactory;

    @Resource(name = "authorDao")
    private AuthorDao authorDao;

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findAll() {
        return (List<Book>) sessionFactory.getCurrentSession()
               .getNamedQuery(BookQueries.FIND_ALL)
               .list();
    }

    @Override
    public Book findById(long id) {
        return sessionFactory.getCurrentSession()
               .get(Book.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByTitle(String title) {
        return (List<Book>) sessionFactory.getCurrentSession()
               .getNamedQuery(BookQueries.FIND_BY_TITLE)
               .setParameter("title", title)
               .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByAuthor(Author author) {
        Session session = sessionFactory.getCurrentSession();

        //if author's id is unspecified
        //find all books that have authors with the same name
        if(author.getId() <= 0) {
            List<Author> authors = authorDao.findByName(author);
            List<Book> books = new ArrayList<>();

            for (Author a : authors)
                books.addAll(
                    session.getNamedQuery(BookQueries.FIND_BY_AUTHOR)
                    .setParameter("author_id", a.getId())
                    .list()
                );
            return books;
        } else
            return session.getNamedQuery(BookQueries.FIND_BY_AUTHOR)
                   .setParameter("author_id", author.getId()).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByPublishDate(Date publishDate) {
        return (List<Book>) sessionFactory.getCurrentSession()
               .getNamedQuery(BookQueries.FIND_BY_DATE)
               .setParameter("date", publishDate)
               .list();
    }

    @Override
    public Book save(Book book) {
        checkForAuthors(book.getAuthors());
        sessionFactory.getCurrentSession().saveOrUpdate(book);
        return book;
    }

    @Override
    public Book update(Book book) {
        sessionFactory.getCurrentSession().update(book);
        return book;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByGenre(Book.Genre genre) {
        return (List<Book>) sessionFactory.getCurrentSession()
               .getNamedQuery(BookQueries.FIND_BY_GENRE)
               .setParameter("genre", genre)
               .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByRating(Book.Rating rating) {
        return (List<Book>) sessionFactory.getCurrentSession()
               .getNamedQuery(BookQueries.FIND_BY_RATING)
               .setParameter("rating", rating)
               .list();
    }

    @Override
    public void delete(long id) {
        Book book = findById(id);
        delete(book);
    }

    @Override
    public void delete(Book book) {
        if(book.getId() <= 0) {
            return;
        }

        sessionFactory.getCurrentSession().delete(book);
    }

    //checks authors' id property
    //prevents from overwriting information in rows
    //where author's (from the passed list) id equals id in the row
    //but author have different properties set against
    //properties stored in db
    private void checkForAuthors(List<Author> authors) {
        Session session = sessionFactory.getCurrentSession();
        for(Author a: authors) {
            Author a1 = authorDao.findById(a.getId());
            if(a1 != null && !a.equals(a1)) {
                BigInteger id = (BigInteger) session.createNativeQuery(NativeQueries.SELECT_LAST_ID)
                          .uniqueResult();
                a.setId(id.longValue());
            }
        }
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
