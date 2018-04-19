package edu.books.services.books.pagination.books.dao;


import edu.books.entities.Book;
import edu.books.services.books.BookDao;
import edu.books.services.books.pagination.Page;
import edu.books.services.books.pagination.PagedDao;
import edu.books.services.books.pagination.books.BookPage;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * For each new http session new object
 * will be created in order to do so that
 * cache is to be in consistent state
 */

@Transactional
@Service("pagedBookDao")
@Scope("session")
public class PagedBookDao implements PagedDao<Book> {

    private Map<Integer, Page<Book>> cache = new ConcurrentHashMap<>();
    private static Logger log = Logger.getLogger(PagedBookDao.class);

    private SessionFactory sessionFactory;

    private BookDao bookDao;

    private boolean firstPageLoaded = false;

    /**
     *  I use seek method for pagination instead of
     *  simply setting OFFSET for reasons of performance
     *  So the code looks little bit tricky and complicated
     */

    private static final String FETCH_PAGE =
            "SELECT id FROM book " +
                    "WHERE " +
                        "rating <= :last_rating AND " +
                        "(id < :last_id OR rating < :last_rating)" +
                    "ORDER BY rating DESC, id DESC LIMIT :page_size";

    private static final String FETCH_FIRST_PAGE =
            "SELECT id FROM book " +
                    "ORDER BY rating DESC, id DESC LIMIT :page_size";

    @Override
    public List<Book> get(int page, int pageSize) {
        Page<Book> bookPage;
        if(!firstPageLoaded) {
            bookPage = fetchFirstPage(pageSize);
        } else {
            bookPage = cache.get(1);
        }

        if(cache.containsKey(page)) {
            return cache.get(page).getData();
        }

        long lastId = bookPage.lastElement().getId();
        float lastRating = bookPage.lastElement().getRating();

        for (int i = 1; i <= page; i++) {
        }

        return null;

    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    /* ***************** Private Methods ********************** */

    @SuppressWarnings("unchecked")
    private List<Long> getIdentitiesByPage(long lastId, float lastRating, int pageSize) {
        Session session = sessionFactory.getCurrentSession();

        return  (List<Long>)
                session.createNativeQuery(FETCH_PAGE)
                .setParameter("last_id", lastId)
                .setParameter("last_rating", lastRating)
                .setParameter("page_size", pageSize)
                .list();
    }

    /**
     *
     *
     * @variable lastId id of the last element on the previous page
     * @variable  lastRating rating of the last element on the previous page
     * @variable  pageSize size of the page
     * @return new tuple that consists of retrieved id and rating values
     */

    @SuppressWarnings("unchecked")
    private IdAndRatingMutableTuple getNextLastIdAndRating(IdAndRatingMutableTuple tuple, int pageSize) {

        long lastId = tuple.getId();
        float lastRating = tuple.getRating();
        final String query =
                "SELECT t.id, t.rating FROM ( " +
                        "SELECT id, rating FROM book " +
                            "WHERE rating <= :last_rating " +
                                "AND (id < :last_id OR rating < 10.0) " +
                        "ORDER BY rating DESC, id DESC LIMIT :page_size " +
                ") AS t ORDER BY rating ASC, id ASC LIMIT 1";

        Session session = sessionFactory.getCurrentSession();
        List<Object[]> rl = session.createNativeQuery(query)
                .setParameter("last_id", lastId)
                .setParameter("last_rating", lastRating)
                .setParameter("page_size", pageSize)
                .list();


        lastId = (Long) rl.get(0)[0];
        lastRating = (Float) rl.get(0)[1];

        tuple.setId(lastId);
        tuple.setRating(lastRating);

        return tuple;
    }

    @SuppressWarnings("unchecked")
    private Page<Book> fetchFirstPage(int pageSize) {
        if(!firstPageLoaded) {
            firstPageLoaded = true;
            Session session = sessionFactory.getCurrentSession();

            List<Long> identities = (List<Long>)
                    session.createNativeQuery(FETCH_FIRST_PAGE)
                    .setParameter("page_size", pageSize)
                    .list();

            List<Book> books = getBooksById(identities);

            Page<Book> bookPage = new BookPage.Builder()
                    .setData(books).build();


            cache.putIfAbsent(1, bookPage);

            // prefetch next two pages
            for (int i = 2; i <= 3; i++) {
                Page<Book> previous = cache.get(i - 1);
                long lastId = previous.lastElement().getId();
                float lastRating = previous.lastElement().getRating();

                identities = getIdentitiesByPage(lastId, lastRating, pageSize);
                books = getBooksById(identities);

                BookPage temp = new BookPage.Builder()
                        .setData(books).build();
                cache.putIfAbsent(i, temp);

                return bookPage;
            }
        }

        return cache.get(1);
    }

    private List<Book> getBooksById(List<Long> identities) {
        List<Book> books = new LinkedList<>();

        for(Long id: identities) {
            books.add(bookDao.findById(id));
        }

        return books;
    }

    private static class IdAndRatingMutableTuple {
        private long id;
        private float rating;

        public IdAndRatingMutableTuple(long id, long rating) {
            this.id = id;
            this.rating = rating;
        }

        public float getRating() {
            return rating;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }
    }
}