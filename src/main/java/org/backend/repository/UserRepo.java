package org.backend.repository;

import org.backend.exception.Exceptions;
import org.backend.modals.*;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class UserRepo implements UserRepoInt {
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;
    @Inject
    HelperRepo helperRepo;

    @Override
    public SimpleUser create(SimpleUser user) {
        if (!helperRepo.isUserExistInAllTables(user.getLogin())) {
            em.persist(user);
            return user;
        }
        throw new Exceptions.UserAlreadyExistException("user already exist");
    }

    @Override
    public void delete(Long id) {
        em.remove(find(id));
    }

    @Override
    public SimpleUser find(Long id) {
        SimpleUser user = em.find(SimpleUser.class, id);
        if (user == null)
            throw new Exceptions.DataNotFoundException("message with id: " + id + " is not found");
        return user;
    }

    @Override
    public Long countAll() {
        TypedQuery query = em.createQuery("select count(user) from SimpleUser user", Long.class);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<SimpleUser> findAll() {
        TypedQuery query = em.createQuery("select user from SimpleUser user order by user.login", SimpleUser.class);
        return query.getResultList();
    }

    @Override
    public SimpleUser update(Long userId, SimpleUser newUser) {
        SimpleUser user = em.find(SimpleUser.class, userId);
        user.updateUser(newUser);
        //user.setLinks(newUser.getLinks());
        return user;
    }

    @Override
    public boolean isPasswordCorrect(String user, String password) {
        TypedQuery query = em.createQuery("select user from SimpleUser user order by user.login", SimpleUser.class);
        List<SimpleUser> users = query.getResultList();
        for (SimpleUser iteratedPersonne : users) {
            if (iteratedPersonne.getLogin().equals(user))
                if (iteratedPersonne.getMdp().equals(password))
                    return true;
        }

        return false;
    }

    @Override
    public Long getUserId(String login) {
        SimpleUser userBis = new SimpleUser();
        userBis.setLogin(login);
        List<SimpleUser> users = findAll();
        for (Personne user : users) {
            if (user.equals(userBis))
                return user.getUserId();
        }
        return -1l;
    }
}
