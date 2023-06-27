package com.synes.util;


import com.synes.util.gestionUtilisateur.Membre;

import java.util.Date;

public class Notification {

    private int id;
    private Membre membre;
    private String contenu;
    private Date envoyéLe;
    private String typeMessage;

    public Notification() {
    }

    public Notification(int id, Membre membre, String contenu, Date envoyéLe, String typeMessage) {
        this.id = id;
        this.membre = membre;
        this.contenu = contenu;
        this.envoyéLe = envoyéLe;
        this.typeMessage = typeMessage;
    }

    public Notification(String contenu, Date envoyéLe, String typeMessage) {
        this.contenu = contenu;
        this.envoyéLe = envoyéLe;
        this.typeMessage = typeMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getEnvoyéLe() {
        return envoyéLe;
    }

    public void setEnvoyéLe(Date envoyéLe) {
        this.envoyéLe = envoyéLe;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }
}
