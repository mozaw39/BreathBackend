package org.backend.repository;

import org.backend.modals.Personne;
import org.backend.modals.Urgencier;

import java.util.List;

public interface UrgencierRepoInt {
    Urgencier create(Urgencier user);
    void delete(Long id);
    Urgencier find(Long id);
    Urgencier update(Long userId, Urgencier user);
    Long countAll();
    List<Urgencier> findAll();
    boolean isPasswordCorrect(String user, String password);
    Long getUserId(String login);
}
