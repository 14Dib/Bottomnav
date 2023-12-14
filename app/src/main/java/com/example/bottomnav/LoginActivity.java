package com.example.bottomnav;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView txtSignin,txtSignup, txtForget;
    TextInputEditText edtemail, edtpass;

    FirebaseAuth fAuth;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtSignin = (TextView) findViewById(R.id.txtSignIn);
        txtSignup = (TextView) findViewById(R.id.tv_signup);
        txtForget = (TextView) findViewById(R.id.forget_pass);
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

        txtForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Nhập email để reset password.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = resetMail.getText().toString().trim();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(LoginActivity.this, "Đã gửi link reset", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error reset link" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                passwordResetDialog.create().show();
            }
        });


        txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edtemail.getText().toString().trim();
                String password = edtpass.getText().toString().trim();


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