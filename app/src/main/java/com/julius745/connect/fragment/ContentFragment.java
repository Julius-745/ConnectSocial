package com.julius745.connect.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.julius745.connect.R;
import com.julius745.connect.SplashScreenActivity;
import com.julius745.connect.data.BackendService;
import com.julius745.connect.data.ContentAdapter;
import com.julius745.connect.view.AccountActivity;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContentFragment extends Fragment {

    public ContentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_content, container, false);
        RecyclerView rv = v.findViewById(R.id.content_view);
        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(v.getContext());
        rv.setLayoutManager(mLayoutManager);

        if (BackendService.service == null) {
            return v;
        }

        // load data user yang login dari server
        Call<List<Map>> obj = BackendService.service.postList(0);

        obj.enqueue(new Callback<List<Map>>() {
            @Override
            public void onResponse(Call<List<Map>> call, Response<List<Map>> response) {
                if (response.isSuccessful()) {

                    ContentAdapter mAdapter = new ContentAdapter(response.body());
                    // Set CustomAdapter as the adapter for RecyclerView.
                    rv.setAdapter(mAdapter);
                } else {
                    Toast.makeText(v.getContext(), response.raw().body().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Map>> call, Throwable t) {
                Toast.makeText(v.getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        return v;

    }
}