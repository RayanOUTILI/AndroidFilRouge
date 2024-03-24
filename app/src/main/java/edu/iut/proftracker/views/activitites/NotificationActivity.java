package edu.iut.proftracker.views.activitites;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.iut.proftracker.R;
import edu.iut.proftracker.controllers.Clickable;
import edu.iut.proftracker.controllers.PostExecuteActivity;
import edu.iut.proftracker.models.Notification;
import edu.iut.proftracker.models.NotificationAdapter;

public class NotificationActivity extends AppCompatActivity  implements Clickable, PostExecuteActivity<Notification>  {
    private List<Notification> displayedNotifications = new ArrayList<>();

    private NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        this.displayedNotifications = Notification.getNotification("Outili");
        System.out.println(displayedNotifications.size());
    }

    @Override
    public void onPostExecute(List<Notification> itemList) {
        System.out.println("onPostExecute");
        adapter = new NotificationAdapter(displayedNotifications, this);
        ListView listview = findViewById(R.id.listeViewNotification);
        listview.setAdapter(adapter);
    }


    @Override
    public void onClicItem(int itemIndex) {

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }


}