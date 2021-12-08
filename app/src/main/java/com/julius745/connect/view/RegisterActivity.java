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
import com.julius745.connect.data.BackendService;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText emailTxt = findViewById(R.id.emailInput);
        EditText nameTxt = findViewById(R.id.nameInput);
        EditText passwordTxt = findViewById(R.id.passwordInput);
        Button saveBtn = findViewById(R.id.btn_signup);

        saveBtn.setOnClickListener(view -> {
           Call<Map> obj = BackendService.service.register(
                    nameTxt.getText().toString(),
                    emailTxt.getText().toString(),
                    passwordTxt.getText().toString());

           obj.enqueue(new Callback<Map>() {
               @Override
               public void onResponse(Call<Map> call, Response<Map> response) {
                   if (response.isSuccessful()) {
                       startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                       finish();
                   } else {
                       Toast.makeText(RegisterActivity.this, response.raw().body().toString(), Toast.LENGTH_LONG).show();
                   }
               }

               @Override
               public void onFailure(Call<Map> call, Throwable t) {
                   Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
               }
           });
        });
    }
}