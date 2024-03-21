package edu.iut.proftracker.models;

import java.util.ArrayList;
import java.util.List;

public class ProfesseurList  {
    private static List<Professeur> allProfesseurs = new ArrayList<>();
    private static List<Professeur> displayedProfesseurs = new ArrayList<>();
    private static ProfesseurList instance = null;

    private ProfesseurList(){
        /*
        for(String name : OnePieceApp.getContext().getResources().getStringArray(R.array.Professeurs)){
            Professeur mangaProfesseur = new Professeur(name);
            allProfesseurs.add(mangaProfesseur);
            displayedProfesseurs.add(mangaProfesseur);
        }
         */
    }

    public static List<Professeur> getAllProfesseurs(){
        if (instance==null) {
            instance = new ProfesseurList();
        }
        return allProfesseurs;
    }
    public static List<Professeur> getDisplayedProfesseurs(){
        if (instance==null) {
            instance = new ProfesseurList();
        }
        return displayedProfesseurs;
    }


    public static void clearDisplayedProfesseurs(){
        if (instance==null) {
            instance = new ProfesseurList();
        }
        displayedProfesseurs.clear();
    }

    public static void addDisplayedProfesseurs(Professeur Professeur){
        if (instance==null) {
            instance = new ProfesseurList();
        }
        displayedProfesseurs.add(Professeur);
    }

    public static Professeur getDisplayedProfesseur(int index){
        if (instance==null) {
            instance = new ProfesseurList();
        }
        return displayedProfesseurs.get(index);
    }

    public static Professeur getProfesseur(int index){
        if (instance==null) {
            instance = new ProfesseurList();
        }
        return allProfesseurs.get(index);
    }

}

