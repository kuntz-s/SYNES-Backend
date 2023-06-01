
package com.synes.util.gestionUtilisateur;

import com.synes.util.baseDeDonnee.BaseDeDonnee;
import java.util.Date;

public class Membre {

    BaseDeDonnee baseDeDonnee = new BaseDeDonnee();

    String matricule;
    String noms;
    String prenom;
    String email;
    String photo;
    String motdepasse;
    Universite universite;
    Role role;

    Date dateInscription;



    public Membre() {

    }

    public Membre(String matricule, String noms, String prenom, String email, String photo, String motdepasse, Universite Universite, Date dateInscription) {
        this.matricule = matricule;
        this.noms = noms;
        this.prenom = prenom;
        this.email = email;
        this.photo = photo;
        this.motdepasse = motdepasse;
        this.universite = Universite;
        //this.idRole = baseDeDonnee.getRoleIdByUnivId(iduniversite);
        this.dateInscription = dateInscription;
    }
    public Membre(String matricule, String noms, String prenom, String email, String photo, String motdepasse, Role Role, Universite Universite, Date dateInscription) {
        this.matricule = matricule;
        this.noms = noms;
        this.prenom = prenom;
        this.email = email;
        this.photo = photo;
        this.motdepasse = motdepasse;
        this.universite = Universite;
        this.role = Role;
        this.dateInscription = dateInscription;
    }
    public Membre(String matricule, String noms, String prenom, String email, String photo, Role Role, Universite Universite, Date dateInscription) {
        this.matricule = matricule;
        this.noms = noms;
        this.prenom = prenom;
        this.email = email;
        this.photo = photo;
        this.universite = Universite;
        this.role = Role;
        this.dateInscription = dateInscription;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getNoms() {
        return noms;
    }

    public void setNoms(String noms) {
        this.noms = noms;
    }

    public Universite getUniversite() {
        return universite;
    }

    public void setUniversite(Universite universite) {
        this.universite = universite;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }
}
