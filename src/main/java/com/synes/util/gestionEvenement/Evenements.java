package com.synes.util.gestionEvenement;

import com.synes.util.gestionUtilisateur.Membre;

import java.util.Date;


/*
{
    "nom":"cotisation juin",
    "dateDebut":"2023-06-01T23:45:17",
    "dateFin":"2023-06-30T23:45:17",
    "description":"cotisation mensuel du mois de juin",
    "photo":""
}
 */
public class Evenements {

    int id;
    String nom;
    Date dateDebut;
    Date dateFin;
    String description;
    Membre membre;
    String photo;

    public Evenements() { }

    public Evenements(int id, String nom, Date dateDebut, Date dateFin, String description, Membre membre, String photo) {
        this.id = id;
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
        this.membre = membre;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
