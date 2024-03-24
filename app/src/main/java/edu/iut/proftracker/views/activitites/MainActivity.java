package edu.iut.proftracker.views.activitites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.iut.proftracker.R;

import java.util.ArrayList;
import java.util.List;

import edu.iut.proftracker.controllers.Clickable;
import edu.iut.proftracker.controllers.HttpAsyncGet;
import edu.iut.proftracker.controllers.PostExecuteActivity;
import edu.iut.proftracker.databinding.ActivityMainBinding;
import edu.iut.proftracker.models.Notification;
import edu.iut.proftracker.models.Professeur;
import edu.iut.proftracker.models.ProfesseurAdapter;

public class MainActivity extends AppCompatActivity implements PostExecuteActivity<Professeur>, Clickable {
    private final String TAG = "AAAAAAAAAAAAAAAAAAAAAA";
    private static final List<Professeur> professeurList = new ArrayList<>(); //the complete list
    private final List<Professeur> displayedprofesseur = new ArrayList<>(); //the displayed list
    private ProfesseurAdapter adapter;
    private Intent professorIntent, loginIntent;
    private FirebaseUser firebaseUser;
    ActivityMainBinding binding;

    private Button buttonFrancais;

    private Button buttonMathematiques;

    private Button buttonHistoire;

    private Button buttonInformatique;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser == null) {
            this.loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginIntent);
        }

        TextView textView = findViewById(R.id.title);
        Button fr = findViewById(R.id.buttonFrancais);


        String url = "https://rayanoutili.github.io/proftrackerjson/data.json";
        new HttpAsyncGet<>(url, Professeur.class, this, new ProgressDialog(MainActivity.this) );

        com.google.android.material.bottomnavigation.BottomNavigationView menu = findViewById(R.id.menu);
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                final int idhome = findViewById(R.id.homePage).getId();
                final int idnotification = R.id.notificationPage;
                final int idProfile = R.id.profilePage;
                if(idhome == id){
                }else if(idnotification == id){
                    Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                    intent.putExtra("key",1);
                    startActivity(intent);
                }else if(id == idProfile){
                }else{
                    System.out.println("DKNOW");
                }
                return true;
            }
        });

        Log.d(TAG, "onCreate: " + professeurList.size());
        Notification.getNotification();
        Notification.getNotification("Griffonnet");
        Log.d(TAG, "onCreate: " + professeurList.size());

        /*
        Filtrer les professeurs par matières
         */
        buttonFrancais = findViewById(R.id.buttonFrancais);
        buttonMathematiques = findViewById(R.id.buttonMathematiques);
        buttonHistoire = findViewById(R.id.buttonHistoire);
        buttonInformatique = findViewById(R.id.buttonInformatique);

        buttonFrancais.setOnClickListener(view -> {
            filterProfesseursByMatiere("Français");
            resetButtonsColors();
            buttonFrancais.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        });
        buttonMathematiques.setOnClickListener(view -> {
            filterProfesseursByMatiere("Mathématiques");
            resetButtonsColors();
            buttonMathematiques.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        });
        buttonHistoire.setOnClickListener(view -> {
            filterProfesseursByMatiere("Histoire");
            resetButtonsColors();
            buttonHistoire.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        });
        buttonInformatique.setOnClickListener(view -> {
            filterProfesseursByMatiere("Informatique");
            resetButtonsColors();
            buttonInformatique.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        });

    }

    public void resetButtonsColors() {
        buttonFrancais.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
        buttonMathematiques.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
        buttonHistoire.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
        buttonInformatique.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
    }

    @Override
    public void onPostExecute(List<Professeur> itemList) {
        professeurList.addAll(itemList);
        displayedprofesseur.addAll(itemList);
        adapter = new ProfesseurAdapter(displayedprofesseur, this);
        ListView listview = findViewById(R.id.listeViewRecherche);
        listview.setAdapter(adapter);
        Log.d(TAG,"itemList = " + itemList);
    }

    // Filtrer les professeurs par matières
    private void filterProfesseursByMatiere(String matiere) {
        List<Professeur> filteredProfesseurs = new ArrayList<>();
        for (Professeur professeur : professeurList) {
            if (professeur.getMatieres().contains(matiere)) {
                filteredProfesseurs.add(professeur);
            }
        }
        displayedprofesseur.clear();
        displayedprofesseur.addAll(filteredProfesseurs);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onClicItem(int Index) {
        int itemIndex = findIndexInList(Index);
        Log.d(TAG, String.valueOf(itemIndex));
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra(getString(R.string.key), professeurList.get(itemIndex));
        startActivity(intent);
    }

    private int findIndexInList(int index) {
        Professeur characterToFind = professeurList.get(index);
        for (int i = 0; i < professeurList.size(); i++) {
            if (professeurList.get(i).equals(characterToFind)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}