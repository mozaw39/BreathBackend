package org.backend.repository;

import org.backend.exception.Exceptions;
import org.backend.modals.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class Repository implements RepositoryInt {
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;
    @Inject
    HelperRepo helperRepo;

    @Override
    public Personne create(Personne user) {
        if (!isUserExist(user.getLogin())) {
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
    public Personne find(Long id) {
        Personne user = em.find(Personne.class, id);
        if (user == null)
            throw new Exceptions.DataNotFoundException("message with id: " + id + " is not found");
        return user;
    }

    @Override
    public Long countAll() {
        TypedQuery query = em.createQuery("select count(user) from Personne user", Long.class);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Personne> findAll() {
        TypedQuery query = em.createQuery("select user from Personne user order by user.login", Personne.class);
        return query.getResultList();
    }

    @Override
    public Personne update(Long userId, Personne newUser) {
        Personne user = em.find(Personne.class, userId);
        user.updateUser(newUser);
        //user.setLinks(newUser.getLinks());
        return user;
    }
    public boolean isUserExist(String user) {
        TypedQuery query = em.createQuery("select user from Personne user order by user.login", Personne.class);
        List<Personne> users = query.getResultList();
        for(Personne iteratedPersonne : users){
            if(iteratedPersonne.getLogin().equals(user))
                return true;
        }
        return false;
    }

    @Override
    public boolean isUserOrPasswordCorrect(String user, String password) {
        TypedQuery query = em.createQuery("select user from Personne user order by user.login", Personne.class);
        List<Personne> users = query.getResultList();
        for (Personne iteratedPersonne : users) {
            if (iteratedPersonne.getLogin().equals(user))
                if (iteratedPersonne.getMdp().equals(password))
                    return true;
        }

        return false;
    }

    @Override
    public Long getUserId(String login) {
        Personne userBis = new Personne();
        userBis.setLogin(login);
        List<Personne> users = findAll();
        for (Personne user : users) {
            if (user.equals(userBis))
                return user.getUserId();
        }
        return -1l;
    }
}
