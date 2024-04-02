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

/** Classe MainActivity permettant de gérer l'activité principale de l'application ProfTracker
 * 
 * @see androidx.appcompat.app.AppCompatActivity
 */
public class MainActivity extends AppCompatActivity implements PostExecuteActivity<Professeur>, Clickable {
    private final String TAG = "MainActivity Tag";
    private static final List<Professeur> listeProfesseur = new ArrayList<>();
    private final List<Professeur> listeProfesseurAffiche = new ArrayList<>();
    private ProfesseurAdapter adapter;
    private Intent professeurIntent, loginIntent;
    private FirebaseUser firebaseUtilisateur;
    private Button buttonFrancais, boutonMathematiques, boutonPhysique, boutonPhilosophie;
    private final boolean[] boutonActive = {false,false,false,false};
    private RangeSlider rangeSlider;

    /** Méthode onCreate permettant de créer l'activité principale de l'application ProfTracker
     *  
     * <p> On initialise les éléments de l'activité tels que les champs de texte, les boutons et les images </p>
     * <p> On récupère les professeurs de l'application et on les affiche dans l'activité </p>
     *  
     * @param savedInstanceState : Instance de l'activité
     * 
     * @see android.os.Bundle
     * @see com.google.firebase.auth.FirebaseAuth
     * @see com.google.firebase.auth.FirebaseUser
     * @see edu.iut.proftracker.models.Professeur
     * @see edu.iut.proftracker.models.ProfesseurAdapter
     * @see android.widget.Button
     * @see android.widget.ListView
     * @see android.widget.ProgressBar
     * @see android.widget.TextView
     * @see android.widget.Toast
     * @see com.google.android.material.bottomnavigation.BottomNavigationView
     * @see com.google.android.material.navigation.NavigationBarView
     * @see com.google.android.material.slider.RangeSlider
     * @see edu.iut.proftracker.views.activitites.auth.LoginActivity
     * @see edu.iut.proftracker.controllers.HttpAsyncGet
     * @see edu.iut.proftracker.controllers.PostExecuteActivity
     * @see edu.iut.proftracker.controllers.Clickable
     * @see edu.iut.proftracker.views.activitites.ProfileActivity
     * @see edu.iut.proftracker.views.activitites.NotificationActivity
     * @see android.content.Intent
     * @see android.content.Context
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Appel du constructeur de la super classe
        super.onCreate(savedInstanceState);

        // Association de l'activité à son layout et initialisation de l'activité
        setContentView(R.layout.activity_main);

        // Récupération de l'utilisateur connecté
        this.firebaseUtilisateur = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUtilisateur == null) {
            // Redirection vers l'activité de connexion si l'utilisateur n'est pas connecté càd si l'utilisateur est null
            this.loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginIntent);
        }

        // Mise en place du bouton de déconnexion
        Button boutonDeconnexion = findViewById(R.id.logout);
        boutonDeconnexion.setOnClickListener(
                click -> {
                    FirebaseAuth.getInstance().signOut();
                    this.loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginIntent);
                }
        );


        // Récupération des professeurs de l'application depuis le fichier JSON situé sur le serveur
        String url = "https://rayanoutili.github.io/proftrackerjson/data3.json";
        // Appel de la classe HttpAsyncGet pour récupérer les professeurs dans l'
        new HttpAsyncGet<>(url, Professeur.class, this, new ProgressDialog(MainActivity.this) );

        // Mise en place de la liste des professeurs
        BottomNavigationView menu = findViewById(R.id.menu);

        // Mise en place de l'écouteur d'événements sur le menu, pour rediriger l'utilisateur vers la page de notification ou de profil
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
                } else if(id == idProfile){
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("key",professeurConnecte);
                    intent.putExtra("estLui", true);
                    startActivity(intent);
                } else{
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
        boutonPhysique = findViewById(R.id.buttonPhysique);
        buttonPhilosophie = findViewById(R.id.buttonPhilosophie);

        // Appel de la méthode setButtonOnClickListener pour chaque bouton
        setButtonOnClickListener(buttonFrancais, "Français", 0);
        setButtonOnClickListener(boutonMathematiques, "Mathématiques", 1);
        setButtonOnClickListener(boutonPhysique, "Physique", 2);
        setButtonOnClickListener(buttonPhilosophie, "Philosophie", 3);

        // Mise en place du slider pour filtrer les professeurs par prix
        rangeSlider = findViewById(R.id.rangeSlider);

        // Mise en place de l'écouteur d'événements sur le slider pour filtrer les professeurs par prix
        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(RangeSlider slider, float value, boolean fromUser) {
                String[] matieres = {"Français","Mathématiques","Physique","Philosophie"};
                int compteur = 0;
                boolean unBoutonActif = false;
                for(boolean actif : boutonActive){
                    if(actif){
                        unBoutonActif = true;
                        filterProfesseursParMatiere(matieres[compteur]);
                        break;
                    }
                    compteur++;
                }
                if(!unBoutonActif){
                    filterProfesseursReinitialiser();
                }
            }
        });
    }

    /** Méthode setButtonOnClickListener permettant de mettre en place un écouteur d'événements sur un bouton
     * 
     * <p> On met en place un écouteur d'événements sur un bouton pour filtrer les professeurs par matières </p>
     * <p> On change la couleur du bouton en rouge si le bouton est actif </p>
     * 
     * @param bouton : Bouton sur lequel on met en place l'écouteur d'événements
     * @param matiere : Matière sur laquelle on filtre les professeurs
     * @param indice : Indice du bouton
     * 
     * @see android.widget.Button
     * @see java.lang.String
     */

