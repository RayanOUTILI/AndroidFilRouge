package edu.iut.proftracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 *  Classe Commentaire permettant de créer un commentaire pour un professeur
 *  Un commentaire est composé d'un auteur, d'un contenu et d'une note
 *  La note est une valeur flottante comprise entre 0 et 5
 *  La classe implémente l'interface Parcelable pour pouvoir passer des objets de type Commentaire entre les activités
 *  @see android.os.Parcelable
 */



public class Commentaire implements Parcelable {
    private String auteur;
    private String contenu;
    private float note;

    /**
     * Constructeur de la classe Commentaire
     * 
     * @param auteur : auteur du commentaire
     * @param contenu : contenu du commentaire
     * @param note : note attribuée au professeur
     */

    public Commentaire(String auteur, String contenu, float note) {
        this.auteur = auteur;
        this.contenu = contenu;
        this.note = note;
    }

    /**
     * Constructeur par défaut de la classe Commentaire
     */

    public Commentaire() {
        super();
    }

    /**
     * Constructeur de la classe Commentaire
     * @param in : Parcel contenant les informations du commentaire
     */

    protected Commentaire(Parcel in) {
        auteur = in.readString();
        contenu = in.readString();
        note = in.readFloat();
    }
    

    public static final Creator<Commentaire> CREATOR = new Creator<Commentaire>() {
        @Override
        public Commentaire createFromParcel(Parcel in) {
            return new Commentaire(in);
        }

        @Override
        public Commentaire[] newArray(int size) {
            return new Commentaire[size];
        }
    };

    /** Retourne l'auteur du commentaire 
     * @return auteur : auteur du commentaire
     */

    public String getAuteur() {
        return auteur;
    }

    /** Modifie l'auteur du commentaire 
     * @param auteur : auteur du commentaire
     */

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    /** Retourne le contenu du commentaire 
     * @return contenu : contenu du commentaire
     */

    public String getContenu() {
        return contenu;
    }

    /** Modifie le contenu du commentaire 
     * @param contenu : contenu du commentaire
     */

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    /** Retourne la note attribuée au professeur 
     * @return note : note attribuée au professeur
     */

    public float getNote() {
        return note;
    }

    /** Calcule et retourne la note moyenne du professeur en fonction des notes attribuées dans chaque commentaire
     * @param commentaires
     * @return note : note moyenne du professeur
    */

    public float getNoteMoyenne(List<Commentaire> commentaires) {
        float note = 0;
        for (Commentaire commentaire : commentaires){
            note += commentaire.getNote();
        }
        return note / commentaires.size();
    }

    /** Modifie la note attribuée au professeur 
     * @param note : note attribuée au professeur
     */

    public void setNote(float note) {
        this.note = note;
    }

    /** Retourn un entier décrivant le nombre d'objets spéciaux contenus dans le Parcel, ou 0 si le contenu est vide
     * @return 0
     */

    @Override
    public int describeContents() {
        return 0;
    }

    /** Ecrit les informations du commentaire dans un Parcel 
     * @param dest : Parcel contenant les informations du commentaire
     * @param flags : flags
     */

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(auteur);
        dest.writeString(contenu);
        dest.writeFloat(note);
    }

    /** Retourne une chaîne de caractères décrivant le commentaire 
     *  @return String : chaîne de caractères décrivant le commentaire
     */

    @Override
    public String toString() {
        return "Commentaire{" +
                "auteur='" + auteur + '\'' +
                ", contenu='" + contenu + '\'' +
                ", note=" + note +
                '}';
    }
}
