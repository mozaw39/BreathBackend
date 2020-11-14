package org.backend.modals.formation;

import org.backend.modals.Candidat;
import org.backend.modals.Personne;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.spi.DateFormatProvider;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@XmlRootElement
@Table(name = "table_formation")
public class Formation {
    @Id
    @XmlElement
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long formationId;
    @XmlElement
    private String ville;
    @XmlElement
    private String formateur;
    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    @JsonbDateFormat("yyyy-MM-dd")
    private Date dateDebut, dateFin;
    @OneToMany
    private List<Candidat> candidatList = new ArrayList<>();

    public Formation(String ville, String formateur, Date dateDebut, Date dateFin) {
        this.ville = ville;
        this.formateur = formateur;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Formation() {
    }

    public void addCandidat(Candidat candidat){
        candidatList.add(candidat);
    }

    public Long getFormationId() {
        return formationId;
    }

    public void setFormationId(Long formationId) {
        this.formationId = formationId;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getFormateur() {
        return formateur;
    }

    public void setFormateur(String formateur) {
        this.formateur = formateur;
    }
    public Date getDateDebut() {
        return dateDebut;
    }
    @XmlElement
//    public String getStrDateDebut() {
//        if(dateDebut == null) return null;
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        return dateFormat.format(dateDebut);
//    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

//    public void setDateDebut(String dateDebut) throws Exception{
//        this.dateDebut = new SimpleDateFormat("yyyy-MM-dd").parse(dateDebut);
//    }
    public Date getDateFin() {
        return dateFin;
    }

//    public String getStrDateFin() {
//        if(dateFin == null) return null;
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        return dateFormat.format(dateFin);
//    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    //public void setDateFin(String dateFin) throws Exception { this.dateFin = new SimpleDateFormat("yyyy-MM-dd").parse(dateFin); }
}
