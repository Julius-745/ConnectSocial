package com.julius745.connect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_LENGTH = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // title bar nya dihabis
        getSupportActionBar().hide();

        // delay 5 detik (SPLASH_LENGTH)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // ambil mainactivity
                Intent main = new Intent(SplashScreenActivity.this,LoginActivity.class);
                // load mainactivity (bagian e julius)
                SplashScreenActivity.this.startActivity(main);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_LENGTH);    }
}