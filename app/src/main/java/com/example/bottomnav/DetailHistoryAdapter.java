package com.example.bottomnav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class DetailHistoryAdapter extends BaseAdapter {
    FirebaseAuth fAuth;
    private Context context;
    private int layout;
    private List<CartModel> cart_list;
    public DetailHistoryAdapter(Context context, int layout, List<CartModel> cart_list) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);

        CartModel cartModel = cart_list.get(i);

        TextView txtname = view.findViewById(R.id.cart_history_name);
        TextView txtdes = view.findViewById(R.id.cart_history_des);
        TextView txtprice = view.findViewById(R.id.cart_history_price);
        TextView txtquantity = view.findViewById(R.id.cart_history_quantity);
        ImageView imageV = view.findViewById(R.id.cart_history_img);

        txtname.setText(cartModel.getName());
        txtdes.setText("(Sốt Sa Tế/Bơ Tỏi)");
        txtprice.setText(String.valueOf(cartModel.getTotalPrice()) +"00 VND");
        txtquantity.setText("SL : " + cartModel.getQuantity());
        Glide.with(context).load(cartModel.getImage()).into(imageV);

        return view;
    }
}
