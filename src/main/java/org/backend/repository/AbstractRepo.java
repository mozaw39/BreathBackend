package org.backend.repository;

import org.backend.exception.Exceptions;
import org.backend.modals.Personne;


import javax.ejb.EJBTransactionRolledbackException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.RollbackException;
import java.util.List;

public abstract class AbstractRepo<T extends Personne> implements AbstractRepoInt<T> {

    final Class<T> typeParameterClass;

    protected abstract EntityManager getEntityManager();
    public AbstractRepo(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public T getInstanceOfT(Class<T> aClass){
        try {
            return aClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public T create(T user){
        getEntityManager().persist(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        getEntityManager().remove(find(id));
    }

    @Override
    public T find(Long id) {
        T user = getEntityManager().find(typeParameterClass, id);
        if (user == null)
            throw new Exceptions.DataNotFoundException("message with id: " + id + " is not found");
        return user;
    }
    public T find(Long id, Class<?> C) {
        T user = (T) getEntityManager().find(C, id);
        if (user == null)
            throw new Exceptions.DataNotFoundException("message with id: " + id + " is not found");
        return user;
    }


    @Override
    public List<T> findAll(Class<?> C) {
        TypedQuery query = getEntityManager().createQuery("select user from "+ C.getName() + " user order by user.login", C);
        return query.getResultList();
    }

    @Override
    public List<T> findAll() {
        TypedQuery query = getEntityManager().createQuery("select user from "+ typeParameterClass.getName() + " user order by user.login", typeParameterClass);
        return query.getResultList();
    }

    @Override
    public T update(Long userId, T newUser) {
        T user = getEntityManager().find(typeParameterClass, userId);
        user.updateUser(newUser);
        //user.setLinks(newUser.getLinks());
        return user;
    }
    /*public boolean isUserExist(String user) {
       TypedQuery query = getEntityManager().createQuery("select user from "+ typeParameterClass.getName() + " user order by user.login", typeParameterClass);
        List<T> users = query.getResultList();
        for(T iteratedPersonne : users){
            if(iteratedPersonne.getLogin().equals(user))
                return true;
        }
        return false;
    }*/

    @Override
    public Personne isUserExist(String user, String password) {
        TypedQuery query = getEntityManager().createQuery("select user from "+ typeParameterClass.getName() + " user order by user.login", typeParameterClass);
        List<T> users = query.getResultList();
        for (T iteratedPersonne : users) {
            if (iteratedPersonne.getLogin().equals(user))
                if (iteratedPersonne.getMdp().equals(password))
                    return iteratedPersonne;
        }

        return null;
    }

    @Override
    public Long getUserId(String login) {
        T userBis = getInstanceOfT(typeParameterClass);
        userBis.setLogin(login);
        List<T> users = findAll(typeParameterClass);
        for (T user : users) {
            if (user.equals(userBis))
                return user.getUserId();
        }
        return -1l;
    }
}
