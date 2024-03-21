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
}