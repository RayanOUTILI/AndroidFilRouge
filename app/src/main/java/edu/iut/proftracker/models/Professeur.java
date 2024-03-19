package edu.iut.proftracker.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Professeur extends Utilisateur {
    private HashMap<Utilisateur, Avis> avis; // Utilisateur / List{note,avis}


    public Professeur(String nom, String prenom, String mail, ETypeUtilisateur eTypeUtilisateur, String localisation) {
        super(nom, prenom, mail, eTypeUtilisateur, localisation);
        this.avis = new HashMap<Utilisateur, Avis>();
    }

    public HashMap<Utilisateur, Avis> getAvis() {
        return avis;
    }

    public void setAvis(HashMap<Utilisateur, Avis> avis) {
        this.avis = avis;
    }


}
