package com.example.bottomnav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class HistoryAdapter extends BaseAdapter {
    FirebaseAuth fAuth;
    private Context context;
    private int layout;
    private List<CheckoutModel> checkout_list;
    public HistoryAdapter(Context context, int layout, List<CheckoutModel> checkout_list) {
        this.context = context;
        this.layout = layout;
        this.checkout_list = checkout_list;
    }

    @Override
    public int getCount() {
        return checkout_list.size();
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

        CheckoutModel checkoutModel = checkout_list.get(i);

        TextView txtname = view.findViewById(R.id.name_checkout);
        TextView txtdate_time = view.findViewById(R.id.date_time_checkout);
        TextView txtprice = view.findViewById(R.id.price_checkout);
        TextView txtquantity = view.findViewById(R.id.quantity_checkout);

        txtname.setText(checkoutModel.getName());
        txtdate_time.setText(checkoutModel.getDate() + " - " + checkoutModel.getTime());
        txtprice.setText(String.valueOf(checkoutModel.getTotalPrice()) +"00 VND");
        txtquantity.setText(checkoutModel.getTotalquantity() + " món");

        return view;
    }
}
