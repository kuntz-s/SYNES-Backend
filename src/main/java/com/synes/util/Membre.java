package com.synes.util;

/*
{
        "matricule":"1z1z1z",
        "nom":"toto",
        "prenom":"liu",
        "email":"tot.liuàei.com",
        "photo":"",
        "motdepasse":"12345",
        "idRole":1,
        "iduniversite":1,
        "dateInscription":"2023-05-22 14:19:35"
        }


        {
    "email":"nzouetengmicaelle@gmail.com",
    "password":"Micaelle4"
}
        */

import java.util.Date;

public class Membre {

    String matricule;
    String nom;
    String prenom;
    String email;
    String photo;
    String motdepasse;
    int idRole;
    int iduniversite;
    Date dateInscription;

    public Membre() {

    }

    public Membre(String matricule, String nom, String prenom, String email, String photo, String motdepasse, int idRole, int iduniversite, Date dateInscription) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.photo = photo;
        this.motdepasse = motdepasse;
        this.idRole = idRole;
        this.iduniversite = iduniversite;
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

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public int getIduniversite() {
        return iduniversite;
    }

    public void setIduniversite(int iduniversite) {
        this.iduniversite = iduniversite;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }
}
