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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Intent loginIntent, mainIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.firebaseAuth = FirebaseAuth.getInstance();

        Button registerButton = findViewById(R.id.registerButton);

        TextInputEditText emailTextField = findViewById(R.id.emailInputField);
        TextInputEditText passwordTextField = findViewById(R.id.passwordInputField);

        TextView registerLink = findViewById(R.id.havingAccount);

        registerLink.setOnClickListener(
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
                    else {
                        this.firebaseAuth.createUserWithEmailAndPassword(emailTextField.getText().toString(), passwordTextField.getText().toString())
                                .addOnCompleteListener(
                                        task -> {
                                            if(task.isSuccessful()) {
                                                mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(mainIntent);
                                            }
                                            else {
                                                registerError();
                                            }
                                    });
                    }
                });
    }

    public void fieldError(TextInputEditText textField) {
        // TODO Faire en sorte de surligner l'input en rouge pour signaler l'erreur
    }

    public void registerError() {
        // TODO Faire quelque chose pour montrer que le register est impossible
    }
}

