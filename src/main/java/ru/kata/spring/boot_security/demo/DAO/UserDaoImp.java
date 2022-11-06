package ru.kata.spring.boot_security.demo.DAO;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    @Override
    public List<User> printUser() {
        return entityManager.createQuery("select e from User e ", User.class).getResultList();
    }
    @Transactional
    @Override
    public void add(User user) {
        entityManager.persist(user);
    }
    @Transactional
    @Override
    public void deleteById(Long id) {
        entityManager.remove(getById(id));
    }
    @Transactional
    @Override
    public void edit(User user) {
        entityManager.merge(user);
    }
    @Transactional
    @Override
    public User getById(Long id) {
        return entityManager.find(User.class,id);
    }

    @Override
    public UserDetails getByName(String username) {
        return entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
