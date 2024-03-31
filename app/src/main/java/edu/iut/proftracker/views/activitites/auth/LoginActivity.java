package edu.iut.proftracker.views.activitites.auth;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    private Button loginButton;
    private EditText usernameTextField, passwordTextField;
    private TextView title, registerLink, forgottenPassword;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
    
        this.loginButton = findViewById(R.id.loginButton);
        this.usernameTextField = findViewById(R.id.usernameInputField);
        this.passwordTextField = findViewById(R.id.passwordInputField);

        this.title = findViewById(R.id.login);
        this.registerLink = findViewById(R.id.noAccount);
        this.forgottenPassword = findViewById(R.id.forgottenPassword);

        this.progressBar = findViewById(R.id.progressBar);



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
        clearAllViews();
        animation();
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

    private void animation() {
        ImageView lib1 = findViewById(R.id.library);
        ImageView lib2 = findViewById(R.id.library2);
        ImageView book = findViewById(R.id.book);
        ImageView logo = findViewById(R.id.logo);

        lib1.setVisibility(ImageView.VISIBLE);
        lib2.setVisibility(ImageView.VISIBLE);
        book.setVisibility(ImageView.VISIBLE);
        logo.setVisibility(ImageView.VISIBLE);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        Animation bookAnim = AnimationUtils.loadAnimation(this, R.anim.book_animation);
        Animation lib1Anim = AnimationUtils.loadAnimation(this, R.anim.lib_animation);
        Animation lib2Anim = AnimationUtils.loadAnimation(this, R.anim.lib2_animation);
        Animation logoAnim = AnimationUtils.loadAnimation(this, R.anim.logo_animation);

        lib1.startAnimation(lib1Anim);
        lib2.startAnimation(lib2Anim);
        book.startAnimation(bookAnim);
        logo.startAnimation(logoAnim);
    }

    private void clearAllViews() {
        this.title.setVisibility(TextView.INVISIBLE);
        this.usernameTextField.setVisibility(EditText.INVISIBLE);
        this.passwordTextField.setVisibility(EditText.INVISIBLE);
        this.loginButton.setVisibility(Button.INVISIBLE);
        this.registerLink.setVisibility(TextView.INVISIBLE);
        this.forgottenPassword.setVisibility(TextView.INVISIBLE);
    }


}

