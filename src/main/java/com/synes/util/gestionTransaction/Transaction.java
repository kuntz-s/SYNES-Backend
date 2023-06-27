package com.synes.util.gestionTransaction;

import com.synes.util.gestionEvenement.Evenements;
import com.synes.util.gestionUtilisateur.Membre;

public class Transaction {
    private int id;
    private int montant;
    private String type;
    private String raison;
    private Membre membre;
    private Evenements evenements;

    public Transaction() {
    }

    public Transaction(int id, int montant, String type, String raison, Membre membre, Evenements evenements) {
        this.id = id;
        this.montant = montant;
        this.type = type;
        this.raison = raison;
        this.membre = membre;
        this.evenements = evenements;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public Evenements getEvenements() {
        return evenements;
    }

    public void setEvenements(Evenements evenements) {
        this.evenements = evenements;
    }
}
