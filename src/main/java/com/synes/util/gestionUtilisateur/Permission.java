package com.synes.util.gestionUtilisateur;

public class Permission {
    int id;
    String nom;
    String description;
    Role role;

    public Permission() {
    }
    public Permission(int id, String nom, String description, Role role) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
