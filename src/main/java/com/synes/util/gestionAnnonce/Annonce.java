package com.synes.util.gestionAnnonce;

import com.synes.util.gestionUtilisateur.Membre;

public class Annonce {
    private int id;
    private String titre;
    private String contenu;
    private String typeAnnonce;
    private Object posteLe;
    private Membre membre;

    public Annonce() {
    }

    public Annonce(int id, String titre, String contenu, String typeAnnonce, Object posteLe, Membre membre) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.typeAnnonce = typeAnnonce;
        this.posteLe = posteLe;
        this.membre = membre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getTypeAnnonce() {
        return typeAnnonce;
    }

    public void setTypeAnnonce(String typeAnnonce) {
        this.typeAnnonce = typeAnnonce;
    }

    public Object getPosteLe() {
        return posteLe;
    }

    public void setPosteLe(Object posteLe) {
        this.posteLe = posteLe;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }
}
