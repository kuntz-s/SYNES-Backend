package com.synes.util.gestionTransaction;

public class SoldeBancaire {
    private int id;
    private Transaction transaction;
    private int solde;
    private Object modifieLe;

    public SoldeBancaire() {
    }

    public SoldeBancaire(int id, Transaction transaction, int solde, Object modifieLe) {
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

    public Object getModifieLe() {
        return modifieLe;
    }

    public void setModifieLe(Object modifieLe) {
        this.modifieLe = modifieLe;
    }
}
