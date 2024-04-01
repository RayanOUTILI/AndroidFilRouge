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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.iut.proftracker.R;

import java.util.ArrayList;
import java.util.List;

import edu.iut.proftracker.controllers.Clickable;
import edu.iut.proftracker.controllers.HttpAsyncGet;
import edu.iut.proftracker.controllers.PostExecuteActivity;
import edu.iut.proftracker.models.Professeur;
import edu.iut.proftracker.models.ProfesseurAdapter;
import edu.iut.proftracker.views.activitites.auth.LoginActivity;

public class MainActivity extends AppCompatActivity implements PostExecuteActivity<Professeur>, Clickable {
    private final String TAG = "MainActivity Tag";
    private static final List<Professeur> listeProfesseur = new ArrayList<>();
    private final List<Professeur> listeProfesseurAffiche = new ArrayList<>();
    private ProfesseurAdapter adapter;
    private Intent professeurIntent, loginIntent;
    private FirebaseUser firebaseUtilisateur;
    private Button buttonFrancais, boutonMathematiques, boutonHistoire, boutonInformatique;
    private final boolean[] boutonActive = {false,false,false,false};
    private RangeSlider rangeSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.firebaseUtilisateur = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUtilisateur == null) {
            this.loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginIntent);
        }

        Button boutonDeconnexion = findViewById(R.id.logout);
        boutonDeconnexion.setOnClickListener(
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

                final String nomProfesseur = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

                Professeur professeurConnecte = null;
                for (Professeur prof : listeProfesseur) {
                    if(prof.getNom().equals(nomProfesseur)){
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
        boutonMathematiques = findViewById(R.id.buttonMathematiques);
        boutonHistoire = findViewById(R.id.buttonHistoire);
        boutonInformatique = findViewById(R.id.buttonInformatique);

        setButtonOnClickListener(buttonFrancais, "Français", 0);
        setButtonOnClickListener(boutonMathematiques, "Mathématiques", 1);
        setButtonOnClickListener(boutonHistoire, "Histoire", 2);
        setButtonOnClickListener(boutonInformatique, "Informatique", 3);

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
                for(boolean actif : boutonActive){
                    if(actif){
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
            if (!boutonActive[indice]) {
                boutonActive[indice] = true;
                filterProfesseursByMatiere(matiere);
                resetButtonsColors();
                button.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            } else {
                boutonActive[indice] = false;
                filterProfesseursReset();
                resetButtonsColors();
            }
            for(int i = 0; i < boutonActive.length; i++){
                if(i != indice){
                    boutonActive[i] = false;
                }
            }
        });
    }

    public void resetButtonsColors() {
        buttonFrancais.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#005072")));
        boutonMathematiques.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#005072")));
        boutonHistoire.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#005072")));
        boutonInformatique.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#005072")));
    }

    @Override
    public void onPostExecute(List<Professeur> itemList) {
        listeProfesseur.addAll(itemList);
        listeProfesseurAffiche.addAll(itemList);
        adapter = new ProfesseurAdapter(listeProfesseurAffiche, this);
        ListView listview = findViewById(R.id.listeViewRecherche);
        listview.setAdapter(adapter);
        Log.d(TAG,"itemList = " + itemList);
    }

    // Filtrer les professeurs par matières
    private void filterProfesseursByMatiere(String matiere) {
        List<Professeur> filteredProfesseurs = new ArrayList<>();
        for (Professeur professeur : listeProfesseur) {
            if (professeur.getMatieres().contains(matiere)
                    && rangeSlider.getValues().get(0) <= professeur.getPrixFloat()
                    && professeur.getPrixFloat() <= rangeSlider.getValues().get(1)) {
                filteredProfesseurs.add(professeur);
            }
        }
        listeProfesseurAffiche.clear();
        listeProfesseurAffiche.addAll(filteredProfesseurs);
        adapter.notifyDataSetChanged();
    }

    private void filterProfesseursReset(){
        List<Professeur> filteredProfesseurs = new ArrayList<>();
        for (Professeur professeur : listeProfesseur) {
            if (rangeSlider.getValues().get(0) <= professeur.getPrixFloat()
                    && professeur.getPrixFloat() <= rangeSlider.getValues().get(1)) {
                filteredProfesseurs.add(professeur);
            }
        }
        listeProfesseurAffiche.clear();
        listeProfesseurAffiche.addAll(filteredProfesseurs);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onClicItem(int Index) {
        int itemIndex = findIndexInList(Index); // Selection du proffesseur clické
        Log.d(TAG, String.valueOf(itemIndex));
        // Création et ajout dans l'intent du professeur
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra(getString(R.string.key), listeProfesseur.get(itemIndex));
        // Lancement de la class ProfileActivity
        startActivity(intent);
    }

    private int findIndexInList(int index) {
        Professeur characterToFind = listeProfesseur.get(index);
        for (int i = 0; i < listeProfesseur.size(); i++) {
            if (listeProfesseur.get(i).equals(characterToFind)) {
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