package com.exemple.compte;

public class CompteBancaire {

    //attributs
    private String nom;
    private int solde;

    //Constructeurs
    public CompteBancaire(){}

    public CompteBancaire(String nom, int solde)
    {
        this.nom = nom;
        this.solde = solde;
    }

    //Getters et Setters

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getSolde() {
        return this.solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    //Méthodes
    public void retrait(int montant) throws Exception {
        //Test si le montant est supérieur au solde
        if (montant > this.solde) {
            throw new Exception("Retrait impossible solde insuffisant");
        }
        if (montant <= 0) {
            throw new Exception("Montant interdit négatif ou zéro");
        }
        this.solde -= montant;
    }

    public void ajouter(int montant) throws Exception {
        if (montant <= 0) {
            throw new Exception("Montant interdit négatif ou zéro");
        }
        this.solde += montant;
    }

    public void virement(int montant, CompteBancaire compteBancaire) throws Exception
    {
        this.retrait(montant);
        compteBancaire.ajouter(montant);
    }

    public String toString()
    {
        return "Nom : " + this.nom + ", solde = " + this.solde + " €";
    }
}
