package org.backend.repository;

import org.backend.modals.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class Repository extends AbstractRepo<Personne> implements RepositoryInt {
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;

    public Repository(Class<Personne> typeParameterClass) {
        super(typeParameterClass);
    }

    protected EntityManager getEntityManager() {
        return em;
    }


}
