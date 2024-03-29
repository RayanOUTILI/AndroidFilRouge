package edu.iut.proftracker.views.activitites;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import edu.iut.proftracker.R;

public class LoginActivity extends AppCompatActivity {

    private Intent registerIntent, mainIntent;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

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
        this.firebaseFirestore = FirebaseFirestore.getInstance();
    
        Button loginButton = findViewById(R.id.loginButton);
        EditText usernameTextField = findViewById(R.id.usernameInputField);
        EditText passwordTextField = findViewById(R.id.passwordInputField);

        TextView registerLink = findViewById(R.id.noAccount);

        Button animation = findViewById(R.id.launchAnim);
        animation.setOnClickListener(
                click -> {
                    animation();
                }
        );


        registerLink.setOnClickListener(
                click -> {
                    this.registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(registerIntent);
                }
        );


        loginButton.setOnClickListener(
                click -> {
                    if(usernameTextField.getText().toString().equals("")) {
                        fieldError(usernameTextField);
                    }
                    else if (passwordTextField.getText().toString().equals("")){
                        fieldError(passwordTextField);
                    }
                    else {
                        login(usernameTextField.getText().toString(), passwordTextField.getText().toString());
                    }
                }
        );

    }

    private void login(String username, String password) {

        firebaseFirestore.collection("utilisateurs")
                .get()
                .addOnCompleteListener(
                    task -> {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if(documentSnapshot.get("username").equals(username)) {
                                     String emailFromUsername = documentSnapshot.get("email").toString();
                                     signInWithEmailAndPassword(emailFromUsername, password);
                                }
                            }
                        }
                    }
                );
    }

    private void signInWithEmailAndPassword(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                    task -> {
                        if(task.isSuccessful()) {
                            mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(mainIntent);
                        }
                        else {
                            loginError();
                        }
                    }
            );
    }

    public void fieldError(EditText textField) {

    }

    public void loginError() {
        // TODO Faire quelque chose pour montrer que soit le mot de passe soit le login est faux
    }

    public void animation() {
        ImageView lib1 = findViewById(R.id.library);
        ImageView lib2 = findViewById(R.id.library2);

        ImageView book = findViewById(R.id.book);
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.book_animation);
        book.startAnimation(anim);

        Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.lib_animation);
        lib1.startAnimation(anim2);

        Animation anim3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.lib2_animation);
        lib2.startAnimation(anim3);
    }
}
