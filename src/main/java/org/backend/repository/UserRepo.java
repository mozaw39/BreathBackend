package org.backend.repository;

import org.backend.modals.*;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class UserRepo extends AbstractRepo<SimpleUser> implements UserRepoInt {
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;

    public UserRepo(Class<SimpleUser> typeParameterClass) {
        super(typeParameterClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
