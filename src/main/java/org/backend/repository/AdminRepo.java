package org.backend.repository;

import org.backend.modals.Admin;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class AdminRepo extends AbstractRepo<Admin> {
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }
    public AdminRepo() {
        super(Admin.class);
    }
}
