package edu.iut.proftracker.views.activitites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.iut.proftracker.R;

public class LoginActivity extends AppCompatActivity {

    private Intent registerIntent, mainIntent;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null) {
            this.mainIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(this.mainIntent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.firebaseAuth = FirebaseAuth.getInstance();

        Button loginButton = findViewById(R.id.loginButton);
        TextInputEditText emailTextField = findViewById(R.id.emailInputField);
        TextInputEditText passwordTextField = findViewById(R.id.passwordInputField);

        TextView forgotPasswordLink = findViewById(R.id.forgottenPassword);
        TextView registerLink = findViewById(R.id.noAccount);

        registerLink.setOnClickListener(
                click -> {
                    this.registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(registerIntent);
                }
        );


        loginButton.setOnClickListener(
                click -> {
                    if(emailTextField.getText().toString().equals("")) {
                        fieldError(emailTextField);
                    }
                    else if (passwordTextField.getText().toString().equals("")){
                        fieldError(passwordTextField);
                    }
                    else {
                        this.firebaseAuth.signInWithEmailAndPassword(emailTextField.getText().toString(), passwordTextField.getText().toString())
                                .addOnCompleteListener(
                                        task -> {
                                            if(task.isSuccessful()) {
                                                mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(mainIntent);
                                            }
                                            else {
                                                loginError();
                                            }
                                    });
                    }
                }
        );

    }

    public void fieldError(TextInputEditText textField) {
        // TODO Faire en sorte de surligner l'input en rouge pour signaler l'erreur
    }

    public void loginError() {
        // TODO Faire quelque chose pour montrer que soit le mot de passe soit le login est faux
    }
}
