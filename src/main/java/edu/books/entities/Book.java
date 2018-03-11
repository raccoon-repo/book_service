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
    @NamedQuery(name = BookQueries.FIND_BY_TITLE, query = BookQueries.FIND_BY_TITLE_QUERY),
    @NamedQuery(name = BookQueries.FIND_BY_GENRE, query = BookQueries.FIND_BY_GENRE_QUERY)
})
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Embedded
    private Genre genre;

    @Temporal(TemporalType.DATE)
    @Column(name = "publish_date")
    private Date publishDate;

    @Enumerated(EnumType.STRING)
    @Column(name="rating")
    private Rating rating;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public enum Rating {
        WORST, BAD, OKAY, GOOD, BEST
    }

    @Embeddable
    public static class Genre implements Serializable {

        @Column(name = "genre")
        private String genre;

        @Column(name = "sub_genre")
        private String subGenre;

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public String getSubGenre() {
            return subGenre;
        }

        public void setSubGenre(String subGenre) {
            this.subGenre = subGenre;
        }

        @Override
        public boolean equals(Object o) {
            if(!(o instanceof Genre))
                return false;
            else {
                Genre g = (Genre) o;
                return genre.equals(g.genre);
            }
        }
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
