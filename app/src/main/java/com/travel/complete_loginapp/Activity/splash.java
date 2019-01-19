package com.travel.complete_loginapp.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.travel.complete_loginapp.R;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {




                Intent i = new Intent(splash.this,IntroTutorialActivity.class);
                startActivity(i);
                finish();

            }
        },3000);
    }
}
