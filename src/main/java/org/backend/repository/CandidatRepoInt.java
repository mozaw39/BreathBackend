package org.backend.repository;

import org.backend.modals.Candidat;
import org.backend.modals.formation.Formation;


public interface CandidatRepoInt {
    void addFormation(Candidat candidat, Formation formation);
}
