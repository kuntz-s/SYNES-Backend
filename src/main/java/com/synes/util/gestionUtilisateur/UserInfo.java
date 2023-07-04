package com.synes.util.gestionUtilisateur;

import com.synes.util.gestionEvenement.Evenements;
import com.synes.util.gestionTransaction.Transaction;

import java.util.List;

public class UserInfo {
    private Membre membre;
    private List<Transaction> transactionList;
    private List<Evenements> evenementsList;
    private List<Avertissement> avertissementList;

    public UserInfo() {
    }

    public UserInfo(Membre membre, List<Transaction> transactionList, List<Evenements> evenementsList, List<Avertissement> avertissementList) {
        this.membre = membre;
        this.transactionList = transactionList;
        this.evenementsList = evenementsList;
        this.avertissementList = avertissementList;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public List<Evenements> getEvenementsList() {
        return evenementsList;
    }

    public void setEvenementsList(List<Evenements> evenementsList) {
        this.evenementsList = evenementsList;
    }

    public List<Avertissement> getAvertissementList() {
        return avertissementList;
    }

    public void setAvertissementList(List<Avertissement> avertissementList) {
        this.avertissementList = avertissementList;
    }
}