    private void setButtonOnClickListener(Button bouton, String matiere, int indice) {
        bouton.setOnClickListener(view -> {
            if (!boutonActive[indice]) {
                boutonActive[indice] = true;
                filterProfesseursParMatiere(matiere);
                resetButtonsColors();
                bouton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            } else {
                boutonActive[indice] = false;
                filterProfesseursReinitialiser();
                resetButtonsColors();
            }
            for(int i = 0; i < boutonActive.length; i++){
                if(i != indice){
                    boutonActive[i] = false;
                }
            }
        });
    }

    /** Méthode resetButtonsColors permettant de réinitialiser les couleurs des boutons
     * 
     * <p> On réinitialise les couleurs des boutons en bleu </p>
     * 
     * @see android.widget.Button
     * @see android.content.res.ColorStateList
     * @see android.graphics.Color
     */
    public void resetButtonsColors() {
        buttonFrancais.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#005072")));
        boutonMathematiques.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#005072")));
        boutonPhysique.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#005072")));
        boutonPhilosophie.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#005072")));
    }

    /** Méthode onPostExecute permettant de récupérer les professeurs de l'application
     * 
     * <p> On récupère les professeurs de l'application et on les affiche dans l'activité </p>
     * 
     * @param itemList : Liste des professeurs de l'application
     * 
     * @see java.util.List
     * @see edu.iut.proftracker.models.Professeur
     * @see edu.iut.proftracker.models.ProfesseurAdapter
     * @see android.widget.ListView
     * @see android.content.Context
     */
    @Override
    public void onPostExecute(List<Professeur> itemList) {
        listeProfesseur.clear();
        listeProfesseurAffiche.clear();
        listeProfesseur.addAll(itemList);
        listeProfesseurAffiche.addAll(itemList);
        adapter = new ProfesseurAdapter(listeProfesseurAffiche, this);
        ListView listview = findViewById(R.id.listeViewRecherche);
        listview.setAdapter(adapter);
        Log.d(TAG,"itemList = " + itemList);
    }

    /** Méthode filterProfesseursParMatiere permettant de filtrer les professeurs par matière
     * 
     * <p> On filtre les professeurs par matière et on les affiche dans l'activité </p>
     * 
     * @param matiere : Matière sur laquelle on filtre les professeurs
     * 
     * @see edu.iut.proftracker.models.Professeur
     * @see edu.iut.proftracker.models.ProfesseurAdapter
     * @see com.google.android.material.slider.RangeSlider
     * @see java.util.ArrayList
     * @see java.util.List
     * @see android.widget.ListView
     */
    private void filterProfesseursParMatiere(String matiere) {
        List<Professeur> listeProfesseurFlitre = new ArrayList<>();
        for (Professeur professeur : listeProfesseur) {
            if (professeur.getMatieres().contains(matiere)
                    && rangeSlider.getValues().get(0) <= professeur.getPrixFloat()
                    && professeur.getPrixFloat() <= rangeSlider.getValues().get(1)) {
                listeProfesseurFlitre.add(professeur);
            }
        }
        listeProfesseurAffiche.clear();
        listeProfesseurAffiche.addAll(listeProfesseurFlitre);
        adapter.notifyDataSetChanged();
    }

    /** Méthode filterProfesseursReinitialiser permettant de réinitialiser les professeurs filtrés
     *  
     * <p> On réinitialise les professeurs filtrés et on les affiche dans l'activité </p>
     * 
     * @see edu.iut.proftracker.models.Professeur
     * @see edu.iut.proftracker.models.ProfesseurAdapter
     * @see com.google.android.material.slider.RangeSlider
     * @see java.util.ArrayList
     * @see java.util.List
     * @see android.widget.ListView
     */
    private void filterProfesseursReinitialiser(){
        List<Professeur> listeProfesseurFlitre = new ArrayList<>();
        for (Professeur professeur : listeProfesseur) {
            if (rangeSlider.getValues().get(0) <= professeur.getPrixFloat()
                    && professeur.getPrixFloat() <= rangeSlider.getValues().get(1)) {
                listeProfesseurFlitre.add(professeur);
            }
        }
        listeProfesseurAffiche.clear();
        listeProfesseurAffiche.addAll(listeProfesseurFlitre);
        adapter.notifyDataSetChanged();
    }

    /** Méthode onPointerCaptureChanged permettant de gérer le changement de capture de pointeur
     * 
     * @param hasCapture : Capture de pointeur
     * 
     * @see android.view.View
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    /** Méthode onClicItem permettant de gérer le clic sur un item
     *  
     * @param indice : Indice de l'item cliqué
     * 
     * @see edu.iut.proftracker.models.Professeur
     */
    @Override
    public void onClicItem(int indice) {
        Log.d(TAG, String.valueOf(indice));
        // Création et ajout dans l'intent du professeur
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra(getString(R.string.key), listeProfesseurAffiche.get(indice));
        // Lancement de la class ProfileActivity
        startActivity(intent);
    }


    /** Méthode pour récupérer le context de l'application
     * 
     * @return getApplicationContext() : Context de l'application
     */
    @Override
    public Context getContext() {
        return getApplicationContext();
    }

}