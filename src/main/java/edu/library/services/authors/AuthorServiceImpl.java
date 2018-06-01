package edu.library.services.authors;

import edu.library.dao.authors.AuthorDao;
import edu.library.entities.Author;
import edu.library.utils.AuthorQueries;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AuthorServiceImpl implements AuthorService{
    private AuthorDao authorDao;

    private SessionFactory sessionFactory;

    @Override
    public Author findById(long id) {
        return sessionFactory.getCurrentSession()
                             .get(Author.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Author> findAll() {
        return (List<Author>) sessionFactory.getCurrentSession()
                             .getNamedQuery(AuthorQueries.FIND_ALL)
                             .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Author> findByName(String firstName, String lastName) {
        return (List<Author>) sessionFactory.getCurrentSession()
                              .getNamedQuery(AuthorQueries.FIND_BY_NAME)
                              .setParameter("firstName", firstName)
                              .setParameter("lastName", lastName)
                              .list();
    }

    @Autowired
    public void setAuthorDao(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
