package com.example.bottomnav;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextView txtSignin,txtSignup;
    TextInputEditText edtusername, edtpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtSignin = (TextView) findViewById(R.id.txtSignIn);
        txtSignup = (TextView) findViewById(R.id.tv_signup);
        edtusername = (TextInputEditText) findViewById(R.id.username2);
        edtpass = (TextInputEditText) findViewById(R.id.password2);

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user,password;
                user = edtusername.getText().toString();
                password = edtpass.getText().toString();

                Intent intent = getIntent();
//                if(intent != null){
//                    String user_signup = intent.getStringExtra("username");
//                    String pass_signup = intent.getStringExtra("pass");
//                    if(user.equals(user_signup) && password.equals(pass_signup)){
//                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                    } else if (user.equals("") && password.equals("")) {
//                        Toast.makeText(LoginActivity.this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
//                    }
//                }

                if(user.equals("admin") && password.equals("admin")){
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                } else if (user.equals("") && password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}