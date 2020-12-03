package org.backend.repository;

import org.backend.modals.Admin;
import org.backend.repository.qualifiers.AdminQualifier;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@AdminQualifier
@Transactional
public class AdminRepo extends AbstractRepo<Admin> {
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;
    public AdminRepo() {
        super(Admin.class);
    }

    protected EntityManager getEntityManager() {
        return em;
    }
}
