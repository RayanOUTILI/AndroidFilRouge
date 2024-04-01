package edu.iut.proftracker.views.activitites.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import edu.iut.proftracker.R;

/** Classe ForgottenPasswordActivity permettant de gérer l'activité de réinitialisation du mot de passe
 *
 * @see androidx.appcompat.app.AppCompatActivity 
 */
public class ForgottenPasswordActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Intent loginIntent;
    
    /** Méthode onCreate permettant de créer l'activité de réinitialisation du mot de passe
     * 
     * @param savedInstanceState : Instance de l'activité
     * 
     * @see android.os.Bundle
     * @see android.content.Intent
     * @see com.google.firebase.auth.FirebaseAuth
     * @see android.widget.EditText
     * @see android.widget.Button
     * @see android.widget.TextView
     * @see android.app.ProgressDialog
     * @see android.content.Intent
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Appel du constructeur de la super classe
        super.onCreate(savedInstanceState);

        // Association de l'activité à son layout
        setContentView(R.layout.activity_forgotten_password);

        // Initialisation de l'instance de FirebaseAuth
        this.firebaseAuth = FirebaseAuth.getInstance();

        // Récupération des éléments du layout
        EditText email = findViewById(R.id.emailEditText);
        Button sendEmail = findViewById(R.id.sendEmailButton);

        TextView backToLogin = findViewById(R.id.backToLoginTextView);

        // Association des actions aux éléments du layout
        backToLogin.setOnClickListener(
                click -> {
                    this.loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginIntent);
                }
        );

        sendEmail.setOnClickListener(
                click -> {
                    if(email.getText().toString().isEmpty()){
                        email.setError("Rentrez votre email");
                        return;
                    }
                    firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(
                            task -> {
                                // Création d'une boite de dialogue pour afficher le résultat de l'envoi de l'email
                                ProgressDialog progressDialog = new ProgressDialog(this);
                                if(task.isSuccessful()){
                                    email.setText("");
                                    // Affichage du message de succès
                                    progressDialog.setMessage("Email bien envoyé, veuillez vérifier votre boite mail.");
                                }
                                else{
                                    // Affichage du message d'erreur
                                    progressDialog.setMessage("Erreur lors de l'envoi de l'email.");
                                }
                                progressDialog.show();
                                progressDialog.dismiss();
                            });
                }
        );

    }
}