package edu.iut.proftracker.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.iut.proftracker.R;
import edu.iut.proftracker.views.activitites.ProfileActivity;

/** Classe CommentaireAdapter permettant de gérer l'affichage des commentaires dans un ListView
 * @see android.widget.BaseAdapter
 */

public class CommentaireAdapter extends BaseAdapter {

    /** Liste des commentaires 
     *  @see Commentaire
     *  @see java.util.List
     */

    private final List<Commentaire> items;

    /** Layout inflater pour instancier les éléments de la vue 
     *  @see android.view.LayoutInflater
     */

    private final LayoutInflater mInflater;

    /** Activité appelante 
     *  @see edu.iut.proftracker.views.activitites.ProfileActivity
     */
    private final ProfileActivity callBackActivity;

    /** Constructeur de la classe CommentaireAdapter
     * @param items : Liste des commentaires
     * @param callBackActivity : Activité appelante
     */

    public CommentaireAdapter(List<Commentaire> items, ProfileActivity callBackActivity) {
        this.items = items;
        this.callBackActivity = callBackActivity;
        mInflater = LayoutInflater.from(callBackActivity.getContext());
    }

    /** Méthode permettant de récupérer le nombre d'éléments de la liste
     *  @return int : nombre d'éléments de la liste
     */
    public int getCount() {
        return items.size();
    }

    /** Méthode permettant de récupérer un élément de la liste
     * @param position : position de l'élément dans la liste
     * @return Object : élément de la liste
     */
    public Object getItem(int position) {
        return items.get(position);
    }

    /** Méthode permettant de récupérer l'identifiant d'un élément de la liste
     * @param position : position de l'élément dans la liste
     * @return long : identifiant de l'élément
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
                ? mInflater.inflate(R.layout.commentaire_item, parent, false)
                : convertView);

        //(2) : Récupération des éléments
        TextView name = layoutItem.findViewById(R.id.text_message);
        TextView note = callBackActivity.findViewById(R.id.noteetoile);

        //(3) : Mise à jour des valeurs
        String message = "Note : " + items.get(position).getNote() + "\n" + items.get(position).getAuteur() + " a commenté : " + items.get(position).getContenu();
        float noteMsg = items.get(position).getNote();
        note.setText("Note moyenne : " + noteMsg + " ⭐");
        name.setText(message);

        // (4) On retourne l'item créé.
        return layoutItem;
    }
}
