package org.backend.repository;

import org.backend.modals.Personne;

import java.util.List;

public interface AbstractRepoInt<T extends Personne> {
    T create(T user);

    void delete(Long id);

    T find(Long id);

    T update(Long userId, T user);

    Long countAll();

    List<T> findAll();

    boolean isPasswordCorrect(String user, String password);

    Long getUserId(String login);
}
