package org.backend.repository;

import org.backend.modals.Personne;
import org.backend.modals.SimpleUser;

import java.util.List;

public interface UserRepoInt {
    SimpleUser create(SimpleUser user);
    void delete(Long id);
    SimpleUser find(Long id);
    SimpleUser update(Long userId, SimpleUser user);
    Long countAll();
    List<SimpleUser> findAll();
    boolean isPasswordCorrect(String user, String password);
    Long getUserId(String login);
}
