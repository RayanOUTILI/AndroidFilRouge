package edu.iut.proftracker.views.activitites.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.iut.proftracker.R;
import edu.iut.proftracker.views.activitites.MainActivity;
import edu.iut.proftracker.views.activitites.auth.LoginActivity;

/** Classe RegisterActivity permettant de gérer l'activité d'inscription de l'application ProfTracker
 * 
 *  @see androidx.appcompat.app.AppCompatActivity
 */
public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private Intent loginIntent, mainIntent;

    /** Méthode onCreate permettant de créer l'activité d'inscription de l'application ProfTracker
     * 
     * <p> On initialise les éléments de l'activité tels que les champs de texte, les boutons et les liens </p>
     * <p> On associe les actions aux éléments de l'activité </p>
     * <p> On vérifie si les champs de texte sont vides lors de l'inscription ou si l'inscription est impossible </p>
     * <p> On enregistre l'utilisateur dans la base de données Firestore et on redirige l'utilisateur vers l'activité principale </p>
     * 
     * @param savedInstanceState : Instance de l'activité
     * 
     * @see android.os.Bundle
     * @see android.widget.Button
     * @see android.widget.EditText
     * @see android.widget.TextView
     * @see com.google.firebase.auth.FirebaseAuth
     * @see com.google.firebase.firestore.FirebaseFirestore
     * @see android.content.Intent
     * @see edu.iut.proftracker.views.activitites.MainActivity
     * @see edu.iut.proftracker.views.activitites.auth.LoginActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();

        Button registerButton = findViewById(R.id.registerButton);

        EditText usernameTextField = findViewById(R.id.usernameInputField);
        EditText emailTextField = findViewById(R.id.emailInputField);
        EditText passwordTextField = findViewById(R.id.passwordInputField);

        TextView loginLink = findViewById(R.id.havingAccount);

        loginLink.setOnClickListener(
                click -> {
                    this.loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginIntent);
                }
        );

        registerButton.setOnClickListener(
                click -> {
                    if(emailTextField.getText().toString().equals("")) {
                        fieldError(emailTextField);
                    }
                    else if (passwordTextField.getText().toString().equals("")){
                        fieldError(passwordTextField);
                    }
                    else if (usernameTextField.getText().toString().equals("")){
                        fieldError(usernameTextField);
                    }
                    else {
                        register(emailTextField.getText().toString(), passwordTextField.getText().toString(), usernameTextField.getText().toString());
                    }
                });
    }

    /** Méthode register permettant d'enregistrer un utilisateur dans la base de données Firestore
     * 
     * <p> On crée un utilisateur avec son email et son mot de passe </p>
     * <p> On ajoute le nom d'utilisateur à la base de données Firestore pour ensuite pouvoir permettre a ce dernier la connexion à l'aide de son nom d'utilisateur et mot de passe</p>
     * <p> Si l'inscription est réussie, on redirige l'utilisateur vers l'activité principale de l'application et on set le nom d'utilisateur de l'utilisateur </p>
     * <p> On redirige l'utilisateur vers l'activité principale de l'application si l'inscription est réussie </p>
     * <p> Sinon, on affiche un message d'erreur </p>
     * 
     * @param email : Email de l'utilisateur
     * @param password : Mot de passe de l'utilisateur
     * @param username : Nom d'utilisateur de l'utilisateur
     * 
     * @see com.google.firebase.auth.FirebaseAuth
     * @see com.google.firebase.auth.FirebaseUser
     * @see com.google.firebase.auth.UserProfileChangeRequest
     * @see com.google.firebase.firestore.FirebaseFirestore
     * @see android.content.Intent
     * @see edu.iut.proftracker.views.activitites.MainActivity
     */
    private void register(String email, String password, String username) {
        this.firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        // set du nom d'utilisateur directement dans l'objet FirebaseUser
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build();
                        user.updateProfile(profileUpdates);
                        addUsernameToDatabase(username, email);
                        this.mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainIntent);
                    } else {
                        registerError();
                    }

                });
    }

    /** Méthode addUsernameToDatabase permettant d'ajouter le nom d'utilisateur à la base de données Firestore
     * 
     * <p> On crée un objet de type Map contenant le nom d'utilisateur et l'email de l'utilisateur </p>
     * <p> On ajoute le nom d'utilisateur et l'email de l'utilisateur à la collection "utilisateurs" de la base de données Firestore </p>
     * 
     * @param username : Nom d'utilisateur de l'utilisateur
     * @param email : Email de l'utilisateur
     * 
     * @see com.google.firebase.firestore.FirebaseFirestore
     * @see java.util.HashMap
     * @see java.util.Map
     */
    private void addUsernameToDatabase(String username, String email) {
        Map<String, String> dataToPush = new HashMap<>();
        dataToPush.put("username", username);
        dataToPush.put("email", email);
        firebaseFirestore.collection("utilisateurs").add(dataToPush);
    }

    /** Méthode fieldError permettant d'afficher un message d'erreur si un champ de texte est vide
     * 
     * <p> On affiche un message d'erreur en fonction du type de champ de texte </p>
     * 
     * @param textField : Champ de texte à vérifier
     * 
     * @see android.widget.EditText
     * @see android.widget.EditText#getInputType()
     * @see android.widget.EditText#setError(CharSequence)
     */
    public void fieldError(EditText textField) {
        switch(textField.getInputType()) {
            // cas de l'email
            case 32:
                textField.setError("Email is required");
                break;
            // cas du mot de passe
            case 129:
                textField.setError("Password is required");
                break;
            // cas du username
            case 1:
                textField.setError("Username is required");
                break;
            default:
                break;

        }
    }

    public void registerError() {
        // TODO Faire quelque chose pour montrer que le register est impossible
    }
}

