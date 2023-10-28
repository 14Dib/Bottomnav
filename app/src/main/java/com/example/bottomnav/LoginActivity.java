package com.example.bottomnav;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView txtSignin,txtSignup;
    TextInputEditText edtemail, edtpass;

    FirebaseAuth fAuth;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtSignin = (TextView) findViewById(R.id.txtSignIn);
        txtSignup = (TextView) findViewById(R.id.tv_signup);
        edtemail = (TextInputEditText) findViewById(R.id.username2);
        edtpass = (TextInputEditText) findViewById(R.id.password2);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        fAuth = FirebaseAuth.getInstance();

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

                String email = edtemail.getText().toString().trim();
                String password = edtpass.getText().toString().trim();
//                String password = edtpass.getText().toString().trim();
//                String password = edtpass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    edtemail.setError("Yêu cầu email");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    edtpass.setError("Yêu cầu password");
                    return;
                }

                if(password.length() < 6){
                    edtpass.setError("Mật khẩu phải nhiều hơn 6 ký tự");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }else{
                            Toast.makeText(LoginActivity.this, "Lỗi " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}