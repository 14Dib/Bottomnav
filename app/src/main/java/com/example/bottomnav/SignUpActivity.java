package com.example.bottomnav;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    TextView btnSignup;
    TextInputEditText edtname, edtpass, edtphone, edtemail, editconfirmpass;

    FirebaseAuth fAuth;

    FirebaseFirestore fstore;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String userID;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //EDITTEXT
        edtname = (TextInputEditText) findViewById(R.id.username3);
        edtpass = (TextInputEditText) findViewById(R.id.password_4);
        edtphone = (TextInputEditText) findViewById(R.id.phone2);
        edtemail = (TextInputEditText) findViewById(R.id.email2);
        editconfirmpass = (TextInputEditText) findViewById(R.id.confirmpass_4);

        //BUTTON

        btnSignup = (TextView) findViewById(R.id.txtSignUp);

        //AUTH
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        //PROGRESS
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtemail.getText().toString().trim();
                String password = edtpass.getText().toString().trim();
                String confirmpassword = editconfirmpass.getText().toString().trim();
                String phone = edtphone.getText().toString().trim();
                String name = edtname.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(TextUtils.isEmpty(email)){
                    edtemail.setError("Yêu cầu email");
                    return;
                }

                if(!(email.matches(emailPattern))){
                    edtemail.setError("Emai không hợp lệ");
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

                if(confirmpassword.equals(password) == false){
                    editconfirmpass.setError("Mật khẩu không trùng nhau");
                    return;
                }

                if(TextUtils.isEmpty(phone)){
                    edtphone.setError("Yêu cầu số điện thoại");
                    return;
                }


                if(TextUtils.isEmpty(name)){
                    edtname.setError("Yêu cầu tên");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user2 = fAuth.getCurrentUser();
                            user2.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(SignUpActivity.this, "Đã gửi xác thực email", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "OnFailure: Email not send " + e.getMessage());
                                }
                            });

                            Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fname", name);
                            user.put("email", email);
                            user.put("phone", phone);
//                            user.put("avatar", "");
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSucces: userProfile is created for " + userID);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e(TAG, "onFailure: ", e);
                                        }
                                    });

//                            db.collection("users")
//                                    .add(user)
//                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                        @Override
//                                        public void onSuccess(DocumentReference documentReference) {
//                                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.w(TAG, "Error adding document", e);
//                                        }
//                                    });

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finishAffinity();
                        }else{
                            Toast.makeText(SignUpActivity.this, "Lỗi " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}