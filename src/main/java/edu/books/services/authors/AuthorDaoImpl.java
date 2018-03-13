package edu.books.services.authors;

import edu.books.entities.Author;
import edu.books.utils.AuthorQueries;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository("authorDao")
@Transactional
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
