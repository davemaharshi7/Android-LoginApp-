package com.travel.complete_loginapp.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.travel.complete_loginapp.R;

public class LoginActivity extends AppCompatActivity {

    private TextView log;
    private EditText email;
    private EditText pass;
    private Button btn,reset;
    private ProgressBar pgbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pgbar =(ProgressBar) findViewById(R.id.progressBar2);
        pgbar.setVisibility(View.INVISIBLE);


        reset = (Button) findViewById(R.id.btn_reset);
        email = (EditText)findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        btn = (Button) findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
//        if (mAuth.getCurrentUser() != null) {
//            // User is signed in (getCurrentUser() will be null if not signed in)
//            changeActivity();
//        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));

            }
        });
        log = (TextView) findViewById(R.id.reg);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(register);
                finish();
                return;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgbar.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);

                final String log_email = email.getText().toString().trim();
                final String log_pass = pass.getText().toString().trim();

                if(log_email.isEmpty() || log_pass.isEmpty()){
                    showMessage("Please Enter All Fields");
                    pgbar.setVisibility(View.INVISIBLE);
                    btn.setVisibility(View.VISIBLE);
                }else {
                    signIn(log_email,log_pass);
                }
            }

            private void showMessage(String s) {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

            }

            private void signIn(String log_email, String log_pass) {

                mAuth.signInWithEmailAndPassword(log_email,log_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            pgbar.setVisibility(View.INVISIBLE);
                            btn.setVisibility(View.VISIBLE);
                            //check if user is email verified
                            //checkIfEmailVerified();
                            //change activity
                            changeActivity();

                        }else {
                            showMessage("Login Error Occured : " + task.getException().getMessage());
                            pgbar.setVisibility(View.INVISIBLE);
                            btn.setVisibility(View.VISIBLE);
                        }
                    }


                });
            }

            private void checkIfEmailVerified() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user.isEmailVerified())
                {
                    // user is verified, so you can finish this activity or send user to activity which you want.
                    finish();
                    Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // email is not verified, so just prompt the message to the user and restart this activity.
                    Toast.makeText(LoginActivity.this, "Email Not Verified! Please Verify first and then Login...", Toast.LENGTH_LONG).show();

                    // NOTE: don't forget to log out the user.
                    FirebaseAuth.getInstance().signOut();

                    //restart this activity

                }
            }
        });
    }

    private void changeActivity() {
        Intent home = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(home);
        finish();
        return;
    }
}
