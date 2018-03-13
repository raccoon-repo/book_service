package edu.books.entities;

import edu.books.utils.AuthorQueries;
import org.hibernate.annotations.Cascade;

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

    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authors")
    @Cascade({SAVE_UPDATE, PERSIST, REFRESH, MERGE})
    private List<Book> books = new ArrayList<>();

    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

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
            return id == a.id && firstName.equals(a.firstName)
                    && lastName.equals(a.lastName);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", books=" + books +
                '}';
    }
}
