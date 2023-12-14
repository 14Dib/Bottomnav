package com.example.bottomnav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailHistoryActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    TextView txtDate, txtTime, txtPrice, txtQuantity, txt_back;
    ListView listview_cart_history;
    DetailHistoryAdapter detailHistoryAdapter;
    List<CartModel> cart_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        fAuth = FirebaseAuth.getInstance();
        String userId = fAuth.getCurrentUser().getUid();

        txtDate = (TextView) findViewById(R.id.date_total_history);
        txtTime = (TextView) findViewById(R.id.time_total_history);
        txtPrice = (TextView) findViewById(R.id.price_total_history);
        txtQuantity = (TextView) findViewById(R.id.quantity_total_history);
        listview_cart_history = (ListView) findViewById(R.id.listview_history_cart);

        txt_back = (TextView) findViewById(R.id.back_to_history);

        Bundle bundle = getIntent().getExtras();
        String key = bundle.getString("key");
        String date = bundle.getString("date");
        String time = bundle.getString("time");
        String price = bundle.getString("price");
        String quantity = bundle.getString("quantity");

        txtDate.setText(date);
        txtTime.setText(time);
        txtPrice.setText(price + "00 VND");
        txtQuantity.setText(quantity);

        cart_list = new ArrayList<>();

        detailHistoryAdapter = new DetailHistoryAdapter(DetailHistoryActivity.this, R.layout.layout_history_cart,cart_list);

        listview_cart_history.setAdapter(detailHistoryAdapter);

        DatabaseReference userHistory = FirebaseDatabase
                .getInstance()
                .getReference("Checkout")
                .child(userId)
                .child(key)
                .child("cart_list");

        userHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cart_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot dataCart : snapshot.getChildren()){
                        System.out.println("KEY CART HERE: " + dataCart.getKey());
                        System.out.println("VALUE CART HERE: " + dataCart.getValue());
                        CartModel cartModel = dataCart.getValue(CartModel.class);
                        cartModel.setKey(dataCart.getKey());
                        cart_list.add(cartModel);
                    }
                }
                detailHistoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                finish();

            }
        });

    }
}