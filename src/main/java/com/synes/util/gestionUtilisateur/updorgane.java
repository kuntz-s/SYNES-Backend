package com.synes.util.gestionUtilisateur;

public class updorgane {
    int id;
    String nom;
    String description;
    int fondAlloue;
    int idUniv;

    public updorgane() {
    }

    public updorgane(int id, String nom, String description, int fondAlloue, int idUniv) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.fondAlloue = fondAlloue;
        this.idUniv = idUniv;
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

    public int getFondAlloue() {
        return fondAlloue;
    }

    public void setFondAlloue(int fondAlloue) {
        this.fondAlloue = fondAlloue;
    }

    public int getIdUniv() {
        return idUniv;
    }

    public void setIdUniv(int idUniv) {
        this.idUniv = idUniv;
    }
}
