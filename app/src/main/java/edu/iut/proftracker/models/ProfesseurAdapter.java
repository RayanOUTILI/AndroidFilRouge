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
import edu.iut.proftracker.models.Professeur;
import edu.iut.proftracker.views.activitites.MainActivity;

public class ProfesseurAdapter extends BaseAdapter {
    private final List<Professeur> items;
    private final LayoutInflater mInflater;
    private final Clickable callBackActivity;
    public ProfesseurAdapter(List<Professeur> items, MainActivity callBackActivity) {
        this.items = items;
        this.callBackActivity = callBackActivity;
        mInflater = LayoutInflater.from(callBackActivity.getContext());
    }
    public int getCount() {
        return items.size();
    }
    public Object getItem(int position) {
        return items.get(position);
    }
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layoutItem;

        //(1) : Réutilisation des layouts (lorsque c'est possible)
        layoutItem = (convertView == null
                ? mInflater.inflate(R.layout.professor_layout, parent, false)
                : convertView);

        //(2) : Récupération des éléments
        TextView name = layoutItem.findViewById(R.id.profName);
        //ImageView picture = layoutItem.findViewById(R.id.picture);
        RatingBar ratingBar = layoutItem.findViewById(R.id.note);
        TextView prix = layoutItem.findViewById(R.id.textPrice);
        TextView matiere = layoutItem.findViewById(R.id.textSubjects);

        //(3) : Mise à jour des valeurs
        name.setText(items.get(position).getNom());
        //picture.setImageResource(items.get(position).getPicture());
        ratingBar.setRating(items.get(position).getNote());
        prix.setText(items.get(position).getPrix());
        matiere.setText(items.get(position).getMatiere());

        //(4) écouteur sur chaque élément de l'adapter
        layoutItem.setOnClickListener( click -> callBackActivity.onClicItem(position));
        //On retourne l'item créé.
        return layoutItem;
    }

}
