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

public class NotificationAdapter extends BaseAdapter {
    private final List<Notification> items;
    private final LayoutInflater mInflater;
    private final Clickable callBackActivity;

    public NotificationAdapter(List<Notification> items, NotificationActivity callBackActivity) {
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
                ? mInflater.inflate(R.layout.notification_item, parent, false)
                : convertView);

        //(2) : Récupération des éléments
        TextView name = layoutItem.findViewById(R.id.text_message);

        //(3) : Mise à jour des valeurs
        String message = items.get(position).getEleve() + " a demandé un cours de " + items.get(position).getMatiere() + " avec " + items.get(position).getProfesseur() + " à " + items.get(position).getLieu() + " le " + items.get(position).getDate() + ".";
        name.setText(message);

        //(4) écouteur sur chaque élément de l'adapter
        layoutItem.setOnClickListener( click -> callBackActivity.onClicItem(position));
        //On retourne l'item créé.
        return layoutItem;
    }

}
