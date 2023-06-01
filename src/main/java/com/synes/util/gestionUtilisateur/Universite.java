package com.synes.util.gestionUtilisateur;

public class Universite {
    int id;
    String nom;
    String localisation;
    String logo;

    public Universite() {}
    public Universite(int id, String nom, String localisation, String logo) {
        this.id = id;
        this.nom = nom;
        this.localisation = localisation;
        this.logo = logo;
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

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
