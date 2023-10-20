package com.example.bottomnav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bottomnav.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    ListView listViewfood2;

    ActivityMainBinding binding;
    ArrayList<List_Food> list_foods;

    Adapter_Test adapter_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        listViewfood2 = (ListView) findViewById(R.id.listviewFood2);

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

        adapter_list = new Adapter_Test(TestActivity.this,R.layout.layout_food,list_foods);
        listViewfood2.setAdapter(adapter_list);

        listViewfood2.setClickable(true);

        listViewfood2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    Toast.makeText(TestActivity.this, "Kakaa", Toast.LENGTH_SHORT).show();
                }
                if(i == 1){
                    Toast.makeText(TestActivity.this, "Hihi", Toast.LENGTH_SHORT).show();
                }
                if(i == 2){
                    Toast.makeText(TestActivity.this, "Kata", Toast.LENGTH_SHORT).show();
                }
                if(i == 3){
                    Toast.makeText(TestActivity.this, "Rina", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}