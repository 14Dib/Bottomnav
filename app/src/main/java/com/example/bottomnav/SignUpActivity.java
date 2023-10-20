package com.example.bottomnav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText edtusername, edtpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtusername = (TextInputEditText) findViewById(R.id.username3);
        edtpass = (TextInputEditText) findViewById(R.id.password3);

        findViewById(R.id.txtSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(SignUpActivity.this, LoginActivity.class);
                intent.putExtra("username", edtusername.getText());
                intent.putExtra("pass", edtpass.getText());
//                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                startActivity(intent);
            }
        });

        findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });
    }
}