package edu.library.pagination;

import java.util.List;

/**
 * DataAccessObject for fetching elements
 * from database dividing them into pages
 *
 * @param <E> element to be fetched
 */
public interface PagedDao<E> {
    List<E> get(int page, int pageSize);
}
