package com.synes.util.gestionUtilisateur;

import java.util.List;

public class UseConnectInfo {

    String token;
    Membre membre;
    String nomRole;
    List<String> listPermission;
    String nomUniversite;
    private int idMembre;


    public UseConnectInfo(String token, Membre leMembre, String nomRole, String nomUniv) {
    }
    public UseConnectInfo() {
    }

    public UseConnectInfo(String token, Membre membre, String nomRole, List<String> listPermission, String nomUniversite, int idMembre) {
        this.token = token;
        this.membre = membre;
        this.nomRole = nomRole;
        this.listPermission = listPermission;
        this.nomUniversite = nomUniversite;
        this.idMembre = idMembre;
    }

    public UseConnectInfo(String token, Membre membre, String nomRole, List<String> listPermission, String nomUniversite) {
        this.token = token;
        this.membre = membre;
        this.nomRole = nomRole;
        this.listPermission = listPermission;
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

    public String getNomRole() {
        return nomRole;
    }

    public void setNomRole(String nomRole) {
        this.nomRole = nomRole;
    }

    public List<String> getListPermission() {
        return listPermission;
    }

    public void setListPermission(List<String> listPermission) {
        this.listPermission = listPermission;
    }

    public String getNomUniversite() {
        return nomUniversite;
    }

    public void setNomUniversite(String nomUniversite) {
        this.nomUniversite = nomUniversite;
    }

    public void setMembreId(int idMembre) {
        this.idMembre =idMembre;
    }
    public int getMembreId() {
        return idMembre;
    }
}