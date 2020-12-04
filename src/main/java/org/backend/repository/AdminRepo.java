package org.backend.repository;

import org.backend.modals.Admin;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
