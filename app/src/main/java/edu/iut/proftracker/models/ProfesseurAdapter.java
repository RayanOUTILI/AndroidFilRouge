package edu.iut.proftracker.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import edu.iut.proftracker.R;
import edu.iut.proftracker.controllers.Clickable;
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
        View layoutItem = null;
        /*
        //(1) : Réutilisation des layouts (lorsque c'est possible)
        layoutItem = (convertView == null
                ? mInflater.inflate(R.layout.studient_layout, parent, false)
                : convertView);
        //(2) : Récupération des éléments
        TextView name = layoutItem.findViewById(R.id.name);
        ImageView picture = layoutItem.findViewById(R.id.picture);
        RatingBar ratingBar = layoutItem.findViewById(R.id.rank);
        //(3) : Mise à jour des valeurs
        name.setText(items.get(position).getName());
        picture.setImageResource(items.get(position).getPicture());
        ratingBar.setRating(items.get(position).getRank());
        //(4) écouteur sur chaque élément de l'adapter
        layoutItem.setOnClickListener( click -> callBackActivity.onClicItem(position));
        //On retourne l'item créé.
         */
        return layoutItem;
    }

}
