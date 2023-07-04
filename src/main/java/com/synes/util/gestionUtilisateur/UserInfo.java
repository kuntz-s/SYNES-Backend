package com.synes.util.gestionUtilisateur;

import com.synes.util.gestionAnnonce.Annonce;
import com.synes.util.gestionEvenement.Evenements;
import com.synes.util.gestionTransaction.Transaction;

import java.util.List;

public class UserInfo {
    private Membre membre;
    private List<Transaction> transactionList;
    private List<Evenements> evenementsList;
    private List<Avertissement> avertissementList;
    private List<Annonce> annonceList;

    public UserInfo() {
    }

    public UserInfo(Membre membre, List<Transaction> transactionList, List<Evenements> evenementsList, List<Avertissement> avertissementList, List<Annonce> annonceList) {
        this.membre = membre;
        this.transactionList = transactionList;
        this.evenementsList = evenementsList;
        this.avertissementList = avertissementList;
        this.annonceList = annonceList;
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

    public List<Annonce> getAnnonceList() {
        return annonceList;
    }

    public void setAnnonceList(List<Annonce> annonceList) {
        this.annonceList = annonceList;
    }
}
