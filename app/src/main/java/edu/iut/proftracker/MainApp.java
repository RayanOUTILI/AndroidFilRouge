package edu.iut.proftracker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.iut.proftracker.views.activitites.auth.LoginActivity;
import edu.iut.proftracker.views.activitites.MainActivity;

public class MainApp extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        FirebaseApp.initializeApp(context);
        FirebaseUser utilisateurActuel = FirebaseAuth.getInstance().getCurrentUser();
        if(utilisateurActuel != null) {
            Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainIntent);
        }else {
            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginIntent);
        }
    }
    public static Context getContext() {
        return context;
    }
}
