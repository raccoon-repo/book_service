package edu.books.entities;

import edu.books.app.model.BookQuery;
import edu.books.utils.AuthorQueries;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

import static org.hibernate.annotations.CascadeType.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
@NamedQueries({
    @NamedQuery(name=AuthorQueries.FIND_ALL, query = AuthorQueries.FIND_ALL_QUERY),
    @NamedQuery(name = AuthorQueries.FIND_BY_NAME, query = AuthorQueries.FIND_BY_NAME_QUERY)
})
public class Author implements Serializable {

    /* ********** properties ********** */

    private long id;

    private String firstName;

    private String lastName;

    private List<Book> books = new ArrayList<>();

    private byte[] photo;

    private long version;

    /* ********** ********** ********** */


    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /* ***** getters and setters ****** */

    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authors")
    @Cascade({SAVE_UPDATE, PERSIST, REFRESH, MERGE})
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Version
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "photo")
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }


    /* ********** ********** ********** */

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Author) {
            Author a = (Author) o;
            return id == a.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int)(id ^ (id >>> 32));
    }
}
