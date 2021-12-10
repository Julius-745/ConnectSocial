package com.julius745.connect.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.julius745.connect.R;
import com.julius745.connect.data.BackendService;
import com.julius745.connect.view.AboutActivity;
import com.julius745.connect.view.AccountActivity;
import com.julius745.connect.view.EditProfileActivity;
import com.julius745.connect.view.LoginActivity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {
    Map userData;
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        TextView name = v.findViewById(R.id.tv_name);
        Button aboutBtn = v.findViewById(R.id.btn_about);
        Button logoutBtn = v.findViewById(R.id.btn_logout);
        Button editBtn = v.findViewById(R.id.btn_account);

        name.setText("...");

        aboutBtn.setOnClickListener(view -> {
            startActivity(new Intent(v.getContext(), AboutActivity.class));
        });

        logoutBtn.setOnClickListener(view -> {
            startActivity(new Intent(this.getContext(), LoginActivity.class));
            // biar diklik back tidak kesini lagi
            getActivity().finish();
        });

        editBtn.setOnClickListener(view -> {
            // ke EditProfileActivity + kirim data user
            Intent intent = new Intent(this.getContext(), EditProfileActivity.class);
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
                    Toast.makeText(v.getContext(), response.raw().body().toString(), Toast.LENGTH_LONG).show();
                    name.setText("(error)");
                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                name.setText("(error)");
            }
        });
        return v;
    }


}