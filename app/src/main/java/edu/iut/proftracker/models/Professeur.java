package edu.iut.proftracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/** Classe Professeur permettant de gérer les professeurs de l'application
 *  Elle implémente l'interface Parcelable pour pouvoir être passée entre les activités
 * 
 * @see android.os.Parcelable
 */ 

public class Professeur implements Parcelable {

    private String nom;
    private ArrayList<String> matieres;
    private float prix;
    private String image;
    private String lieu;
    private String mail;
    private String description;
    private ArrayList<Commentaire> commentaires;

    /** Constructeur par défaut de la classe Professeur
     * 
     */
    public Professeur() {
        super();
    }

    /** Constructeur de la classe Professeur
     * 
     * @param in : Parcel contenant les informations du professeur
     * 
     * @see android.os.Parcel
     */
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

    /** Méthode permettant de récupérer le nom du professeur
     * 
     * @return String : Nom du professeur
     * 
     * @see java.lang.String
     */
    public String getNom() {
        return nom;
    }

    /** Méthode permettant de définir le nom du professeur
     * 
     * @param nom : Nom du professeur
     * 
     * @see java.lang.String
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /** Méthode permettant de récupérer les matières enseignées par le professeur
     * 
     * @return ArrayList<String> : Liste des matières enseignées
     * 
     * @see java.util.ArrayList
     * @see java.lang.String
     */
    public ArrayList<String> getMatieres() {
        return matieres;
    }

    /** Méthode permettant de définir les matières enseignées par le professeur
     * 
     * @param matieres : Liste des matières enseignées
     * 
     * @see java.util.ArrayList
     * @see java.lang.String
     */
    public void setMatieres(ArrayList<String> matieres) {
        this.matieres = matieres;
    }

    /** Méthode permettant de récupérer le prix horaire du professeur
     * 
     * @return String : Prix horaire du professeur
     * 
     * @see java.lang.String
     */
    public String getPrix() {
        return prix + " €/h";
    }

    /** Méthode permettant de récupérer le prix horaire du professeur sous forme de float
     * 
     * @return float : Prix horaire du professeur
     */
    public float getPrixFloat() {
        return prix;
    }

    /** Méthode permettant de définir le prix horaire du professeur
     * 
     * @param prix : Prix horaire du professeur
     */
    public void setPrix(float prix) {
        this.prix = prix;
    }

    /** Méthode permettant de récupérer l'image du professeur
     * 
     * @return String : URL de l'image du professeur
     * 
     * @see java.lang.String
     */
    public String getImage() {
        return image;
    }

    /** Méthode permettant de définir l'image du professeur
     * <p> L'image est stockée sur un serveur distant, on ajoute donc le chemin du serveur à l'image </p>
     * 
     * @param image : URL de l'image du professeur
     * 
     * @see java.lang.String
     */
    public void setImage(String image) {
        this.image = "https://rayanoutili.github.io/proftrackerjson/images/profconnus/" + image;
    }

    /** Méthode permettant de récupérer le lieu où le professeur donne ses cours
     * 
     * @return lieu : Lieu où le professeur donne ses cours
     * 
     * @see java.lang.String
     */
    public String getLieu() {
        return lieu;
    }

    /** Méthode permettant de définir le lieu où le professeur donne ses cours
     * 
     * @param lieu : Lieu où le professeur donne ses cours
     * 
     * @see java.lang.String
     */
    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    /** Méthode permettant de récupérer l'adresse mail du professeur
     * 
     * @return String : Adresse mail du professeur
     * 
     * @see java.lang.String
     */
    public String getMail() {
        return mail;
    }

    /** Méthode permettant de définir l'adresse mail du professeur
     * 
     * @param mail : Adresse mail du professeur
     * 
     * @see java.lang.String
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /** Méthode permettant de récupérer la description du professeur
     * 
     * @return String : Description du professeur
     * 
     * @see java.lang.String
     */
    public String getDescription() {
        return description;
    }

    /** Méthode permettant de définir la description du professeur
     * 
     * @param description : Description du professeur
     * 
     * @see java.lang.String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** Méthode permettant de récupérer les commentaires laissés par les élèves sur le professeur
     * 
     * @return ArrayList<Commentaire> : Liste des commentaires laissés par les élèves
     * 
     * @see java.util.ArrayList
     * @see edu.iut.proftracker.models.Commentaire
     */
    public ArrayList<Commentaire> getCommentaires() {
        return commentaires;
    }

    /** Méthode permettant de définir les commentaires laissés par les élèves sur le professeur
     * 
     * @param commentaires : Liste des commentaires laissés par les élèves
     * 
     * @see java.util.ArrayList
     * @see edu.iut.proftracker.models.Commentaire
     */
    public void setCommentaires(ArrayList<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    /** Méthode permettant de récupérer la note moyenne du professeur en fonction des commentaires laissés par les élèves
     * 
     * @return float : Note moyenne du professeur
     * 
     * @see edu.iut.proftracker.models.Commentaire
     */
    public float getNote() {
        float note = 0;
        for (Commentaire commentaire : commentaires) {
            note += commentaire.getNote();
        }
        return note / commentaires.size();
    }

    /** Retourn un entier décrivant le nombre d'objets spéciaux contenus dans le Parcel, ou 0 si le contenu est vide
     * @return 0
     */

    @Override
    public int describeContents() {
        return 0;
    }

    /** Méthode permettant de définir le contenu du Parcel
     * 
     * @param dest : Parcel contenant les informations du professeur
     * @param flags : flags
     * 
     * @see android.os.Parcel
     */
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

    /** attribut CREATOR permettant de créer une instance de la classe Professeur à partir d'un Parcel
     * 
     * @see android.os.Parcelable.Creator
     * @see android.os.Parcel
     * @see edu.iut.proftracker.models.Professeur
     * @see edu.iut.proftracker.models.Commentaire
     */
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
