package com.synes.util.gestionUtilisateur;

public class Avertissement {

    private int id;
    private String raison;
    public int idMembre;


    public Avertissement() {
    }

    public Avertissement(int id, String raison, int idMembre) {
        this.id = id;
        this.raison = raison;
        this.idMembre = idMembre;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

    public int getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
