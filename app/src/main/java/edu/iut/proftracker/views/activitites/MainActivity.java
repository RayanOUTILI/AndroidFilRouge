package edu.iut.proftracker.views.activitites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import edu.iut.proftracker.R;

public class MainActivity extends AppCompatActivity {

    private Intent professorIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}