package edu.books.services.books.pagination;

import java.util.List;

/**
 * PaginationService
 * Retrieve list of elements by page
 *
 * @param <E> element type to be retrieved
 */
public interface PaginationService<E> {
    List<E> get(int page, int pageSize);
}
