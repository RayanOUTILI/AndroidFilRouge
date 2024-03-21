package edu.iut.proftracker.views.activitites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import edu.iut.proftracker.R;
import edu.iut.proftracker.models.FirebaseConnection;

public class MainActivity extends AppCompatActivity {

    private Intent professorIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);

        FirebaseConnection firebaseConnection = new FirebaseConnection();
        String professors = firebaseConnection.select("professors", "name", "John Doe");

        System.out.println(professors);

    }
}