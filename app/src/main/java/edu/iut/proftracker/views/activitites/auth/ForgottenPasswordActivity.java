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

public class ForgottenPasswordActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Intent loginIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);
        this.firebaseAuth = FirebaseAuth.getInstance();

        EditText email = findViewById(R.id.emailEditText);
        Button sendEmail = findViewById(R.id.sendEmailButton);

        TextView backToLogin = findViewById(R.id.backToLoginTextView);

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
                                ProgressDialog progressDialog = new ProgressDialog(this);
                                if(task.isSuccessful()){
                                    email.setText("");
                                    progressDialog.setMessage("Email bien envoyé, veuillez vérifier votre boite mail.");
                                }
                                else{
                                    progressDialog.setMessage("Erreur lors de l'envoi de l'email.");

                                }
                                progressDialog.show();
                                progressDialog.dismiss();
                            });
                }
        );

    }
}