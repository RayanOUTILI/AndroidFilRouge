package edu.iut.proftracker.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import edu.iut.proftracker.R;
import edu.iut.proftracker.controllers.Clickable;
import edu.iut.proftracker.views.activitites.MainActivity;
import edu.iut.proftracker.views.activitites.NotificationActivity;

/** Classe NotificationAdapter permettant de gérer l'adapter des notifications représentant les rendez-vous entre un professeur et un élève dans un ListView
 * 
 * @see android.widget.BaseAdapter
 */
public class NotificationAdapter extends BaseAdapter {


    private final List<Notification> items;
    private final LayoutInflater mInflater;
    private final NotificationActivity callBackActivity;
    

    /** Constructeur de la classe NotificationAdapter
     * 
     * @param items : Liste des notifications
     * @param callBackActivity : Activité appelante
     * 
     * @see edu.iut.proftracker.views.activitites.NotificationActivity
     * @see edu.iut.proftracker.models.Notification
     * @see android.view.LayoutInflater
     * @see java.util.List
     */
    public NotificationAdapter(List<Notification> items, NotificationActivity callBackActivity) {
        this.items = items;
        this.callBackActivity = callBackActivity;
        mInflater = LayoutInflater.from(callBackActivity.getContext());
    }

    /** Méthode permettant de récupérer le nombre d'éléments de l'adapter
     * 
     * @return int : Nombre d'éléments
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

    /** Méthode permettant de créer un élément de la listeView
     * @param position : position de l'élément dans la liste
     * @param convertView : vue de l'élément
     * @param parent : vue parente
     * @return View : élément de la liste
     * 
     * @see android.widget.BaseAdapter#getView(int, android.view.View, android.view.ViewGroup)
     * @see android.view.View
     * @see android.view.ViewGroup
     * @see android.widget.TextView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layoutItem;

        //(1) : Réutilisation des layouts (lorsque c'est possible)
        layoutItem = (convertView == null
                ? mInflater.inflate(R.layout.notification_item, parent, false)
                : convertView);

        //(2) : Récupération des éléments
        TextView name = layoutItem.findViewById(R.id.text_message);

        //(3) : Mise à jour des valeurs
        String message = items.get(position).getEleve() + " a demandé un cours de " + items.get(position).getMatiere() + " avec " + items.get(position).getProfesseur() + " à " + items.get(position).getLieu() + " le " + items.get(position).getDate() + ".";
        name.setText(message);

        // (4) On retourne l'item créé.
        return layoutItem;
    }

}
