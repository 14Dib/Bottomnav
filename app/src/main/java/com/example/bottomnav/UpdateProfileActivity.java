package com.example.bottomnav;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class UpdateProfileActivity extends AppCompatActivity {

    TextView btn_update, btn_back, btn_send , btn_changepass;

    EditText txtName, txtPhone, txtEmail;

    ImageView imguser;

    FirebaseAuth fAuth;

    FirebaseFirestore fstore;

    FirebaseUser user;
    String userId;

    StorageReference storageReference;

    public static final String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        btn_back = (TextView) findViewById(R.id.back);

        btn_update = (TextView) findViewById(R.id.update_profile);

        btn_send = (TextView) findViewById(R.id.send_code);

        btn_changepass = (TextView) findViewById(R.id.change_password);

        imguser = (ImageView) findViewById(R.id.imguser);

        txtName = (EditText) findViewById(R.id.username);

        txtEmail = (EditText) findViewById(R.id.email);

        txtPhone = (EditText) findViewById(R.id.phone);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        if(!(user.isEmailVerified())){
            btn_send.setVisibility(View.VISIBLE);
            btn_send.setText("Your Email is not verrify yet");
            btn_send.setBackgroundResource(R.drawable.round_button);
            btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseUser user2 = fAuth.getCurrentUser();
                    user2.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(UpdateProfileActivity.this, "Đã gửi xác thực email", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "OnFailure: Email not send " + e.getMessage());
                        }
                    });
                }
            });
        }

        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid() +"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imguser);
            }
        });

        userId = fAuth.getCurrentUser().getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("users").document(userId);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    txtEmail.setText(documentSnapshot.getString("email"));
                    txtName.setText(documentSnapshot.getString("fname"));
                    txtPhone.setText(documentSnapshot.getString("phone"));
                }
                else{
                    Toast.makeText(UpdateProfileActivity.this, "lỗi" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        imguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryintent,1000);
            }
        });


        btn_changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetPass = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Change Password ?");
                passwordResetDialog.setMessage("Nhập Password mới nhiều hơn 6 ký tự.");
                passwordResetDialog.setView(resetPass);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String passWord = resetPass.getText().toString().trim();
                        user.updatePassword(passWord).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(UpdateProfileActivity.this, "Đổi password thành công", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateProfileActivity.this, "Đổi password thất bại" + e.getMessage(), Toast.LENGTH_SHORT).show();
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

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String phone = txtPhone.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(name.isEmpty() || name.length() < 3){
                    txtName.setError("Tên quá ngắn hoặc không có tên");
                    return;
                }

                if(email.isEmpty() || !(email.matches(emailPattern))){
                    txtEmail.setError("Lỗi email");
                    return;
                }

                if(phone.isEmpty()){
                    txtPhone.setError("Lỗi số điện thoại");
                    return;
                }



                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DocumentReference docRef = fstore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email", email);
                        edited.put("fname", name);
                        edited.put("phone",phone);
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(UpdateProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }
                        });
//                        Toast.makeText(UpdateProfileActivity.this, "Email is Changed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfileActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Fail" + e.getMessage());
                    }
                });
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
//                imguser.setImageURI(imageUri);
                uploadImagetoFirebase(imageUri);
            }
        }
    }

    private void uploadImagetoFirebase(Uri imageUri) {
        StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid() +"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(UpdateProfileActivity.this, "Update thành công" , Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imguser);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateProfileActivity.this, "Update thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }

}