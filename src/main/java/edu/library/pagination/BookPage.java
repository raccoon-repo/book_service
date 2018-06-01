package edu.library.pagination;

import edu.library.entities.Book;

import java.util.List;

public class BookPage implements Page<Book> {
    private List<Book> books;
    private Book firstElement;
    private Book lastElement;
    private int pageNumber;

    @Override
    public Book firstElement() {
        return firstElement;
    }

    @Override
    public Book lastElement() {
        return lastElement;
    }

    @Override
    public List<Book> getData() {
        return books;
    }

    @Override
    public void setData(List<Book> data) {
        this.books = data;

        if (data.size() == 0)
            return;

        firstElement = data.get(0);
        lastElement = data.get(data.size() - 1);
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public static class Builder {
        private List<Book> data;
        private int pageNumber;

        public Builder() {
        }

        public Builder setData(List<Book> data) {
            this.data = data;
            return this;
        }

        public Builder setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }

        public BookPage build() {
            BookPage page = new BookPage();
            page.setData(data);
            page.setPageNumber(pageNumber);
            return page;
        }
    }
}
