package com.synes.util.gestionUtilisateur;

public class updrole {
    int id;
    String nom;
    String description;
    updorgane updorgane;

    public updrole() {
    }

    public updrole(int id, String nom, String description, com.synes.util.gestionUtilisateur.updorgane updorgane) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.updorgane = updorgane;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public updorgane getUpdorgane() {
        return updorgane;
    }

    public void setUpdorgane(updorgane updorgane) {
        this.updorgane = updorgane;
    }
}
