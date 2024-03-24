package edu.iut.proftracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Professeur implements Parcelable {

    private String nom;
    private ArrayList<String> matieres;
    private float prix;
    private float note;
    private String image;

    public Professeur() {
        super();
    }

    public Professeur(Parcel in){
        this.nom = in.readString();
        this.matieres = in.readArrayList(getClass().getClassLoader());
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

    public ArrayList getMatieres() {
        return matieres;
    }

    public void setMatieres(ArrayList<String> matiere) {
        this.matieres = matiere;
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
        this.image = "https://rayanoutili.github.io/proftrackerjson/images/" + image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.nom);
        dest.writeList(this.matieres);
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

