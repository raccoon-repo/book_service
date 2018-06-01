package edu.books.services.books.pagination.dao;


import edu.books.entities.Author;
import edu.books.entities.Book;
import edu.books.services.books.BookDao;
import edu.books.services.books.pagination.Page;
import edu.books.services.books.pagination.PagedDao;
import edu.books.services.books.pagination.BookPage;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


@Transactional
@Repository("pagedBookDao")
public class PagedBookDao implements PagedDao<Book> {

    private Map<Integer, Page<Book>> cache = new ConcurrentHashMap<>();

    private static Logger log = Logger.getLogger(PagedBookDao.class);

    private SessionFactory sessionFactory;

    private BookDao bookDao;

    private boolean firstPageLoaded = false;


    public PagedBookDao() {
        invalidateCache(1000*60*2);
    }

    /*
     *  I used seek method for pagination instead of
     *  simply setting OFFSET for reasons of performance.
     *  So the code looks little bit tricky and complicated.
     *
     *  Pages are sorted by rating, so first of all one needs
     *  to index this column
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
    @Transactional
    public List<Book> get(int page, int pageSize) {
        Page<Book> bookPage;

        // Load first page
        if(!firstPageLoaded) fetchFirstPage(pageSize);

        if(cache.containsKey(page))
            return cache.get(page).getData();


        // if cache already contains page
        // previous to the page we are looking for
        // get this previous page and retrieve next page
        // just in one step
        if(cache.containsKey(page - 1)) {
            bookPage = cache.get(page - 1);
        } else {
            // else get first page and
            // start iterating from it
            // until we reach to the wanted page
            bookPage = cache.get(1);
        }

        BigInteger lastId = BigInteger.valueOf(bookPage.lastElement().getId());
        BigDecimal lastRating = BigDecimal.valueOf((bookPage.lastElement().getRating()));
        lastRating = lastRating.setScale(2, BigDecimal.ROUND_DOWN);

        IdAndRatingMutableTuple tuple = new IdAndRatingMutableTuple(lastId, lastRating);

        int i;
        for (i = bookPage.getPageNumber(); i < page - 1; i++) {
            getNextLastIdAndRating(tuple, pageSize);
        }

        List<BigInteger> identities = getIdentitiesByPage(tuple.id, tuple.rating, pageSize);
        List<Book> books = getBooksById(identities);

        bookPage = new BookPage.Builder()
                   .setData(books)
                   .setPageNumber(page)
                   .build();

        cache.putIfAbsent(page, bookPage);
        return books;
    }

    /* ***************** Getters and Setters ******************* */

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

    /**
     * Invalidates the cache
     * @param interval specifies the time in millisecond after
     *                 which the cache will be invalidated
     *                 and then cleared
     */
    private void invalidateCache(long interval) {
        ForkJoinPool fjp = ForkJoinPool.commonPool();
        fjp.execute(()-> {
            while (true) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }

                cache.clear();
                firstPageLoaded = false;
            }
        });
    }

    /**
     * Selects list of entities from
     * @param lastId id of the last book on the previous page
     * @param lastRating id of the last book on the previous page
     * @param pageSize size of a single page
     * @return list of identities
     */

    @SuppressWarnings("unchecked")
    private List<BigInteger> getIdentitiesByPage(BigInteger lastId, BigDecimal lastRating, int pageSize) {
        Session session = sessionFactory.getCurrentSession();

        return  (List<BigInteger>)
                 session.createNativeQuery(FETCH_PAGE)
                .setParameter("last_id", lastId)
                .setParameter("last_rating", lastRating)
                .setParameter("page_size", pageSize)
                .list();
    }

    /**
     * Method that is used for iterating over pages.
     * To use it, pass a tuple into the method.
     * Then, the method will get id and rating of the last
     * element on the next page and change tuple' values
     * to reuse this tuple object later
     *
     * @param tuple A mutable tuple that contains
     *              id and rating of the last element
     *              on the current page
     *
     */

    @SuppressWarnings("unchecked")
    private void getNextLastIdAndRating(IdAndRatingMutableTuple tuple, int pageSize) {
        BigInteger lastId = tuple.getId();
        BigDecimal lastRating = tuple.getRating();

        final String query =
                "SELECT t.id, t.rating FROM ( " +
                        "SELECT id, rating FROM book " +
                            "WHERE rating <= :last_rating " +
                                "AND (id < :last_id OR rating < :last_rating) " +
                        "ORDER BY rating DESC, id DESC LIMIT :page_size " +
                ") AS t ORDER BY t.rating ASC, t.id ASC LIMIT 1";

        Session session = sessionFactory.getCurrentSession();
        List<Object[]> rl = session.createNativeQuery(query)
                .setParameter("last_id", lastId)
                .setParameter("last_rating", lastRating)
                .setParameter("page_size", pageSize)
                .list();

        lastId      = (BigInteger) rl.get(0)[0];
        lastRating  = (BigDecimal) rl.get(0)[1];

        tuple.setId(lastId);
        tuple.setRating(lastRating);

    }


    /**
     * Fetches first page and returns it
     *
     * @param pageSize size of single page
     * @return fetched page
     */
    @SuppressWarnings("unchecked")
    private Page<Book> fetchFirstPage(int pageSize) {
        if(!firstPageLoaded) {
            firstPageLoaded = true;
            Session session = sessionFactory.getCurrentSession();

            // Fetch identities on the first page
            List<BigInteger> identities = (List<BigInteger>)
                    session.createNativeQuery(FETCH_FIRST_PAGE)
                            .setParameter("page_size", pageSize)
                            .list();

            // Fetch books by identities using Hibernate
            List<Book> books = getBooksById(identities);

            Page<Book> bookPage = new BookPage.Builder()
                                  .setData(books)
                                  .setPageNumber(1).build();


            cache.putIfAbsent(1, bookPage);
            return bookPage;
        }

        return cache.get(1);
    }

    private List<Book> getBooksById(List<BigInteger> identities) {
        List<Book> books = new LinkedList<>();
        Book book;
        Session session = sessionFactory.getCurrentSession();
        for(BigInteger id: identities) {
            book = session.get(Book.class, id.longValue());

            // Temporal solution is to load all of the authors
            // to avoid LazyInitializationException
            for (Author a : book.getAuthors());
            books.add(book);
        }

        return books;
    }

    private static class IdAndRatingMutableTuple {
        private BigInteger id;
        private BigDecimal rating;

        public IdAndRatingMutableTuple(BigInteger id, BigDecimal rating) {
            this.id = id;
            this.rating = rating;
        }

        public BigDecimal getRating() {
            return rating;
        }

        public BigInteger getId() {
            return id;
        }

        public void setId(BigInteger id) {
            this.id = id;
        }

        public void setRating(BigDecimal rating) {
            this.rating = rating;
        }
    }
}
