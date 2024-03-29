package edu.iut.proftracker.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.iut.proftracker.R;
import edu.iut.proftracker.views.activitites.ProfileActivity;

public class CommentaireAdapter extends BaseAdapter {
    private final List<Commentaire> items;
    private final LayoutInflater mInflater;
    private final ProfileActivity callBackActivity;

    public CommentaireAdapter(List<Commentaire> items, ProfileActivity callBackActivity) {
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
                ? mInflater.inflate(R.layout.commentaire_item, parent, false)
                : convertView);

        //(2) : Récupération des éléments
        TextView name = layoutItem.findViewById(R.id.text_message);

        //(3) : Mise à jour des valeurs
        String message = "Note : " + items.get(position).getNote() + "\n" + items.get(position).getAuteur() + " a commenté : " + items.get(position).getContenu();
        name.setText(message);

        //(4) écouteur sur chaque élément de l'adapter
        //layoutItem.setOnClickListener( click -> callBackActivity.onClicItem(position));
        //On retourne l'item créé.
        return layoutItem;
    }
}
