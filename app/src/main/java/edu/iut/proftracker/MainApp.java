package edu.iut.proftracker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.iut.proftracker.views.activitites.auth.LoginActivity;
import edu.iut.proftracker.views.activitites.MainActivity;

/** Classe MainApp étant l'application principale de l'application ProfTracker
 * 
 * @see android.app.Application
 */
public class MainApp extends Application {
    private static Context context;

    /** Méthode onCreate permettant de créer l'application ProfTracker
     *  
     * <p> Cette méthode permet d'initialiser l'application ProfTracker en initialisant Firebase et en vérifiant si un utilisateur est connecté </p>
     * <p> Si un utilisateur est connecté, l'application redirige l'utilisateur vers l'activité principale de l'application </p>
     * <p> Sinon, l'application redirige l'utilisateur vers l'activité de connexion </p>
     * 
     * @see android.content.Context
     * @see com.google.firebase.FirebaseApp
     * @see com.google.firebase.auth.FirebaseAuth
     * @see com.google.firebase.auth.FirebaseUser
     * @see android.content.Intent
     * @see edu.iut.proftracker.views.activitites.MainActivity
     * @see edu.iut.proftracker.views.activitites.auth.LoginActivity
     */
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        // Initialisation de Firebase
        FirebaseApp.initializeApp(context);
    
        // Vérification de la connexion de l'utilisateur
        FirebaseUser utilisateurActuel = FirebaseAuth.getInstance().getCurrentUser();

        // Redirection de l'utilisateur
        if(utilisateurActuel != null) {
            Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainIntent);
        } else {
            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginIntent);
        }
    }

    /** Méthode getContext permettant de récupérer le contexte de l'application ProfTracker
     * 
     * @return le contexte de l'application ProfTracker
     * 
     * @see android.content.Context
     */
    public static Context getContext() {
        return context;
    }
}
