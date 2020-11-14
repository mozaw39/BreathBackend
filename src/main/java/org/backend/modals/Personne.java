package org.backend.modals;

import javax.persistence.*;

@Entity
@Inheritance(
        strategy = InheritanceType.TABLE_PER_CLASS
)
@Table(name = "table_Personne")
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String login;
    private String mdp;
    private String nom;
    private String prenom;
    private String numTele;
    private String adresseMail;
    private UserType userType;

/*    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    private ArrayList<Link> links = new ArrayList<>();
*/
    public Personne() {
    }

    public Personne(String login, String mdp, String nom, String prenom, String numTele, String adresseMail, UserType userType) {
        this.login = login;
        this.mdp = mdp;
        this.nom = nom;
        this.prenom = prenom;
        this.numTele = numTele;
        this.adresseMail = adresseMail;
        this.userType = userType;
        //this.links = links;
    }

    public void updateUser(Personne newUser){
        this.setLogin(newUser.getLogin());
        this.setMdp(newUser.getMdp());
        this.setNom(newUser.getNom());
        this.setAdresseMail(newUser.getAdresseMail());
        this.setNumTele(newUser.getNumTele());
    }
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String user) {
        this.login = user;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumTele() {
        return numTele;
    }

    public void setNumTele(String numTele) {
        this.numTele = numTele;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Personne)) return false;
        Personne user1 = (Personne) o;
        return (this != null ) && (user1 != null) &&  (login.equals(user1.login));
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (mdp != null ? mdp.hashCode() : 0);
        return result;
    }

   /* @Override
    public Iterator<Link> iterator() {
        return links.iterator();
    }
    public void addLink(String url, String rel){
        Link link = new Link(url, rel);
        links.add(link);
    }*/
}
