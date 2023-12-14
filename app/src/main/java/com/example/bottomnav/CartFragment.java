package com.example.bottomnav;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ListView listview_cart;
    TextView txtTotal_money, txt_checkout;
    CartAdapter adapter_cart;

    ArrayList<CartModel> list_carts;

    FirebaseAuth fAuth;

    long idCheck = 1;

    Float total_checkout = (float) 0;
    String all_name = "";

    int all_quantity = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        listview_cart = (ListView) view.findViewById(R.id.listview_cart);
        txtTotal_money = (TextView) view.findViewById(R.id.txtTotal_all_money);
        txt_checkout = (TextView) view.findViewById(R.id.check_out);

        fAuth = FirebaseAuth.getInstance();
        String userId = fAuth.getCurrentUser().getUid();

        list_carts = new ArrayList<>();

        adapter_cart = new CartAdapter(CartFragment.this,R.layout.layout_cart,list_carts);
        listview_cart.setAdapter(adapter_cart);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cart").child(userId);

        DatabaseReference userCheckout = FirebaseDatabase
                .getInstance()
                .getReference("Checkout")
                .child(userId);

        userCheckout.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                idCheck = snapshot.getChildrenCount() + 1;
                System.out.println("COUNT KEY IDCHECK: " + idCheck);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_carts.clear();

                if(snapshot.exists()){
                    Float total_price = (float) 0;
                    int total_quantity = 0;
                    String total_name = "";
                    for(DataSnapshot cartSnapshot: snapshot.getChildren()){
                        CartModel cartModel = cartSnapshot.getValue(CartModel.class);
                        cartModel.setKey(cartSnapshot.getKey());
                        list_carts.add(cartModel);
                        total_price = total_price + cartModel.getTotalPrice();
                        total_name = total_name  + cartModel.getName() + ", ";

                        total_quantity = total_quantity + cartModel.getQuantity();
                    }
                    all_name = total_name;
                    all_quantity = total_quantity;
                    total_checkout = total_price;
                    txtTotal_money.setText("" + total_price+"00 VND");
                }
                else {
                    total_checkout = (float) 0;
                    txtTotal_money.setText("0.000 VND");
                }
                adapter_cart.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        System.out.println("CHECK ERROR: " + all_quantity);
        txt_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(list_carts.isEmpty()){
                        Toast.makeText(getContext(), "Không có sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
                    }

                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

                    CheckoutModel checkoutModel = new
                            CheckoutModel(all_name,currentDate,currentTime,all_quantity,total_checkout,list_carts);


                    userCheckout.child("" + idCheck)
                            .setValue(checkoutModel)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                    System.out.println("CHECK ERROR: " + all_quantity);
                    total_checkout = (float) 0;
                    all_name = "";
                    all_quantity = 0;
                    reference.removeValue();
                    list_carts.clear();
                    adapter_cart.notifyDataSetChanged();
                }

        });

        return view;
    }
}