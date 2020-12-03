package org.backend.repository;

import org.backend.modals.Candidat;
import org.backend.modals.formation.Formation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
@Transactional
public class CandidatRepo extends AbstractRepo<Candidat> implements CandidatRepoInt{
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;

    public CandidatRepo(Class<Candidat> typeParameterClass) {
        super(typeParameterClass);
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void addFormation(Candidat candidat, Formation formation) {
        //todo, each user can subscribe to a formation and view it in his personnel space
    }
}
