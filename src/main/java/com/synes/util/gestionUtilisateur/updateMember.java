package com.synes.util.gestionUtilisateur;

public class updateMember {
    int id;
    Membre membre;

    public updateMember() {
    }

    public updateMember(int id, Membre membre) {
        this.id = id;
        this.membre = membre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }
}
