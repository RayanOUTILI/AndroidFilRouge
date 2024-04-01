package edu.iut.proftracker.views.activitites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import edu.iut.proftracker.R;
import edu.iut.proftracker.controllers.Clickable;
import edu.iut.proftracker.controllers.PostExecuteActivity;
import edu.iut.proftracker.models.Notification;
import edu.iut.proftracker.models.NotificationAdapter;

/** Classe NotificationActivity permettant de gérer l'activité de notification de l'application ProfTracker
 * 
 * @see androidx.appcompat.app.AppCompatActivity
 */
public class NotificationActivity extends AppCompatActivity {
    private List<Notification> displayedNotifications = new ArrayList<>();
    private FirebaseUser firebaseUser;

    private NotificationAdapter adapter;

    /** Méthode onCreate permettant de créer l'activité de notification de l'application ProfTracker
     * 
     * <p> On initialise les éléments de l'activité tels que les champs de texte, les boutons et les images </p>
     * <p> On récupère les notifications de l'utilisateur connecté et on les affiche dans l'activité </p>
     * 
     * @param savedInstanceState : Instance de l'activité
     * 
     * @see android.os.Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Appel du constructeur de la super classe
        super.onCreate(savedInstanceState);

        // Association de l'activité à son layout et initialisation de l'activité
        setContentView(R.layout.activity_notification);

        // Récupération du nom de l'utilisateur connecté
        String nomProfile = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        // Récupération des notifications de l'utilisateur connecté
        this.displayedNotifications = Notification.getNotification(nomProfile, this);

        ImageView croix = findViewById(R.id.back);

        // Action lors du clic sur la croix, si l'utilisateur clique sur la croix, il est redirigé vers l'activité principale
        croix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
                intent.putExtra("key",1);
                startActivity(intent);
            }
        });
    }

    /** Méthode onPostExecute permettant de mettre à jour l'activité de notification de l'application ProfTracker
     * 
     * <p> On met à jour l'activité en affichant les notifications de l'utilisateur connecté </p>
     * 
     * @param itemList : Liste des notifications de l'utilisateur connecté
     * 
     * @see java.util.List
     * @see edu.iut.proftracker.models.Notification
     * @see edu.iut.proftracker.models.NotificationAdapter
     * @see android.widget.ListView
     * @see android.content.Context
     * @see edu.iut.proftracker.controllers.PostExecuteActivity
     */
    public void onPostExecute(List<Notification> itemList) {
        adapter = new NotificationAdapter(displayedNotifications, this);
        ListView listview = findViewById(R.id.listeViewNotification);
        listview.setAdapter(adapter);
    }

   
    /** Méthode pour récupérer le contexte de l'activité
     * 
     * @return this : Contexte de l'activité
     * 
     * @see android.content.Context
     */
    public Context getContext() {
        return this;
    }
}