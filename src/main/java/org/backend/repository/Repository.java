package org.backend.repository;

import org.backend.modals.*;
import org.backend.repository.qualifiers.RepositoryQualifier;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@RepositoryQualifier
public class Repository extends AbstractRepo<Personne> implements RepositoryInt {
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;
    public Repository() {
        super(Personne.class);
    }

    protected EntityManager getEntityManager() {
        return em;
    }


}
