package com.example.bottomnav;

import android.content.DialogInterface;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends BaseAdapter {
    FirebaseAuth fAuth;
    private Fragment context;
    private int layout;
    private List<CartModel> cart_list;

    public CartAdapter(Fragment context, int layout, List<CartModel> cart_list) {
        this.context = context;
        this.layout = layout;
        this.cart_list = cart_list;
    }

    @Override
    public int getCount() {
        return cart_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View converView, ViewGroup viewGroup) {


        LayoutInflater inflater = (LayoutInflater) context.getLayoutInflater();

        converView = inflater.inflate(layout, null);

        CartModel cartModel = cart_list.get(i);

        //anhxa
        TextView txtname = converView.findViewById(R.id.cart_name);
        TextView txtdes = converView.findViewById(R.id.cart_des);
        TextView txtmonney = converView.findViewById(R.id.cart_price);
        TextView btn_increase = converView.findViewById(R.id.txt_increase);
        TextView btn_decrease = converView.findViewById(R.id.txt_Decrease);
        ImageView btn_delete = converView.findViewById(R.id.btn_delete);
        EditText edt_quantity = converView.findViewById(R.id.cart_quantity);


        ImageView imageV = converView.findViewById(R.id.cart_img);

        String price = cartModel.getPrice();
        int quantity = cartModel.getQuantity();

        txtname.setText(cartModel.getName());
        txtdes.setText("(Sốt Sa Tế/Bơ Tỏi)");
        txtmonney.setText(price + ".000 VND");
        edt_quantity.setText("" + quantity);
        edt_quantity.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "20")});
        Glide.with(context).load(cartModel.getImage()).into(imageV);

        btn_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartModel.setQuantity(cartModel.getQuantity() + 1);
                cartModel.setTotalPrice(cartModel.getQuantity()*Float.parseFloat(cartModel.getPrice()));
                edt_quantity.setText("" + cartModel.getQuantity());
                updateCart(cart_list.get(i));
            }
        });

        btn_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartModel.getQuantity() > 1){
                    cartModel.setQuantity(cartModel.getQuantity() - 1);
                    cartModel.setTotalPrice(cartModel.getQuantity()*Float.parseFloat(cartModel.getPrice()));
                    edt_quantity.setText("" + cartModel.getQuantity());
                    updateCart(cart_list.get(i));
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                                    .setTitle("Delete item")
                                    .setMessage("Bạn có muốn xóa sản phẩm khỏi giỏ hàng không?")
                                    .setNegativeButton("CANCEL", (dialogInterface, i1) -> {
                                        dialogInterface.dismiss();
                                    })
                                    .setPositiveButton("OK", (dialogInterface, i12) -> {
                                        deleteItemCart(cart_list.get(i));
                                        notifyDataSetChanged();
                                        dialogInterface.dismiss();
                                    }).create();
                dialog.show();
            }
        });


        return converView;
    }

    private void deleteItemCart(CartModel cartModel) {
        fAuth = FirebaseAuth.getInstance();
        String userId = fAuth.getCurrentUser().getUid();
        FirebaseDatabase.getInstance()
                .getReference("Cart")
                .child(userId)
                .child(cartModel.getKey())
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void updateCart(CartModel cartModel) {
        fAuth = FirebaseAuth.getInstance();
        String userId = fAuth.getCurrentUser().getUid();
        FirebaseDatabase.getInstance()
                        .getReference("Cart")
                        .child(userId)
                        .child(cartModel.getKey())
                        .setValue(cartModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}
