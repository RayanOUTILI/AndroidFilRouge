package edu.iut.proftracker.views.activitites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
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
                    setContentView(R.layout.activity_main);
                }else if(idnotification == id){
                    setContentView(R.layout.activity_notification);
                }else if(id == idProfile){
                    setContentView(R.layout.activity_profile);
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