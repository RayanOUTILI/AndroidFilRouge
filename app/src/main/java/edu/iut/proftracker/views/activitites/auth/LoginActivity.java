package edu.iut.proftracker.views.activitites.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import edu.iut.proftracker.R;
import edu.iut.proftracker.views.activitites.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private Intent registerIntent, mainIntent, forgottenPasswordIntent;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
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
        TextView forgottenPassword = findViewById(R.id.forgottenPassword);

        Button animation = findViewById(R.id.launchAnim);
        animation.setOnClickListener(
                click -> {
                    animation();
                }
        );

        forgottenPassword.setOnClickListener(
                click -> {
                    this.forgottenPasswordIntent = new Intent(getApplicationContext(), ForgottenPasswordActivity.class);
                    startActivity(forgottenPasswordIntent);
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
                    if (passwordTextField.getText().toString().equals("")){
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
                                        System.out.println(emailFromUsername);
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
        switch(textField.getInputType()) {
            // cas du password
            case 129:
                textField.setError("Rentrez votre mot de passe");
                break;
            // cas du username
            case 1:
                textField.setError("Rentrez votre nom d'utilisateur");
                break;
            default:
                break;

        }
    }

    public void loginError() {
       Drawable textFieldBackground = findViewById(R.id.usernameInputField).getBackground();
       Drawable passwordFieldBackground = findViewById(R.id.passwordInputField).getBackground();
       if(textFieldBackground instanceof  ShapeDrawable && passwordFieldBackground instanceof  ShapeDrawable) {
           ((ShapeDrawable) textFieldBackground).getPaint().setColor(getResources().getColor(R.color.red, getTheme()));
            ((ShapeDrawable) passwordFieldBackground).getPaint().setColor(getResources().getColor(R.color.red, getTheme()));
       }
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
