package com.travel.complete_loginapp.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.travel.complete_loginapp.R;

public class RegisterActivity extends AppCompatActivity {

    ImageView regUserPhoto;


    private TextView log;
    private EditText userEmail, userPassword, userPassword2, userName;
    private ProgressBar loadingProgrss;
    private Button regBtn;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        userEmail =(EditText) findViewById(R.id.regMail);
        userName = (EditText) findViewById(R.id.regName);
        userPassword = (EditText) findViewById(R.id.regPassword1);
        userPassword2 = (EditText) findViewById(R.id.regPassword2);
        loadingProgrss = (ProgressBar) findViewById(R.id.progressBar);
        regBtn = (Button) findViewById(R.id.button);
        loadingProgrss.setVisibility(View.INVISIBLE);
        regUserPhoto = findViewById(R.id.regUserImage);

        mAuth = FirebaseAuth.getInstance();
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    // NOTE: this Activity should get onpen only when the user is not signed in, otherwise
//                    // the user will receive another verification email.
//                    sendVerificationEmail();
//                } else {
//                    // User is signed out
//
//                }
//                // ...
//            }
//        };

        log = (TextView) findViewById(R.id.logLink);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(login);
                finish();
                return;
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regBtn.setVisibility(View.INVISIBLE);
                loadingProgrss.setVisibility(View.VISIBLE);

                final String email = userEmail.getText().toString().trim();
                final String password = userPassword.getText().toString().trim();
                final String password2 = userPassword2.getText().toString().trim();
                final String name = userName.getText().toString().trim();

                if(email.isEmpty() || name.isEmpty() || password.isEmpty() || !password.equals
                        (password2) ){

                    //Toast for error Message
                    showMessage("Please Enter All Fields ");
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgrss.setVisibility(View.INVISIBLE);

                }
                else{

                    //All Set GO FOR Authentication FiREBASE
                    CreateUserAccount(email,name,password);

                }
            }
        });



    }
//    private void sendVerificationEmail()
//    {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        user.sendEmailVerification()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            // email sent
//
//
//                            // after email is sent just logout the user and finish this activity
//                            FirebaseAuth.getInstance().signOut();
//                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                            finish();
//                        } else {
//                            // email not sent, so display message and restart the activity or do whatever you wish to do
//
//                            //restart this activity
//                            overridePendingTransition(0, 0);
//                            finish();
//                            overridePendingTransition(0, 0);
//                            startActivity(getIntent());
//
//                        }
//                    }
//                });
//    }

    private void CreateUserAccount(final String email,final String name, final String password) {

        //this method create user account
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Account Created
                            showMessage("Account Created");
                            //After Account Creation we need to update his profile picture
                            Intent home = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(home);
                            finish();
                            return;
                        }
                        else{
                            showMessage("Account Creation Failed :"+task.getException().getMessage());
                            regBtn.setVisibility(View.VISIBLE);
                            loadingProgrss.setVisibility(View.INVISIBLE);

                        }
                    }
                });

    }

    private void showMessage(String message) {
        //TODO: Make generic toast message
        Toast.makeText(getApplicationContext(),message,Toast
                .LENGTH_LONG).show();

    }



}
