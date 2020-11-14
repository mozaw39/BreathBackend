package org.backend.repository;

import org.backend.exception.Exceptions;
import org.backend.modals.Admin;
import org.backend.modals.Urgencier;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class AdminRepo implements AdminRepoInt{
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;
    @Inject
    HelperRepo helperRepo;
    @Override
    public Admin create(Admin user) {
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
    public Admin find(Long id) {
        Admin user = em.find(Admin.class, id);
        if(user == null)
            throw new Exceptions.DataNotFoundException("message with id: " + id + " is not found");
        return user;
    }

    @Override
    public Admin update(Long userId, Admin newUser) {
        Admin user = em.find(Admin.class,userId);
        user.updateUser(newUser);
        //user.setLinks(newUser.getLinks());
        return user;
    }

    @Override
    public Long countAll() {
        TypedQuery query = em.createQuery("select count(user) from Admin user", Long.class);
        return (Long)query.getSingleResult();
    }

    @Override
    public List<Admin> findAll() {
        TypedQuery query = em.createQuery("select user from Admin user order by user.login", Admin.class);
        return query.getResultList();
    }

    @Override
    public boolean isPasswordCorrect(String user, String password) {
        TypedQuery query = em.createQuery("select user from Admin user order by user.login", Admin.class);
        List<Admin> users = query.getResultList();
        for(Admin iteratedPersonne : users){
            if(iteratedPersonne.getLogin().equals(user))
                if(iteratedPersonne.getMdp().equals(password))
                    return true;
        }
        return false;
    }

    @Override
    public Long getUserId(String login) {
        Admin userBis = new Admin();
        userBis.setLogin(login);
        List<Admin> users = findAll();
        for(Admin user : users){
            if(user.equals(userBis))
                return user.getUserId();
        }
        return -1l;
    }
}
