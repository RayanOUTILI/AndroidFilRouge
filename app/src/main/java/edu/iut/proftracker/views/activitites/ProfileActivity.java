package edu.iut.proftracker.views.activitites;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.iut.proftracker.R;
import edu.iut.proftracker.models.Professeur;


public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);


        Professeur professeur = getIntent().getExtras().getParcelable(getString(R.string.key));
        assert professeur != null;
        ImageView imageView = findViewById(R.id.profilepicture);
        // TODO image
        TextView textView = findViewById(R.id.nomProfile);
        textView.setText(professeur.getNom());
    }
}