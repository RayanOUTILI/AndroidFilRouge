package edu.iut.proftracker.views.activitites;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import edu.iut.proftracker.R;
import edu.iut.proftracker.models.Professeur;

public class ProfesseurProfilActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_professor_profile);

        Professeur professeurConnecte = getIntent().getExtras().getParcelable(getString(R.string.key));

        if (professeurConnecte == null) {
            ImageView imageView = findViewById(R.id.pppicture);
            imageView.setImageResource(R.drawable.student);

            TextView textView = findViewById(R.id.nomProfile);
            textView.setText("Vous êtes connecté en tant que : " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

            TextView localisation = findViewById(R.id.localisation);
            localisation.setText("");

        }
        else{
            ImageView imageView = findViewById(R.id.pppicture);

            TextView textView = findViewById(R.id.nomProfile);
            textView.setText(professeurConnecte.getNom());

            String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            Picasso.get().load(professeurConnecte.getImage()).into(imageView);

            String nomEleve = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        }
    }
}
