package org.backend.repository;

import org.backend.exception.Exceptions;
import org.backend.modals.Candidat;
import org.backend.modals.formation.Formation;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CandidatRepo implements CandidatRepoInt{
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;
    @Inject
    HelperRepo helperRepo;
    @Override
    public Candidat create(Candidat user) {
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
    public Candidat find(Long id) {
        Candidat user = em.find(Candidat.class, id);
        if(user == null)
            throw new Exceptions.DataNotFoundException("message with id: " + id + " is not found");
        return user;
    }

    @Override
    public Candidat update(Long userId, Candidat newUser) {
        Candidat user = em.find(Candidat.class,userId);
        user.updateUser(newUser);
        //user.setLinks(newUser.getLinks());
        return user;
    }

    @Override
    public Long countAll() {
        TypedQuery query = em.createQuery("select count(user) from Candidat user", Long.class);
        return (Long)query.getSingleResult();
    }

    @Override
    public List<Candidat> findAll() {
        TypedQuery query = em.createQuery("select user from Candidat user order by user.login", Candidat.class);
        return query.getResultList();
    }

    @Override
    public boolean isPasswordCorrect(String user, String password) {
        TypedQuery query = em.createQuery("select user from Candidat user order by user.login", Candidat.class);
        List<Candidat> users = query.getResultList();
        for(Candidat iteratedPersonne : users){
            if(iteratedPersonne.getLogin().equals(user))
                if(iteratedPersonne.getMdp().equals(password))
                    return true;
        }
        return false;
    }

    @Override
    public Long getUserId(String login) {
        Candidat userBis = new Candidat();
        userBis.setLogin(login);
        List<Candidat> users = findAll();
        for(Candidat user : users){
            if(user.equals(userBis))
                return user.getUserId();
        }
        return -1l;
    }

    @Override
    public void addFormation(Candidat candidat, Formation formation) {
        candidat.addFormation(formation);
    }
}
