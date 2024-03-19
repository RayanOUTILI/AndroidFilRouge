package edu.iut.proftracker.views.activitites;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import java.util.List;
import edu.iut.proftracker.R;
import edu.iut.proftracker.controllers.Clickable;
import edu.iut.proftracker.controllers.HttpAsyncGet;
import edu.iut.proftracker.controllers.PostExecuteActivity;
import edu.iut.proftracker.models.Professeur;

public class MainActivity extends AppCompatActivity implements PostExecuteActivity<Professeur>, Clickable {

    private Intent professorIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        String url = "http://edu.info06.net/onepiece/characters.json";
        new HttpAsyncGet<>(url, Character.class, this, new ProgressDialog(MainActivity.this) );
         */
    }

    @Override
    public void onPostExecute(List<Professeur> itemList) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onClicItem(int itemIndex) {

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}