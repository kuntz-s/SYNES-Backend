package com.synes.util.gestionAnnonce;

public class PieceJointe {
    private int id;
    private int idAnnonce;
    private String nom;
    private String url;

    public PieceJointe(int id, int idAnnonce, String nom, String url) {
        this.id = id;
        this.idAnnonce = idAnnonce;
        this.nom = nom;
        this.url = url;
    }

    public PieceJointe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAnnonce() {
        return idAnnonce;
    }

    public void setIdAnnonce(int idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

