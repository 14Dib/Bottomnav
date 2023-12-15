package com.example.bottomnav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    ListView listview_history;
    FirebaseAuth fAuth;
    HistoryAdapter historyAdapter;
    List<CheckoutModel> checkout_list;
    TextView txt_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        txt_back = (TextView) findViewById(R.id.back_to_main);
        listview_history = (ListView) findViewById(R.id.listview_history);

        checkout_list = new ArrayList<>();

        historyAdapter = new HistoryAdapter(HistoryActivity.this, R.layout.layout_history,checkout_list);

        listview_history.setAdapter(historyAdapter);

        fAuth = FirebaseAuth.getInstance();
        String userId = fAuth.getCurrentUser().getUid();

        DatabaseReference userCheckout = FirebaseDatabase.getInstance().getReference("Checkout").child(userId);

        userCheckout.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                checkout_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot checkoutSnapshot: snapshot.getChildren()){
                        CheckoutModel checkoutModel = new CheckoutModel();

                        for(DataSnapshot checkoutChild : checkoutSnapshot.getChildren()){

                            if(checkoutChild.getKey().equals("name")){
                                checkoutModel.setName(checkoutChild.getValue().toString());
                            }

                            if(checkoutChild.getKey().equals("date")){
                                checkoutModel.setDate(checkoutChild.getValue().toString());
                            }

                            if(checkoutChild.getKey().equals("time")){
                                checkoutModel.setTime(checkoutChild.getValue().toString());
                            }

                            if(checkoutChild.getKey().equals("totalPrice")){
                                String getString = checkoutChild.getValue().toString();
                                checkoutModel.setTotalPrice(Float.parseFloat(getString));
                            }

                            if(checkoutChild.getKey().equals("totalquantity")){
                                String getString = checkoutChild.getValue().toString();
                                checkoutModel.setTotalquantity(Integer.parseInt(getString));
                            }

                            if(checkoutChild.getKey().equals("user_Name")){
                                checkoutModel.setUser_Name(checkoutChild.getValue().toString());
                            }

                            if(checkoutChild.getKey().equals("user_Phone")){
                                checkoutModel.setUser_Phone(checkoutChild.getValue().toString());
                            }

                            if(checkoutChild.getKey().equals("user_Address")){
                                checkoutModel.setUser_Address(checkoutChild.getValue().toString());
                            }

                            if(checkoutChild.getKey().equals("cart_list")){
                                checkoutModel.setCart_list(null);
                            }
                        }
                        checkout_list.add(checkoutModel);
                    }
                    historyAdapter.notifyDataSetChanged();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        listview_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HistoryActivity.this, DetailHistoryActivity.class);
                CheckoutModel checkoutModel = checkout_list.get(i);
                Bundle bundle = new Bundle();
                bundle.putString("key" , String.valueOf(i +1));
                bundle.putString("date" , checkoutModel.getDate());
                bundle.putString("time" , checkoutModel.getTime());
                bundle.putString("price" , String.valueOf(checkoutModel.getTotalPrice()));
                bundle.putString("quantity" , String.valueOf(checkoutModel.getTotalquantity()));
                bundle.putString("user_Name" , checkoutModel.getUser_Name());
                bundle.putString("user_Phone" , checkoutModel.getUser_Phone());
                bundle.putString("user_Address" , checkoutModel.getUser_Address());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}