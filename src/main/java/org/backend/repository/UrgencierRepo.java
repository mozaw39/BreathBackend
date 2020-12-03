package org.backend.repository;


import org.backend.modals.Urgencier;
import org.backend.repository.qualifiers.UrgencierQualifier;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@UrgencierQualifier
public class UrgencierRepo extends AbstractRepo<Urgencier> implements UrgencierRepoInt{
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;
    public UrgencierRepo() {
        super(Urgencier.class);
    }

    protected EntityManager getEntityManager() {
        return em;
    }
}
