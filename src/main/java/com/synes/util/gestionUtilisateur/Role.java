package com.synes.util.gestionUtilisateur;

public class Role {
    int id;
    String nom;
    String description;
    Organe organe;

    public Role() {
    }

    public Role(int id, String nom, String description, Organe Organe) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.organe = Organe;
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

    public Organe getOrgane() {
        return organe;
    }

    public void setOrgane(Organe organe) {
        this.organe = organe;
    }
}
