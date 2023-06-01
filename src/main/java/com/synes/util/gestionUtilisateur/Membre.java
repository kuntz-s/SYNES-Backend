
package com.synes.util.gestionUtilisateur;

import com.synes.util.baseDeDonnee.BaseDeDonnee;
import java.util.Date;

public class Membre {

    BaseDeDonnee baseDeDonnee = new BaseDeDonnee();

    String matricule;
    String nom;
    String prenom;
    String email;
    String photo;
    String motdepasse;
    upduniv upduniv;
    updrole updrole;

    Date dateInscription;



    public Membre() {

    }

    public Membre(String matricule, String nom, String prenom, String email, String photo, String motdepasse, upduniv upduniv, Date dateInscription) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.photo = photo;
        this.motdepasse = motdepasse;
        this.upduniv = upduniv;
        //this.idRole = baseDeDonnee.getRoleIdByUnivId(iduniversite);
        this.dateInscription = dateInscription;
    }
    public Membre(String matricule, String nom, String prenom, String email, String photo, String motdepasse,updrole updrole, upduniv upduniv, Date dateInscription) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.photo = photo;
        this.motdepasse = motdepasse;
        this.upduniv = upduniv;
        this.updrole = updrole;
        this.dateInscription = dateInscription;
    }
    public Membre(String matricule, String nom, String prenom, String email, String photo,updrole updrole, upduniv upduniv, Date dateInscription) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.photo = photo;
        this.upduniv = upduniv;
        this.updrole = updrole;
        this.dateInscription = dateInscription;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
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

    public com.synes.util.gestionUtilisateur.upduniv getUpduniv() {
        return upduniv;
    }

    public void setUpduniv(com.synes.util.gestionUtilisateur.upduniv upduniv) {
        this.upduniv = upduniv;
    }

    public com.synes.util.gestionUtilisateur.updrole getUpdrole() {
        return updrole;
    }

    public void setUpdrole(com.synes.util.gestionUtilisateur.updrole updrole) {
        this.updrole = updrole;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }
}
