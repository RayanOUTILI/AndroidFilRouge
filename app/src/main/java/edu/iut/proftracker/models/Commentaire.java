package edu.iut.proftracker.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Commentaire implements Parcelable {
    private String auteur;
    private String contenu;
    private float note;

    public Commentaire(String auteur, String contenu, float note) {
        this.auteur = auteur;
        this.contenu = contenu;
        this.note = note;
    }

    public Commentaire() {
        super();
    }

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

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(auteur);
        dest.writeString(contenu);
        dest.writeFloat(note);
    }
}

