package com.julius745.connect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.julius745.connect.data.BackendService;
import com.julius745.connect.view.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // title bar nya dihabis
        getSupportActionBar().hide();
        // init retrofit > sebagai plug in untuk connect ke backend server
        BackendService.initService(getApplicationContext());
        // delay 2 detik (SPLASH_LENGTH)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // ambil mainactivity
                Intent main = new Intent(SplashScreenActivity.this, LoginActivity.class);
                // load login
                SplashScreenActivity.this.startActivity(main);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_LENGTH);    }
}