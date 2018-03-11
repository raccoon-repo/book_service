package edu.books.services.authors;

import edu.books.entities.Author;
import edu.books.utils.AuthorQueries;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Repository("authorDao")
public class AuthorDaoImpl implements AuthorDao {

    @Resource(name = "hibernateSessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public Author findById(long id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Author.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Author> findAll() {
        Session session = sessionFactory.getCurrentSession();

        return  (List<Author>) session.getNamedQuery(AuthorQueries.FIND_ALL)
                .list();
    }


    //the use case is when creating Author object without `id` property set
    //therefore we would like to find all authors with the same name
    @Override
    public List<Author> find(Author author) {
        return findByName(author.getFirstName(), author.getLastName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Author> findByName(String firstName, String lastName) {
        Session session = sessionFactory.getCurrentSession();

        return  (List<Author>) session.getNamedQuery(AuthorQueries.FIND_BY_NAME)
                .setParameter("firstName", firstName).setParameter("lastName", lastName)
                .list();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
