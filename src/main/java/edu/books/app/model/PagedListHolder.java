package edu.books.app.model;

import java.util.List;

public interface PagedListHolder<E> {
    void setPageSize(int size);
    int getPageSize();

    List<E> getPagedList(int page);
}
