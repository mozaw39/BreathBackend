package org.backend.repository;

import org.backend.modals.Candidat;
import org.backend.modals.formation.Formation;
import org.backend.repository.qualifiers.CandidatQualifier;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
@Transactional
@CandidatQualifier
public class CandidatRepo extends AbstractRepo<Candidat> implements CandidatRepoInt{
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;
    public CandidatRepo() {
        super(Candidat.class);
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void addFormation(Candidat candidat, Formation formation) {
        //todo, each user can subscribe to a formation and view it in his personnel space
    }
}
