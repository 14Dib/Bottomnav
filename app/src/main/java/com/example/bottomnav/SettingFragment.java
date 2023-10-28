package com.example.bottomnav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
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
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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

    ListView listViewfood;

    ArrayList<List_Food> list_foods;

    Adapter_List adapter_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

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

        adapter_list = new Adapter_List(SettingFragment.this,R.layout.layout_cart,list_foods);
        listViewfood.setAdapter(adapter_list);

        listViewfood.setClickable(true);
        return view;
    }
}