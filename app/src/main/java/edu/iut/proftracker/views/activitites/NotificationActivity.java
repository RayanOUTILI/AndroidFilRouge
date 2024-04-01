package edu.iut.proftracker.views.activitites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import edu.iut.proftracker.R;
import edu.iut.proftracker.controllers.Clickable;
import edu.iut.proftracker.controllers.PostExecuteActivity;
import edu.iut.proftracker.models.Notification;
import edu.iut.proftracker.models.NotificationAdapter;

public class NotificationActivity extends AppCompatActivity {
    private List<Notification> displayedNotifications = new ArrayList<>();
    private FirebaseUser firebaseUser;

    private NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        String nomProfile = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        this.displayedNotifications = Notification.getNotification(nomProfile, this);

        ImageView croix = findViewById(R.id.back);
        croix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
                intent.putExtra("key",1);
                startActivity(intent);
            }
        });
    }

    public void onPostExecute(List<Notification> itemList) {
        adapter = new NotificationAdapter(displayedNotifications, this);
        ListView listview = findViewById(R.id.listeViewNotification);
        listview.setAdapter(adapter);
    }

    public Context getContext() {
        return this;
    }
}