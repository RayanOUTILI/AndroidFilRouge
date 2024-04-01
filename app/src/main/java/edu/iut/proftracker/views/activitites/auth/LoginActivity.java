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

/** Classe LoginActivity permettant de gérer l'activité de connexion à l'application
 *  
 * @see androidx.appcompat.app.AppCompatActivity
 */
public class LoginActivity extends AppCompatActivity {

    private Intent registerIntent, mainIntent, forgottenPasswordIntent;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private Button loginButton;
    private EditText usernameTextField, passwordTextField;
    private TextView title, registerLink, forgottenPassword;
    private ProgressBar progressBar;

    /** Méthode onCreate permettant de créer l'activité de connexion à l'application
     * 
     * @param savedInstanceState : Instance de l'activité
     * 
     * @see android.os.Bundle
     * @see android.content.Intent
     * @see com.google.firebase.auth.FirebaseAuth
     * @see com.google.firebase.firestore.FirebaseFirestore
     * @see android.widget.Button
     * @see android.widget.EditText
     * @see android.widget.TextView
     * @see android.widget.ProgressBar
     * @see android.content.Intent
     * @see java.lang.String
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Appel du constructeur de la super classe
        super.onCreate(savedInstanceState);

        // Association de l'activité à son layout
        setContentView(R.layout.activity_login);

        // Initialisation de l'instance de FirebaseAuth et de FirebaseFirestore
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
    
        // Récupération des éléments du layout
        this.loginButton = findViewById(R.id.loginButton);
        this.usernameTextField = findViewById(R.id.usernameInputField);
        this.passwordTextField = findViewById(R.id.passwordInputField);

        this.title = findViewById(R.id.login);
        this.registerLink = findViewById(R.id.noAccount);
        this.forgottenPassword = findViewById(R.id.forgottenPassword);

        this.progressBar = findViewById(R.id.progressBar);

        // Association des actions aux éléments du layout
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

    /** Méthode permettant de connecter un utilisateur à l'application
     * 
     * <p> A partir du nom d'utilisateur on verifie si l'utilisateur existe dans la base de données Firestore </p>
     * <p> Si l'utilisateur existe, on récupère son email et on tente de le connecter à l'application </p>
     * 
     * @param username : Nom d'utilisateur
     * @param password : Mot de passe
     * 
     * @see com.google.firebase.auth.FirebaseAuth
     * @see com.google.firebase.firestore.FirebaseFirestore
     * @see com.google.firebase.firestore.QueryDocumentSnapshot
     * @see com.google.firebase.auth.FirebaseAuth#signInWithEmailAndPassword(String, String)
     * @see android.content.Intent
     * @see edu.iut.proftracker.views.activitites.MainActivity
     * 
     */
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

    /** Méthode permettant de connecter un utilisateur à l'application à partir de son email et de son mot de passe
     * 
     * <p> Après avoir récupéré l'email depuis la méthode login, on tente de connecter l'utilisateur à l'application </p>
     * 
     * <p> Si la connexion est réussie, on redirige l'utilisateur vers l'activité principale de l'application </p>
     * <p> Sinon, on affiche un message d'erreur </p>
     * @param email : Email
     * @param password : Mot de passe
     * 
     * @see com.google.firebase.auth.FirebaseAuth
     * @see com.google.firebase.auth.FirebaseAuth#signInWithEmailAndPassword(String, String)
     * @see android.content.Intent
     * @see edu.iut.proftracker.views.activitites.MainActivity
     * 
     */

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

    /** Méthode permettant de gérer les erreurs de champs
     * 
     * <p> En fonction du type de champ, on affiche un message d'erreur </p>
     * 
     * @param textField : Champ de texte
     * 
     * @see android.widget.EditText
     *  
     */
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

    /** Méthode permettant de gérer les erreurs de connexion
     *  
     */
    public void loginError() {
       Drawable textFieldBackground = findViewById(R.id.usernameInputField).getBackground();
       Drawable passwordFieldBackground = findViewById(R.id.passwordInputField).getBackground();
       if(textFieldBackground instanceof  ShapeDrawable && passwordFieldBackground instanceof  ShapeDrawable) {
           ((ShapeDrawable) textFieldBackground).getPaint().setColor(getResources().getColor(R.color.red, getTheme()));
            ((ShapeDrawable) passwordFieldBackground).getPaint().setColor(getResources().getColor(R.color.red, getTheme()));
       }
    }

    /** Méthode jouant l'animation de transition entre l'écran de connexion et l'écran principal de l'application
     * 
     * <p> On récupère les éléments du layout et on leur associe une animation </p>
     * <p> On rend les éléments visibles </p>
     * <p> On lance l'animation </p>
     * 
     * @see android.widget.ImageView
     * @see android.widget.ProgressBar
     * @see android.view.animation.Animation
     * @see android.view.animation.AnimationUtils
     */
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

    /** Méthode permettant de rendre invisible tous les éléments du layout
     *  <p> On rend invisible les éléments du layout visibles au moment de la connexion dans le but de faire jouer l'animation apres </p>
     * 
     * @see android.widget.TextView
     * @see android.widget.EditText
     * @see android.widget.Button
     */
    private void clearAllViews() {
        this.title.setVisibility(TextView.INVISIBLE);
        this.usernameTextField.setVisibility(EditText.INVISIBLE);
        this.passwordTextField.setVisibility(EditText.INVISIBLE);
        this.loginButton.setVisibility(Button.INVISIBLE);
        this.registerLink.setVisibility(TextView.INVISIBLE);
        this.forgottenPassword.setVisibility(TextView.INVISIBLE);
    }


}

