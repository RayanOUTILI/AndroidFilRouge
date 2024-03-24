package edu.iut.proftracker.views.activitites;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.iut.proftracker.R;
import edu.iut.proftracker.models.Notification;
import edu.iut.proftracker.models.Professeur;


public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;

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

        //String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        //System.out.println(name);

        Button buttonContacter = findViewById(R.id.contacter);
        buttonContacter.setOnClickListener(v -> {
            Notification.createNotification(professeur.getNom(), "rayan", "Cagnes-sur-mer", "24/03/2024", "Mathématiques");
        });
    }
}