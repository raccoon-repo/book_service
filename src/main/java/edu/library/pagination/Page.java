package edu.library.pagination;

import java.util.List;

/**
 *
 * @param <E> data to be stored on page
 */
public interface Page<E> {
    E firstElement();
    E lastElement();

    int getPageNumber();
    void setPageNumber(int pageNumber);

    List<E> getData();
    void setData(List<E> data);
}
