package com.synes.util.gestionTransaction;

import java.util.Date;

public class SoldeBancaire {
    private int id;
    private Transaction transaction;
    private int solde;
    private Date modifieLe;

    public SoldeBancaire() {
    }

    public SoldeBancaire(int id, Transaction transaction, int solde, Date modifieLe) {
        this.id = id;
        this.transaction = transaction;
        this.solde = solde;
        this.modifieLe = modifieLe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    public Date getModifieLe() {
        return modifieLe;
    }

    public void setModifieLe(Date modifieLe) {
        this.modifieLe = modifieLe;
    }
}
