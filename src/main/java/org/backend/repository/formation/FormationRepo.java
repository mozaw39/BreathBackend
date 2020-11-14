package org.backend.repository.formation;

import org.backend.exception.Exceptions;
import org.backend.modals.*;
import org.backend.modals.formation.Formation;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class FormationRepo implements FormationRepoInt {
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;

    @Override
    public Formation create(Formation formation) {
        em.persist(formation);
        return formation;
    }

    @Override
    public void delete(Long id) {
        em.remove(find(id));
    }

    @Override
    public Formation find(Long id) {
        Formation formation = em.find(Formation.class, id);
        if (formation == null)
            throw new Exceptions.DataNotFoundException("message with id: " + id + " is not found");
        return formation;
    }

    @Override
    public Long countAll() {
        TypedQuery query = em.createQuery("select count(formation) from Formation formation", Long.class);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Formation> findAll() {
        TypedQuery query = em.createQuery("select formation from Formation formation order by formation.dateDebut", Formation.class);
        return query.getResultList();
    }

    @Override
    public Formation update(Long formationId, Formation formationUser) {
        Formation formation = em.find(Formation.class, formationId);
        //user.setLinks(newUser.getLinks());
        return formation;
    }
}
