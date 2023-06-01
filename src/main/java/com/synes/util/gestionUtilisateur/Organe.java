package com.synes.util.gestionUtilisateur;

public class Organe {
    int id;
    String nom;
    String description;
    int fondAlloue;
    Universite universite;

    public Organe() {
    }

    public Organe(int id, String nom, String description, int fondAlloue, Universite Universite) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.fondAlloue = fondAlloue;
        this.universite = Universite;
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

    public Universite getUniversite() {
        return universite;
    }

    public void setUniversite(Universite universite) {
        this.universite = universite;
    }
}
