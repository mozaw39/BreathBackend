package org.backend.repository;

import org.backend.modals.Admin;
import org.backend.modals.Urgencier;

import java.util.List;

public interface AdminRepoInt {
    Admin create(Admin user);
    void delete(Long id);
    Admin find(Long id);
    Admin update(Long userId, Admin user);
    Long countAll();
    List<Admin> findAll();
    boolean isPasswordCorrect(String user, String password);
    Long getUserId(String login);
}
