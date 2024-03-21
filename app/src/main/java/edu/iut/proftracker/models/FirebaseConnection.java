package edu.iut.proftracker.models;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FirebaseConnection {
    private FirebaseFirestore firebaseFirestore;
    public FirebaseConnection() {
        this.firebaseFirestore = FirebaseFirestore.getInstance();

    }

    /**
     * @param collection La collection NoSQL à laquelle on veut accéder
     * @param field Le champ de la collection sur lequel on veut faire une requête
     * @param value La valeur du champ sur lequel on veut faire une requête
     *
     * @return La resultat de la requête
     */

    public String select(String collection, String field, String value) {
        List<String> professors = new ArrayList<>();
        //Faire en sorte d'attendre la fin de la requête
        firebaseFirestore.collection(collection).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (int i = 0; i < task.getResult().size(); i++) {
                    professors.add(task.getResult().getDocuments().get(i).getData().toString());
                }
            }
        });




        return professors.toString();
    }



    public FirebaseFirestore getFirebaseFirestore() {
        return firebaseFirestore;
    }

    public void setFirebaseFirestore(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    public void closeConnection() {
        this.firebaseFirestore = null;
    }





}
