package edu.iut.proftracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Professeur implements Parcelable {

    private String nom;
    private ArrayList<String> matieres;
    private float prix;
    private String image;
    private String lieu;
    private String mail;
    private String description;
    private ArrayList<Commentaire> commentaires;

    public Professeur() {
        super();
    }

    protected Professeur(Parcel in) {
        this.nom = in.readString();
        this.matieres = in.createStringArrayList();
        this.prix = in.readFloat();
        this.image = in.readString();
        this.lieu = in.readString();
        this.mail = in.readString();
        this.description = in.readString();
        this.commentaires = in.createTypedArrayList(Commentaire.CREATOR);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<String> getMatieres() {
        return matieres;
    }

    public void setMatieres(ArrayList<String> matieres) {
        this.matieres = matieres;
    }

    public String getPrix() {
        return prix + " €/h";
    }

    public float getPrixFloat() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = "https://rayanoutili.github.io/proftrackerjson/images/" + image;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(ArrayList<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    // on calcule la note moyenne du professeur
    public float getNote() {
        float note = 0;
        for (Commentaire commentaire : commentaires) {
            note += commentaire.getNote();
            System.out.println(note);
        }
        return note / commentaires.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nom);
        dest.writeStringList(this.matieres);
        dest.writeFloat(this.prix);
        dest.writeString(this.image);
        dest.writeString(this.lieu);
        dest.writeString(this.mail);
        dest.writeString(this.description);
        dest.writeTypedList(this.commentaires);
    }

    public static final Parcelable.Creator<Professeur> CREATOR = new Parcelable.Creator<Professeur>() {
        @Override
        public Professeur createFromParcel(Parcel source) {
            return new Professeur(source);
        }

        @Override
        public Professeur[] newArray(int size) {
            return new Professeur[size];
        }
    };

    public static Parcelable.Creator<Professeur> getCreator() {
        return CREATOR;
    }
}
