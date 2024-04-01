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
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.iut.proftracker.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.iut.proftracker.controllers.Clickable;
import edu.iut.proftracker.controllers.HttpAsyncGet;
import edu.iut.proftracker.controllers.PostExecuteActivity;
import edu.iut.proftracker.databinding.ActivityMainBinding;
import edu.iut.proftracker.models.Professeur;
import edu.iut.proftracker.models.ProfesseurAdapter;
import edu.iut.proftracker.views.activitites.auth.LoginActivity;

public class MainActivity extends AppCompatActivity implements PostExecuteActivity<Professeur>, Clickable {
    private final String TAG = "AAAAAAAAAAAAAAAAAAAAAA";
    private static final List<Professeur> professeurList = new ArrayList<>(); //the complete list
    private final List<Professeur> displayedprofesseur = new ArrayList<>(); //the displayed list
    private ProfesseurAdapter adapter;
    private Intent professorIntent, loginIntent;
    private FirebaseUser firebaseUser;
    private Button buttonFrancais, buttonMathematiques, buttonHistoire, buttonInformatique;
    private final boolean[] isActivatedButton = {false,false,false,false};
    private RangeSlider rangeSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser == null) {
            this.loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginIntent);
        }

        Button logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(
                click -> {
                    FirebaseAuth.getInstance().signOut();
                    this.loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginIntent);
                }
        );


        String url = "https://rayanoutili.github.io/proftrackerjson/data2.json";
        new HttpAsyncGet<>(url, Professeur.class, this, new ProgressDialog(MainActivity.this) );

        BottomNavigationView menu = findViewById(R.id.menu);
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                final int idnotification = R.id.notificationPage;
                final int idProfile = R.id.profilePage;

                final String ProfName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

                Professeur professeurConnecte = null;
                for (Professeur prof : professeurList) {
                    if(prof.getNom().equals(ProfName)){
                        professeurConnecte = prof;
                    }
                }

                if(idnotification == id){
                    Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                    intent.putExtra("key",1);
                    startActivity(intent);
                }else if(id == idProfile){
                    Intent intent = new Intent(MainActivity.this, ProfesseurProfilActivity.class);
                    intent.putExtra("key",professeurConnecte);
                    startActivity(intent);
                }else{
                    System.out.println("DKNOW");
                }
                return true;
            }
        });
        /*
        Filtrer les professeurs par matières
         */
        buttonFrancais = findViewById(R.id.buttonFrancais);
        buttonMathematiques = findViewById(R.id.buttonMathematiques);
        buttonHistoire = findViewById(R.id.buttonHistoire);
        buttonInformatique = findViewById(R.id.buttonInformatique);

        setButtonOnClickListener(buttonFrancais, "Français", 0);
        setButtonOnClickListener(buttonMathematiques, "Mathématiques", 1);
        setButtonOnClickListener(buttonHistoire, "Histoire", 2);
        setButtonOnClickListener(buttonInformatique, "Informatique", 3);

        rangeSlider = findViewById(R.id.rangeSlider);

        rangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(RangeSlider slider) {
            }

            @Override
            public void onStopTrackingTouch(RangeSlider slider) {
            }
        });

        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(RangeSlider slider, float value, boolean fromUser) {
                String[] matieres = {"Français","Mathématiques","Histoire","Informatique"};
                int compteur = 0;
                boolean unBoutonActif = false;
                for(boolean isActivated : isActivatedButton){
                    if(isActivated){
                        unBoutonActif = true;
                        filterProfesseursByMatiere(matieres[compteur]);
                        break;
                    }
                    compteur++;
                }
                if(!unBoutonActif){
                    filterProfesseursReset();
                }
            }
        });
    }

    private void setButtonOnClickListener(Button button, String matiere, int indice) {
        button.setOnClickListener(view -> {
            if (!isActivatedButton[indice]) {
                isActivatedButton[indice] = true;
                filterProfesseursByMatiere(matiere);
                resetButtonsColors();
                button.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            } else {
                isActivatedButton[indice] = false;
                filterProfesseursReset();
                resetButtonsColors();
            }
            for(int i = 0; i < isActivatedButton.length; i++){
                if(i != indice){
                    isActivatedButton[i] = false;
                }
            }
        });
    }

    public void resetButtonsColors() {
        buttonFrancais.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#005072")));
        buttonMathematiques.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#005072")));
        buttonHistoire.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#005072")));
        buttonInformatique.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#005072")));
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
            if (professeur.getMatieres().contains(matiere)
                    && rangeSlider.getValues().get(0) <= professeur.getPrixFloat()
                    && professeur.getPrixFloat() <= rangeSlider.getValues().get(1)) {
                filteredProfesseurs.add(professeur);
            }
        }
        displayedprofesseur.clear();
        displayedprofesseur.addAll(filteredProfesseurs);
        adapter.notifyDataSetChanged();
    }

    private void filterProfesseursReset(){
        List<Professeur> filteredProfesseurs = new ArrayList<>();
        for (Professeur professeur : professeurList) {
            if (rangeSlider.getValues().get(0) <= professeur.getPrixFloat()
                    && professeur.getPrixFloat() <= rangeSlider.getValues().get(1)) {
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