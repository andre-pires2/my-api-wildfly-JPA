package com.andre.myapi.user;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public User create(User user) {
        em.persist(user);
        return user;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    public List<User> findAll() {
        return em.createQuery(
                "SELECT u FROM User u", User.class
        ).getResultList();
    }

    public User update(User user) {
        return em.merge(user);
    }

    public void delete(Long id) {
        findById(id).ifPresent(em::remove);
    }

    public Optional<User> findByEmail(String email) {
        return em.createQuery(
                        "SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }
}
