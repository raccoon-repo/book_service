package edu.books.entities;


import edu.books.utils.BookQueries;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="book")
@NamedQueries({
    @NamedQuery(name = BookQueries.FIND_ALL, query = BookQueries.FIND_ALL_QUERY),
    @NamedQuery(name = BookQueries.FIND_BY_ID, query = BookQueries.FIND_BY_ID_QUERY),
    @NamedQuery(name = BookQueries.FIND_BY_AUTHOR, query = BookQueries.FIND_BY_AUTHOR_QUERY),
    @NamedQuery(name = BookQueries.FIND_BY_TITLE, query = BookQueries.FIND_BY_TITLE_QUERY)
})
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Temporal(TemporalType.DATE)
    @Column(name = "publish_date")
    private Date publishDate;

    @Enumerated(EnumType.STRING)
    @Column(name="rating")
    private Rating rating;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "book_author",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors = new ArrayList<>();

    public Book() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public enum Rating {
        WORST, BAD, OKAY, GOOD, BEST
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publishDate=" + publishDate +
                ", rating=" + rating +
                ", authors=" + getAuthorsString() +
                '}';
    }

    private String getAuthorsString() {
        StringBuilder builder = new StringBuilder();

        if(authors.size() == 0)
            return null;

        builder.append("[");

        for(Author author: authors) {
            builder.append("{firstName:")
                    .append(author.getFirstName())
                    .append(", lastName: ")
                    .append(author.getLastName())
                    .append("}");
        }

        builder.append("]");
        return builder.toString();
    }
}
