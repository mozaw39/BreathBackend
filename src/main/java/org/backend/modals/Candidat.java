package org.backend.modals;

import org.backend.modals.formation.Formation;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Candidat extends Personne {
    @ManyToOne
    Formation formation;
    public void addFormation(Formation formation){
        this.formation = formation;
        formation.addCandidat(this);
    }
}
