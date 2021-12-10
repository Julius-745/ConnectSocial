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

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editptofile);

        EditText emailTxt = findViewById(R.id.emailInput);
        EditText nameTxt = findViewById(R.id.nameInput);
        EditText passwordTxt = findViewById(R.id.passwordInput);
        Button saveBtn = findViewById(R.id.btn_send);

        // masukkan data user sekarang ( data user sekarang dari activity sebelumnya > AccountActivity )
        emailTxt.setText(getIntent().getStringExtra("email"));
        nameTxt.setText(getIntent().getStringExtra("name"));

        // Funsgi ini untuk menyimpan data user berupa nama,email dan password
        saveBtn.setOnClickListener(view -> {
            Call<Map> obj = BackendService.service.userEdit(
                    nameTxt.getText().toString(),
                    emailTxt.getText().toString(),
                    passwordTxt.getText().toString(),
                    // lang ini dibiarkan kosong karena tidak dipakai
                    "");

            // Mengirim data ke server dan menunggu hasil ke dalam Callback
            obj.enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    if (response.isSuccessful()) {
                        // kembali ke main activity
                        finishActivity(0);
                    } else {
                        Toast.makeText(EditProfileActivity.this, response.raw().body().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    //jika koneksi gagal
                    Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}