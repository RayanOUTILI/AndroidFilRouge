package edu.iut.proftracker.models;

import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class Notification {
    private static final String TAG = "Notification";

    private String professeur;
    private String eleve;
    private String lieu;
    private String date;
    private String matiere;

    public Notification(String professeur, String eleve, String lieu, String date, String matiere) {
        this.professeur = professeur;
        this.eleve = eleve;
        this.lieu = lieu;
        this.date = date;
        this.matiere = matiere;
    }

    // Méthode pour récupérer toutes les notifications depuis Firestore
    public static List<Notification> getNotification() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Notification> notifications = new ArrayList<>();

        db.collection("notifications")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());


                                String professeur = document.getString("professeur");
                                String eleve = document.getString("eleve");
                                String lieu = document.getString("lieu");
                                String date = document.getString("date");
                                String matiere = document.getString("matiere");

                                Notification notification = new Notification(professeur, eleve, lieu, date, matiere);
                                notifications.add(notification);
                            }
                            for (Notification notification : notifications) {
                                System.out.println(notification.professeur + " " + notification.eleve + " " + notification.lieu + " " + notification.date + " " + notification.matiere);
                                Log.d(TAG, notification.professeur + " " + notification.eleve + " " + notification.lieu + " " + notification.date + " " + notification.matiere);
                            }
                        } else {
                            Log.e(TAG, "Erreur lors de la récupération des notifications", task.getException());
                        }
                    }
                });
        return notifications;
    }

    public static List<Notification> getNotification(String profConcerne) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Notification> notifications = new ArrayList<>();

        db.collection("notifications")
                .whereEqualTo("professeur", profConcerne)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String professeur = document.getString("professeur");
                                String eleve = document.getString("eleve");
                                String lieu = document.getString("lieu");
                                String date = document.getString("date");
                                String matiere = document.getString("matiere");

                                Notification notification = new Notification(professeur, eleve, lieu, date, matiere);
                                notifications.add(notification);
                            }
                            for (Notification notification : notifications) {
                                System.out.println(notification.professeur + " " + notification.eleve + " " + notification.lieu + " " + notification.date + " " + notification.matiere);
                                Log.d(TAG, notification.professeur + " " + notification.eleve + " " + notification.lieu + " " + notification.date + " " + notification.matiere);
                            }
                        } else {
                            Log.e(TAG, "Erreur lors de la récupération des notifications", task.getException());
                        }
                    }
                });
        return notifications;
    }

    public static void createNotification(String professeur, String eleve, String lieu, String date, String matiere) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Notification notification = new Notification(professeur, eleve, lieu, date, matiere);

        db.collection("notifications")
                .add(notification)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Notification ajoutée avec succès");
                        } else {
                            Log.e(TAG, "Erreur lors de l'ajout de la notification", task.getException());
                        }
                    }
                });
    }

    public String getProfesseur() {
        return professeur;
    }

    public String getEleve() {
        return eleve;
    }

    public String getLieu() {
        return lieu;
    }

    public String getDate() {
        return date;
    }

    public String getMatiere() {
        return matiere;
    }
}
