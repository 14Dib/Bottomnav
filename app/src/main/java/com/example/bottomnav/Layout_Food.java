package com.example.bottomnav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Layout_Food extends AppCompatActivity {

    TextView txtname , txtback, txtprice, txtdes, txtadd;

    ImageView imgView;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_food);

        txtname = (TextView) findViewById(R.id.txtName);
        txtback = (TextView) findViewById(R.id.txtBack);
        txtprice = (TextView) findViewById(R.id.monney_food);
        txtdes = (TextView) findViewById(R.id.txtDes);
        txtadd = (TextView) findViewById(R.id.txtadd_cart);
        imgView = (ImageView) findViewById(R.id.imageView2);

        Bundle bundle = getIntent().getExtras();
        String key = bundle.getString("key");
        String name = bundle.getString("name");
        String image = bundle.getString("image");
        String price = bundle.getString("price");
        String description = bundle.getString("description");
        Glide.with(getApplication()).load(image).into(imgView);
        txtname.setText(name);
        txtprice.setText(price + ".000 VND");
        txtdes.setText(description);

        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        txtadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth = FirebaseAuth.getInstance();
                String userId = fAuth.getCurrentUser().getUid();
                DatabaseReference userCart = FirebaseDatabase
                        .getInstance()
                        .getReference("Cart")
                        .child(userId);

                userCart.child(key)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    CartModel cartModel = snapshot.getValue(CartModel.class);
                                    cartModel.setQuantity(cartModel.getQuantity()+1);
                                    Map<String,Object> updateData = new HashMap<>();
                                    updateData.put("quantity",cartModel.getQuantity());
                                    updateData.put("totalPrice",cartModel.getQuantity()*Float.parseFloat(cartModel.getPrice()));

                                    userCart.child(key)
                                            .updateChildren(updateData)
                                            .addOnSuccessListener(unused -> {
                                                Toast.makeText(Layout_Food.this, "Add sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Layout_Food.this, "Add sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                                else{
                                    CartModel cartModel = new CartModel();
                                    cartModel.setName(name);
                                    cartModel.setDescription(description);
                                    cartModel.setImage(image);
                                    cartModel.setKey(key);
                                    cartModel.setQuantity(1);
                                    cartModel.setPrice(price);
                                    cartModel.setTotalPrice(Float.parseFloat(price));

                                    userCart.child(key)
                                            .setValue(cartModel)
                                            .addOnSuccessListener(unused -> {
                                                Toast.makeText(Layout_Food.this, "Add sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Layout_Food.this, "Add sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

            }
        });



    }


}