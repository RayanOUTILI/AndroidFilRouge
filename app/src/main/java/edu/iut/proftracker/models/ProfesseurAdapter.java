package edu.iut.proftracker.models;

import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.iut.proftracker.R;
import edu.iut.proftracker.controllers.Clickable;
import edu.iut.proftracker.models.Professeur;
import edu.iut.proftracker.views.activitites.MainActivity;

/** Classe ProfesseurAdapter permettant de gérer les professeurs de l'application dans un ListView
 * 
 * @see android.widget.BaseAdapter
 */
public class ProfesseurAdapter extends BaseAdapter {

    private final List<Professeur> items;
    private final LayoutInflater mInflater;
    private final Clickable callBackActivity;

    /** Constructeur de la classe ProfesseurAdapter
     * 
     * @param items : Liste des professeurs à afficher
     * @param callBackActivity : Activité appelante
     * 
     * @see edu.iut.proftracker.views.activitites.MainActivity
     * @see edu.iut.proftracker.models.Professeur
     * @see android.view.LayoutInflater
     * @see java.util.List
     */
    public ProfesseurAdapter(List<Professeur> items, MainActivity callBackActivity) {
        this.items = items;
        this.callBackActivity = callBackActivity;
        mInflater = LayoutInflater.from(callBackActivity.getContext());
    }

    /** Méthode permettant de récupérer le nombre d'éléments de l'adapter
     * 
     * @return int : Nombre d'éléments
     * 
     * @see java.lang.Integer
     */
    public int getCount() {
        return items.size();
    }

    /** Méthode permettant de récupérer un élément de l'adapter
     * 
     * @param position : Position de l'élément
     * @return Object : Elément
     * 
     * @see java.lang.Object
     */
    public Object getItem(int position) {
        return items.get(position);
    }

    /** Méthode permettant de récupérer l'identifiant d'un élément de l'adapter
     * 
     * @param position : Position de l'élément
     * @return long : Identifiant de l'élément
     */
    public long getItemId(int position) {
        return position;
    }

    /** Méthode permettant de récupérer la vue d'un élément de l'adapter
     * 
     * @param position : Position de l'élément
     * @param convertView : Vue de l'élément
     * @param parent : Vue parente
     * @return View : Vue de l'élément
     * 
     * @see android.view.View
     * @see android.view.ViewGroup
     * @see android.view.LayoutInflater
     * @see android.widget.BaseAdapter#getView(int, android.view.View, android.view.ViewGroup)
     * @see android.animation.ObjectAnimator
     * @see android.widget.TextView
     * @see android.widget.ImageView
     * @see android.widget.RatingBar
     * @see com.squareup.picasso.Picasso
     * @see java.util.ArrayList
     * @see java.util.List
     * @see java.lang.String
     * @see java.lang.StringBuilder
     * @see edu.iut.proftracker.models.Professeur
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layoutItem;

        //(1) : Réutilisation des layouts (lorsque c'est possible)
        layoutItem = (convertView == null
                ? mInflater.inflate(R.layout.professor_layout, parent, false)
                : convertView);

        //(2) : Récupération des éléments
        TextView name = layoutItem.findViewById(R.id.profName);
        ImageView picture = layoutItem.findViewById(R.id.imageProfile);
        RatingBar ratingBar = layoutItem.findViewById(R.id.note);
        TextView prix = layoutItem.findViewById(R.id.textPrice);
        TextView matiere = layoutItem.findViewById(R.id.textSubjects);

        //(3) : Mise à jour des valeurs
        name.setText(items.get(position).getNom());

        Picasso.get().load(items.get(position).getImage()).into(picture);
        ratingBar.setRating(items.get(position).getNote());
        prix.setText(items.get(position).getPrix());

        ObjectAnimator ratingBarAnimator = ObjectAnimator.ofFloat(ratingBar, "rating", 0, items.get(position).getNote());
        ratingBarAnimator.setDuration(750);
        ratingBarAnimator.start();

        ArrayList<String> listMatieres = items.get(position).getMatieres();
        StringBuilder matieresString = new StringBuilder();
        listMatieres.forEach(item -> {
            matieresString.append(item).append(" ");
        });
        matiere.setText(matieresString.toString());

        //(4) écouteur sur chaque élément de l'adapter
        layoutItem.setOnClickListener( click -> callBackActivity.onClicItem(position));
        //On retourne l'item créé.
        return layoutItem;
    }

}
