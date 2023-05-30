package com.synes.util.gestionUtilisateur;

public class updrole {
    int id;
    String nom;
    String description;
    int idOrgane;

    public updrole() {
    }

    public updrole(int id, String nom, String description, int idOrgane) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.idOrgane = idOrgane;
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

    public int getIdOrgane() {
        return idOrgane;
    }

    public void setIdOrgane(int idOrgane) {
        this.idOrgane = idOrgane;
    }
}
