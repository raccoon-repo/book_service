package edu.books.services.books.pagination;

import java.util.List;

/**
 *
 * @param <E> data to be stored on page
 */
public interface Page<E> {
    E firstElement();
    E lastElement();

    List<E> getData();
    void setData(List<E> data);
}
