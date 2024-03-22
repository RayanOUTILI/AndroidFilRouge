package edu.iut.proftracker.models;

import java.util.HashMap;

public abstract class Utilisateur {
    private String nom;
    private String prenom;
    private String mail;
    private ETypeUtilisateur eTypeUtilisateur;
    private String localisation;

    public Utilisateur(String nom, String prenom, String mail, ETypeUtilisateur eTypeUtilisateur, String localisation) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.eTypeUtilisateur = eTypeUtilisateur;
        this.localisation = localisation;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public ETypeUtilisateur geteTypeUtilisateur() {
        return eTypeUtilisateur;
    }

    public void seteTypeUtilisateur(ETypeUtilisateur eTypeUtilisateur) {
        this.eTypeUtilisateur = eTypeUtilisateur;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
}