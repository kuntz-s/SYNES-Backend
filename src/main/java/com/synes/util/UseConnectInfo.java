package com.synes.util;

public class UseConnectInfo {

    String token;
    Membre membre;
    String nomUniversite;

    public UseConnectInfo() {
    }
    public UseConnectInfo(String token, Membre membre, String nomUniversite) {
        this.token = token;
        this.membre = membre;
        this.nomUniversite = nomUniversite;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public String getNomUniversite() {
        return nomUniversite;
    }

    public void setNomUniversite(String nomUniversite) {
        this.nomUniversite = nomUniversite;
    }
}
