package com.example.bottomnav;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bottomnav.databinding.ActivityMainBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }


    ListView listViewfood;

    ArrayList <List_Food> list_foods;

    Adapter_List adapter_list;

    ActivityMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listViewfood = (ListView) view.findViewById(R.id.listviewFood);

        list_foods = new ArrayList<>();

        list_foods.add(new List_Food("Mì Trộn Cải Thìa","(Sốt Sa Tế/Bơ Tỏi)",25.000,R.drawable.caithia));
        list_foods.add(new List_Food("Mì Trộn Ốp La"  ,"(Sốt Sa Tế/Bơ Tỏi)",35.000,R.drawable.opla));
        list_foods.add(new List_Food("Mì Trộn Xá Xíu","(Sốt Sa Tế/Bơ Tỏi) ",45.000,R.drawable.xaxiu));
        list_foods.add(new List_Food("Mì Hoành Thánh Xá Xíu"   ,"(Sốt Sa Tế/Bơ Tỏi)",55.000,R.drawable.hoanhthanhxaxiu));
        list_foods.add(new List_Food("Mì Trộn Lôi Ký Thập Cẩm" ,"(Sốt Sa Tế/Bơ Tỏi)",25.000,R.drawable.loikithapcam));
        list_foods.add(new List_Food("Mì Trộn Vịt Quay Xá Xíu","(Sốt Sa Tế/Bơ Tỏi)",35.000,R.drawable.vitquayxaxiu));
        list_foods.add(new List_Food("Mì Trộn Vịt Quay"  ,"(Sốt Sa Tế/Bơ Tỏi)",45.000,R.drawable.vitquay));
        list_foods.add(new List_Food("Mì Trộn Đùi Vịt Quay","(Sốt Sa Tế/Bơ Tỏi) ",55.000,R.drawable.duivitquay));
        list_foods.add(new List_Food("Mì Trộn Hoành Thánh"   ,"(Sốt Sa Tế/Bơ Tỏi)",25.000,R.drawable.hoanhthanh));
        list_foods.add(new List_Food("Soup Bò Viên" ,"(Sốt Sa Tế/Bơ Tỏi)",35.000,R.drawable.soup));

        adapter_list = new Adapter_List(HomeFragment.this,R.layout.layout_food,list_foods);
        listViewfood.setAdapter(adapter_list);

        listViewfood.setClickable(true);

        listViewfood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for(int j = 0 ; j <=i; j++){
                    if(j == i){
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), Layout_Food.class);
                        intent.putExtra("name", list_foods.get(j).getName());
                        intent.putExtra("img", list_foods.get(j).getImg());
                        startActivity(intent);
                    }
                }



            }
        });
        return view;
    }

}