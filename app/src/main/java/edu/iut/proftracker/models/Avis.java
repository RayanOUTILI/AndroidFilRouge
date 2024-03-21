package edu.iut.proftracker.models;

public class Avis {
    private Utilisateur utilisateur;
    private String avis;
    private Integer note;

    public Avis(Utilisateur utilisateur, String avis, Integer note) {
        this.utilisateur = utilisateur;
        this.avis = avis;
        this.note = note;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }
}
