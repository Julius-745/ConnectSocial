package com.julius745.connect.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.julius745.connect.R;
import com.julius745.connect.data.Sender;

public class RegisterActivity extends AppCompatActivity {
    String urlAddress="https://connectsocial.domcloud.io/register.php";

    EditText usernameTxt, passwordTxt;
    Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameTxt = (EditText) findViewById(R.id.nameInput);
        passwordTxt = (EditText) findViewById(R.id.passwordInput);
        saveBtn = (Button) findViewById(R.id.btn_signup);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sender s = new Sender(RegisterActivity.this,urlAddress,usernameTxt,passwordTxt);
                s.execute();
            }
        });
    }
}