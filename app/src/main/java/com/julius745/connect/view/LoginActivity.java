package com.julius745.connect.view;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.julius745.connect.MainActivity;
import com.julius745.connect.R;
import com.julius745.connect.SplashScreenActivity;
import com.julius745.connect.data.BackendService;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailTxt = findViewById(R.id.emailInput);
        EditText passwordTxt = findViewById(R.id.passwordInput);
        Button signIn = findViewById(R.id.btn_login);
        Button signUp = findViewById(R.id.btn_signup);

        //  Membuka registrasi activity
        signUp.setOnClickListener(view -> {
            Intent main = new Intent(LoginActivity.this, RegisterActivity.class);
            LoginActivity.this.startActivity(main);
        });

        signIn.setOnClickListener(view -> {
            // Program ini untuk mengirim email dan password dan dibungkus oleh Call -> kepada BackendInterface (login PHP)
            Call<Map> obj = BackendService.service.login(
                    emailTxt.getText().toString(),
                    passwordTxt.getText().toString());
            // Jika respon sudah terkirim pada PHP response
            obj.enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            // Jika respon gagal biasanya terjadi karena tidak terdapat koneksi
                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

    };
}