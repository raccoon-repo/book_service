package edu.books.entities;


import edu.books.utils.BookQueries;
import org.hibernate.annotations.Cascade;

import static org.hibernate.annotations.CascadeType.*;

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
    @NamedQuery(name = BookQueries.FIND_BY_GENRE, query = BookQueries.FIND_BY_GENRE_QUERY),
    @NamedQuery(name = BookQueries.FIND_BY_DATE, query = BookQueries.FIND_BY_DATE_QUERY),
    @NamedQuery(name= BookQueries.FIND_BY_RATING, query = BookQueries.FIND_BY_RATING_QUERY)
})
public class Book implements Serializable {

    /* ********** properties ********** */

    private long id;

    private String title;

    private Genre genre;

    private Date publishDate;

    private byte[] cover;

    private Rating rating;

    private List<Author> authors = new ArrayList<>();

    private Set<String> tags = new HashSet<>();

    private long version;

    public Book() {
    }

    /* ********** ********** ********** */


    /* ***** getters and setters ****** */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "publish_date")
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({SAVE_UPDATE, PERSIST, REFRESH, MERGE})
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }


    @Version
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Basic(fetch = FetchType.LAZY)
    @Lob @Column(name = "cover")
    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    @ElementCollection @Column(name = "tag")
    @CollectionTable(name = "tag", joinColumns = @JoinColumn(name = "book_id"))
    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="rating")
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Embedded
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /* ********** ********** ********** */


    public void addAuthor(Author author) {
        authors.add(author);
        author.addBook(this);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
        author.removeBook(this);
    }

    public void addTag(String tag) {
        tags.add(tag.toLowerCase());
    }

    public void removeTag(String tag) {
        tags.remove(tag.toLowerCase());
    }

    public enum Rating {
        WORST, BAD, OKAY, GOOD, BEST
    }

    @Override
    public int hashCode() {
        return (int)(id ^ (id >>> 32));
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Book) {
            Book b = (Book)o;

            return id == b.id;
        }
        return false;
    }


    @Embeddable
    public static class Genre implements Serializable {

        private String genre;

        private String subGenre;

        @Column(name = "genre")
        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        @Column(name = "sub_genre")
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
}
