package edu.iut.proftracker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseUser;

import edu.iut.proftracker.views.activitites.LoginActivity;
import edu.iut.proftracker.views.activitites.MainActivity;

public class MainApp extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext() {
        return context;
    }
}
