package org.backend.repository;

import org.backend.modals.Admin;
import org.backend.modals.SimpleUser;
import org.backend.modals.Urgencier;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class HelperRepo {
    @PersistenceContext(unitName = "BreathPU")
    EntityManager em;

    public boolean isSimpleUserExist(String user) {
        TypedQuery query = em.createQuery("select user from SimpleUser user order by user.login", SimpleUser.class);
        List<SimpleUser> users = query.getResultList();
        for(SimpleUser iteratedPersonne : users){
            if(iteratedPersonne.getLogin().equals(user))
                return true;
        }
        return false;
    }

    public boolean isUrgenicerExist(String user) {
        TypedQuery query = em.createQuery("select user from Urgencier user order by user.login", Urgencier.class);
        List<Urgencier> users = query.getResultList();
        for(Urgencier iteratedPersonne : users){
            if(iteratedPersonne.getLogin().equals(user))
                return true;
        }
        return false;
    }

    public boolean isAdminExist(String user) {
        TypedQuery query = em.createQuery("select user from Admin user order by user.login", Admin.class);
        List<Admin> users = query.getResultList();
        for(Admin iteratedPersonne : users){
            if(iteratedPersonne.getLogin().equals(user))
                return true;
        }
        return false;
    }

    public boolean isUserExistInAllTables(String user){

        return isSimpleUserExist(user) || isUrgenicerExist(user) || isAdminExist(user);
    }
}
