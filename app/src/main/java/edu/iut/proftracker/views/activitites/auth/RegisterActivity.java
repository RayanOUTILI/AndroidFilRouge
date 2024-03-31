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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private Intent loginIntent, mainIntent;

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

    private void register(String email, String password, String username) {
        this.firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
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

    private void addUsernameToDatabase(String username, String email) {
        Map<String, String> dataToPush = new HashMap<>();
        dataToPush.put("username", username);
        dataToPush.put("email", email);
        firebaseFirestore.collection("utilisateurs").add(dataToPush);
    }

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
