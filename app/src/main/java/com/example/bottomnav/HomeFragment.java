package com.example.bottomnav;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bottomnav.databinding.ActivityMainBinding;
import com.example.bottomnav.listener.ICartLoadListener;
import com.example.bottomnav.listener.IMitronLoadListestener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    Adapter_List adapter_list;

    ArrayList<FoodModel> list_foods;
    IMitronLoadListestener mitronLoadListestener;
    ICartLoadListener cartLoadListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        recyclerFood = (RecyclerView) view.findViewById(R.id.recycle_food);


        listViewfood = (ListView) view.findViewById(R.id.listviewFood);

        list_foods = new ArrayList<>();

        adapter_list = new Adapter_List(HomeFragment.this,R.layout.layout_food,list_foods);
        listViewfood.setAdapter(adapter_list);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Mitron");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list_foods.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        System.out.println("Key Parent:" + snapshot.getKey());
                        FoodModel foodModel = new FoodModel();
                        foodModel.setKey(snapshot.getKey());
                        for(DataSnapshot child: snapshot.getChildren()){

                            if(child.getKey().equals("name")){
                                foodModel.setName(child.getValue().toString());
                            }

                            if(child.getKey().equals("description")){
                                foodModel.setDescription(child.getValue().toString());
                            }

                            if(child.getKey().equals("image")){
                                foodModel.setImage(child.getValue().toString());
                            }

                            if(child.getKey().equals("price")){
//                                System.out.println("Key Parent here: " + snapshot.getKey() + " Key: " + child.getKey() + " Value of: "
//                                        + child.getKey() + " "
//                                        + child.getValue());
                                foodModel.setPrice(child.getValue().toString());
                            }

                        }
                    list_foods.add(foodModel);
                }
                adapter_list.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listViewfood.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FoodModel foodModel = list_foods.get(i);
                Intent intent = new Intent(getContext() , Layout_Food.class);
                Bundle bundle = new Bundle();
                bundle.putString("key" , foodModel.getKey());
                bundle.putString("name" , foodModel.getName());
                bundle.putString("description" , foodModel.getDescription());
                bundle.putString("price" , foodModel.getPrice());
                bundle.putString("image" , foodModel.getImage());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        return view;
    }



}