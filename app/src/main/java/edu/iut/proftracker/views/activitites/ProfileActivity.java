package edu.iut.proftracker.views.activitites;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.iut.proftracker.R;
import edu.iut.proftracker.models.CommentaireAdapter;
import edu.iut.proftracker.models.Notification;
import edu.iut.proftracker.models.Professeur;

/** Classe ProfileActivity permettant de gérer l'activité de profil de l'application ProfTracker
 *  
 * @see androidx.appcompat.app.AppCompatActivity
 */
public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser firebaseUtilisateur;

    private CommentaireAdapter adapter;

    /** Méthode onCreate permettant de créer l'activité de profil de l'application ProfTracker
     * 
     * <p> On initialise les éléments de l'activité tels que les champs de texte, les boutons et les images </p>
     * <p> On récupère les informations du professeur et on les affiche dans l'activité </p>
     * <p> On affiche les commentaires du professeur dans l'activité </p>
     * 
     * @param savedInstanceState : Instance de l'activité
     * 
     * @see android.os.Bundle
     * @see com.google.firebase.auth.FirebaseAuth
     * @see com.google.firebase.auth.FirebaseUser
     * @see com.squareup.picasso.Picasso
     * @see java.text.SimpleDateFormat
     * @see java.util.ArrayList
     * @see java.util.Date
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Appel du constructeur de la super classe
        super.onCreate(savedInstanceState);

        // Association de l'activité à son layout et initialisation de l'activité
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        // Recupération du professeur depuis l'intent
        Professeur professeur = getIntent().getExtras().getParcelable(getString(R.string.key));
        boolean estLui = getIntent().getExtras().getBoolean("estLui");

        // Initialisation des éléments de l'activité
        if (professeur == null) {
            // Si le professeur est null, on affiche les informations de l'élève
            ImageView imageView = findViewById(R.id.profilepicture);
            imageView.setImageResource(R.drawable.student);

            TextView nom = findViewById(R.id.nomProfile);
            nom.setText(
                    "Elève connecté en tant que : " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

            nom.setTextSize(20);

            // On cache les informations du professeur puisque l'utilisateur est un élève
            TextView localisation = findViewById(R.id.localisation);
            localisation.setText("");

            TextView description = findViewById(R.id.textView4);
            description.setText("");

            Button boutonContacter = findViewById(R.id.contacter);
            boutonContacter.setEnabled(false);

            ImageView image = findViewById(R.id.imageView);
            image.setVisibility(ImageView.GONE);

            //ImageView image2 = findViewById(R.id.livree);
            //image2.setVisibility(ImageView.GONE);

            TextView note = findViewById(R.id.noteetoile);
            note.setVisibility(TextView.GONE);

            ImageView logo = findViewById(R.id.logoposition);
            logo.setVisibility(ImageView.GONE);


        } else {

            // Si le professeur n'est pas null, on affiche ses informations
            ImageView imageView = findViewById(R.id.profilepicture);
            TextView textView = findViewById(R.id.nomProfile);
            textView.setText(professeur.getNom());

            // Récupérer le nom de l'utilisateur connecté
            String nom = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

            // Si le nom de l'utilisateur connecté est le même que le nom du professeur, on affiche les informations de l'utilisateur connecté
            Picasso.get().load(professeur.getImage()).into(imageView);

            
            TextView premierMatiere = findViewById(R.id.permiereMatiere);
            TextView deuxiemeMatiere = findViewById(R.id.deuxiemeMatiere);
            TextView troisiemeMatiere = findViewById(R.id.troisiemeMatiere);

            TextView description = findViewById(R.id.textView4);
            description.setText(professeur.getDescription());

            TextView localisation = findViewById(R.id.localisation);
            localisation.setText(professeur.getLieu());

            ArrayList<String> matieres = professeur.getMatieres();
            premierMatiere.setText(matieres.get(0));
            deuxiemeMatiere.setText(matieres.get(1));
            troisiemeMatiere.setText(matieres.get(2));

            Button boutonContacter = findViewById(R.id.contacter);
            String nomEleve = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateFormatte = formatter.format(date);

            boutonContacter.setOnClickListener(v -> {
                Notification.createNotification(professeur.getNom(), nomEleve, professeur.getLieu(), dateFormatte,
                        "Mathématiques");
            });

            if (estLui) {
                boutonContacter.setEnabled(false);
            }

            // on créé l'adapter pour les commentaires
            adapter = new CommentaireAdapter(professeur.getCommentaires(), this);
            ListView listView = findViewById(R.id.listeViewCommentaire);
            listView.setAdapter(adapter);
        }
    }

    /** Méthode getContext permettant de récupérer le contexte de l'activité ProfileActivity
     * 
     * @return this : le contexte de l'activité ProfileActivity
     */
    public Context getContext() {
        return this;
    }
}