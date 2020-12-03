package org.backend.repository;

import org.backend.modals.*;
import org.backend.repository.qualifiers.SimpleUserQualifier;


import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@SimpleUserQualifier
public class UserRepo extends AbstractRepo<SimpleUser> implements UserRepoInt {
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;

    public UserRepo() {
        super(SimpleUser.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
