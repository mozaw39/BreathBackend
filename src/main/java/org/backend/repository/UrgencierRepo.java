package org.backend.repository;

import org.backend.exception.Exceptions;
import org.backend.modals.Personne;
import org.backend.modals.Urgencier;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class UrgencierRepo implements UrgencierRepoInt{
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;
    @Inject
    HelperRepo helperRepo;
    @Override
    public Urgencier create(Urgencier user) {
        if(!helperRepo.isUserExistInAllTables(user.getLogin())) {
            em.persist(user);
            return user;
        }
        throw  new Exceptions.UserAlreadyExistException("user already exist");
    }

    @Override
    public void delete(Long id) {
        em.remove(find(id));
    }

    @Override
    public Urgencier find(Long id) {
        Urgencier user = em.find(Urgencier.class, id);
        if(user == null)
            throw new Exceptions.DataNotFoundException("message with id: " + id + " is not found");
        return user;
    }

    @Override
    public Urgencier update(Long userId, Urgencier newUser) {
        Urgencier user = em.getReference(Urgencier.class,userId);
        user.updateUser(newUser);
        //user.setLinks(newUser.getLinks());
        return user;
    }

    @Override
    public Long countAll() {
        TypedQuery query = em.createQuery("select count(user) from Urgencier user", Long.class);
        return (Long)query.getSingleResult();
    }

    @Override
    public List<Urgencier> findAll() {
        TypedQuery query = em.createQuery("select user from Urgencier user order by user.login", Urgencier.class);
        return query.getResultList();
    }

    @Override
    public boolean isPasswordCorrect(String user, String password) {
        TypedQuery query = em.createQuery("select user from Urgencier user order by user.login", Urgencier.class);
        List<Urgencier> users = query.getResultList();
        for(Urgencier iteratedPersonne : users){
            if(iteratedPersonne.getLogin().equals(user))
                if(iteratedPersonne.getMdp().equals(password))
                    return true;
        }
        return false;
    }

    @Override
    public Long getUserId(String login) {
        Urgencier userBis = new Urgencier();
        userBis.setLogin(login);
        List<Urgencier> users = findAll();
        for(Urgencier user : users){
            if(user.equals(userBis))
                return user.getUserId();
        }
        return -1l;
    }

}
