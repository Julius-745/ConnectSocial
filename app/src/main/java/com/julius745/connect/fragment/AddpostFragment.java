package com.julius745.connect.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.collection.SimpleArrayMap;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.julius745.connect.MainActivity;
import com.julius745.connect.R;
import com.julius745.connect.data.BackendService;
import com.julius745.connect.view.RegisterActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddpostFragment extends Fragment {

    public AddpostFragment() {
        // Required empty public constructor
    }

    Uri image;

    public void setImage(Uri image) {
        this.image = image;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_addpost, container, false);
        TextInputEditText title = v.findViewById(R.id.titleInput);
        TextInputEditText content = v.findViewById(R.id.contentInput);
        Button btn = v.findViewById(R.id.btn_send);
        ImageView myImage = v.findViewById(R.id.imageView);
        myImage.setImageURI(image);
        btn.setOnClickListener(view -> {
            File file = new File(image.getPath());
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file.getName(), requestFile);
            Call<Map> obj = BackendService.service.postNew(
                    title.getText().toString(),
                    content.getText().toString(),
                    body
            );

            obj.enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(v.getContext(), "Sudah diupload!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(v.getContext(), response.raw().body().toString(), Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        return v;
    }
}