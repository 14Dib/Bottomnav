package com.example.bottomnav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter_Test extends BaseAdapter {
    private Context context;
    private int layout;
    private List<List_Food> arraylist;

    public Adapter_Test(Context context, int layout, List<List_Food> arraylist) {
        this.context = context;
        this.layout = layout;
        this.arraylist = arraylist;
    }

    @Override
    public int getCount() {
        return arraylist.size();
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        LayoutInflater inflater = (LayoutInflater) context.getLayoutInflater();

        converView = inflater.inflate(layout,null);

        List_Food list_food = arraylist.get(i);

        //anhxa
        TextView txtname = converView.findViewById(R.id.name_food);
        TextView txtdes = converView.findViewById(R.id.des_food);
        TextView txtmonney = converView.findViewById(R.id.monney_food);

        ImageView imageV = converView.findViewById(R.id.img_food);

        double money = list_food.getMoney();

        txtname.setText(list_food.getName());
        txtdes.setText(list_food.getDes());
        txtmonney.setText(String.valueOf(money) + "00 vnd");
        imageV.setImageResource(list_food.getImg());
        return converView;
    }

//    private Context context;
//    private int layout;
//    private List<List_Food> arraylist;
//
//    public Adapter_Test(Context context, int layout,List<List_Food> arraylist) {
//        this.context = context;
//        this.layout = layout;
//        this.arraylist = arraylist;
//    }
//
//    @Override
//    public int getCount() {
//        return arraylist.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View converView, ViewGroup viewGroup) {
//
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        converView = inflater.inflate(layout,null);
//
//        List_Food list_food = arraylist.get(i);
//
//        //anhxa
//        TextView textV1 = converView.findViewById(R.id.name);
//        TextView textV2 = converView.findViewById(R.id.mota);
//        ImageView imageV = converView.findViewById(R.id.imgHinh);
//
//        textV1.setText(list_food.getName());
//        textV2.setText(list_food.getDes());
//        imageV.setImageResource(list_food.getImg());
//        return converView;
//    }
}
