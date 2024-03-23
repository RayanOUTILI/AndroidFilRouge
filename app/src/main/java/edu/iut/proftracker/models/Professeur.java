package edu.iut.proftracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Professeur implements Parcelable {

    private String nom;
    private String matiere;
    private float prix;
    private float note;
    private String image;

    public Professeur() {
        super();
    }

    public Professeur(Parcel in){
        this.nom = in.readString();
        this.matiere = in.readString();
        this.prix = in.readFloat();
        this.note = in.readFloat();
        this.image = in.readString();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getPrix() {
        return prix+ " €/h";
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.nom);
        dest.writeString(this.matiere);
        dest.writeFloat(this.prix);
        dest.writeFloat(this.note);
        dest.writeString(this.image);
    }

    public static final Parcelable.Creator<Professeur> CREATOR = new Parcelable.Creator<Professeur>() {
        @Override
        public Professeur createFromParcel(Parcel source) {
            return new Professeur(source);
        }
        @Override
        public Professeur[] newArray(int size)
        {
            return new Professeur[size];
        }
    };
    public static Parcelable.Creator<Professeur> getCreator() {
        return CREATOR;
    }
}

