package org.backend.repository;

import org.backend.modals.Personne;
import org.backend.modals.SimpleUser;

import java.util.List;

public interface RepositoryInt {
    Personne create(Personne user);

    void delete(Long id);

    Personne find(Long id);

    Personne update(Long userId, Personne user);

    Long countAll();

    List<Personne> findAll();

    boolean isUserOrPasswordCorrect(String user, String password);

    Long getUserId(String login);
}
