package com.synes.util;

public class Membre {

    String matricule;
    String nom;
    String prenom;
    String email;
    String photo;
    String motdepasse;
    String role;
    int iduniversite;

    public Membre() {

    }

    public Membre(String matricule, String nom, String prenom, String email, String photo, String motdepasse, String role, int iduniversite) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.photo = photo;
        this.motdepasse = motdepasse;
        this.role = role;
        this.iduniversite = iduniversite;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getIduniversite() {
        return iduniversite;
    }

    public void setIduniversite(int iduniversite) {
        this.iduniversite = iduniversite;
    }
}
