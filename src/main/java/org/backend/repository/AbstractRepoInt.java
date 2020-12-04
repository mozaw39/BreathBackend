package org.backend.repository;

import org.backend.modals.Personne;

import javax.persistence.PersistenceContext;
import java.util.List;

public interface AbstractRepoInt<T extends Personne> {
    T create(T user) throws Exception;

    void delete(Long id);

    T find(Long id);

    T update(Long userId, T user);

    public List<T> findAll();

    List<T> findAll(Class<?> C);

    Personne isUserExist(String user, String password);

    Long getUserId(String login);
}
