package com.travel.complete_loginapp.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.travel.complete_loginapp.R;

public class changePasswordActivity extends AppCompatActivity {

    private EditText pass1,pass2;
    private Button submit;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        pass1 = (EditText) findViewById(R.id.p1);
        pass2 = (EditText) findViewById(R.id.p2);
        submit = (Button) findViewById(R.id.Submit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.INVISIBLE);

        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                submit.setVisibility(View.INVISIBLE);
                if (user != null && pass1.getText().toString().equals(pass2.getText().toString()) && !pass1.getText().toString().trim().equals("")) {
//                    displayMessage("Password cannot be empty or don't match Confirm Password.");
                    if (pass1.getText().toString().trim().length() < 6) {
                        pass1.setError("Password too short, enter minimum 6 characters");
                        progressBar.setVisibility(View.GONE);
                        submit.setVisibility(View.VISIBLE);
                    } else {
                        user.updatePassword(pass1.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(changePasswordActivity.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                            auth.signOut();
                                            progressBar.setVisibility(View.GONE);
                                            submit.setVisibility(View.VISIBLE);
                                            Intent i = new Intent(changePasswordActivity.this,LoginActivity.class);
                                            startActivity(i);
                                            finish();
                                            return;
                                        } else {
                                            Toast.makeText(changePasswordActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                            submit.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });
                    }
                } else if (pass1.getText().toString().trim().equals("")) {
                    pass1.setError("Enter password");
//                    displayMessage("Empty Password or Password Don't Match");
                    progressBar.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                }
                else if(!pass1.getText().toString().equals(pass2.getText().toString())){
                    pass1.setError("Passwords Don't Match");
                    progressBar.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                }
            }

            private void displayMessage(String s) {
                Toast.makeText(changePasswordActivity.this,s,Toast.LENGTH_SHORT).show();

            }

        });

    }
}
