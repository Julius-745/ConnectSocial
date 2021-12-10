package com.julius745.connect.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.julius745.connect.MainActivity;
import com.julius745.connect.R;
import com.julius745.connect.data.BackendService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    Map userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        TextView name = findViewById(R.id.tv_name);
        Button aboutBtn = findViewById(R.id.btn_about);
        Button logoutBtn = findViewById(R.id.btn_logout);
        Button editBtn = findViewById(R.id.btn_account);

        name.setText("...");

        aboutBtn.setOnClickListener(view -> {
            startActivity(new Intent(AccountActivity.this, AboutActivity.class));
        });

        logoutBtn.setOnClickListener(view -> {
            startActivity(new Intent(AccountActivity.this, LoginActivity.class));
            // biar diklik back tidak kesini lagi
            finish();
        });

        editBtn.setOnClickListener(view -> {
            // ke EditProfileActivity + kirim data user
            Intent intent = new Intent(AccountActivity.this, EditProfileActivity.class);
            intent.putExtra("name", userData.get("name").toString());
            intent.putExtra("email", userData.get("email").toString());
            // password tidak perlu soalnya kalau tidak mau diganti ya dikosongi saja.
            startActivity(intent);
        });

        // load data user yang login dari server untuk mengambil nama pada profil
        Call<Map> obj = BackendService.service.userGet();

        obj.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if (response.isSuccessful()) {
                    userData = response.body(); //data disimpan terlebih dahulu untuk baris 49
                    name.setText(userData.get("name").toString());
                } else {
                    Toast.makeText(AccountActivity.this, response.raw().body().toString(), Toast.LENGTH_LONG).show();
                    name.setText("(error)");
                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                Toast.makeText(AccountActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                name.setText("(error)");
            }
        });
    }
}