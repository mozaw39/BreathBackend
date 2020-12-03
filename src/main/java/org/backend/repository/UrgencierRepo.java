package org.backend.repository;


import org.backend.modals.Urgencier;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class UrgencierRepo extends AbstractRepo<Urgencier> implements UrgencierRepoInt{
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;

    public UrgencierRepo(Class<Urgencier> typeParameterClass) {
        super(typeParameterClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return null;
    }
}
