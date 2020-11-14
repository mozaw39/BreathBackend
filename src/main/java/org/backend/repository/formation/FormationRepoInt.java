package org.backend.repository.formation;

import org.backend.modals.Admin;
import org.backend.modals.Urgencier;
import org.backend.modals.formation.Formation;

import java.util.List;

public interface FormationRepoInt {
    Formation create(Formation formation);
    void delete(Long id);
    Formation find(Long id);
    Formation update(Long formationId, Formation formation);
    Long countAll();
    List<Formation> findAll();
}
