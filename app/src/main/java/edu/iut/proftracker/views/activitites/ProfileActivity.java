package edu.iut.proftracker.views.activitites;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;

    private CommentaireAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        Professeur professeur = getIntent().getExtras().getParcelable(getString(R.string.key));
        boolean estLui = getIntent().getExtras().getBoolean("estLui");

        if (professeur == null) {
            ImageView imageView = findViewById(R.id.profilepicture);
            imageView.setImageResource(R.drawable.student);

            TextView nom = findViewById(R.id.nomProfile);
            nom.setText(
                    "Elève connecté en tant que : " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

            nom.setTextSize(20);

            TextView localisation = findViewById(R.id.localisation);
            localisation.setText("");

            TextView description = findViewById(R.id.textView4);
            description.setText("");

            Button buttonContacter = findViewById(R.id.contacter);
            buttonContacter.setEnabled(false);

            ImageView image = findViewById(R.id.imageView);
            image.setVisibility(ImageView.GONE);

            //ImageView image2 = findViewById(R.id.livree);
            //image2.setVisibility(ImageView.GONE);

            TextView note = findViewById(R.id.noteetoile);
            note.setVisibility(TextView.GONE);

            ImageView logo = findViewById(R.id.logoposition);
            logo.setVisibility(ImageView.GONE);


        } else {

            ImageView imageView = findViewById(R.id.profilepicture);
            TextView textView = findViewById(R.id.nomProfile);
            textView.setText(professeur.getNom());

            String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
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

            Button buttonContacter = findViewById(R.id.contacter);
            String nomEleve = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateFormatted = formatter.format(date);

            buttonContacter.setOnClickListener(v -> {
                Notification.createNotification(professeur.getNom(), nomEleve, professeur.getLieu(), dateFormatted,
                        "Mathématiques");
            });

            if (estLui) {
                buttonContacter.setEnabled(false);
            }

            // on créé l'adapter pour les commentaires
            adapter = new CommentaireAdapter(professeur.getCommentaires(), this);
            ListView listView = findViewById(R.id.listeViewCommentaire);
            listView.setAdapter(adapter);
        }
    }

    public Context getContext() {
        return this;
    }
}