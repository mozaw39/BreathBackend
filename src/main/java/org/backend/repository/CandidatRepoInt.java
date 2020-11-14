package org.backend.repository;

import org.backend.modals.Candidat;
import org.backend.modals.formation.Formation;

import java.util.List;

public interface CandidatRepoInt {
    Candidat create(Candidat user);
    void delete(Long id);
    Candidat find(Long id);
    Candidat update(Long userId, Candidat user);
    Long countAll();
    List<Candidat> findAll();
    boolean isPasswordCorrect(String user, String password);
    Long getUserId(String login);
    void addFormation(Candidat candidat, Formation formation);
}
