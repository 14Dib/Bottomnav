package com.example.bottomnav;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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

public class Adapter_List extends BaseAdapter {
    FirebaseAuth fAuth;
    private Fragment context;
    private int layout;
    private List<FoodModel> arraylist;

    public Adapter_List(Fragment context, int layout, List<FoodModel> arraylist) {
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

//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LayoutInflater inflater = (LayoutInflater) context.getLayoutInflater();

        converView = inflater.inflate(layout, null);

        FoodModel list_food = arraylist.get(i);

        //anhxa
        TextView txtname = converView.findViewById(R.id.name_food);
        TextView txtdes = converView.findViewById(R.id.des_food);
        TextView txtmonney = converView.findViewById(R.id.monney_food);
        TextView txtadd = converView.findViewById(R.id.txt_add);

        ImageView imageV = converView.findViewById(R.id.img_food);

        String price = list_food.getPrice();

        txtname.setText(list_food.getName());
        txtdes.setText("(Sốt Sa Tế/Bơ Tỏi)");
        txtmonney.setText(price + ".000 VND");
        Glide.with(context).load(list_food.getImage()).into(imageV);

        txtadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "Thêm vào giỏ hàng " + i, Toast.LENGTH_SHORT).show();
                addTocart(arraylist.get(i));
            }

            private void addTocart(FoodModel foodModel) {
                fAuth = FirebaseAuth.getInstance();
                String userId = fAuth.getCurrentUser().getUid();
                DatabaseReference userCart = FirebaseDatabase
                        .getInstance()
                        .getReference("Cart")
                        .child(userId);

                userCart.child(foodModel.getKey())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    CartModel cartModel = snapshot.getValue(CartModel.class);
                                    cartModel.setQuantity(cartModel.getQuantity()+1);
                                    Map<String,Object> updateData = new HashMap<>();
                                    updateData.put("quantity",cartModel.getQuantity());
                                    updateData.put("totalPrice",cartModel.getQuantity()*Float.parseFloat(cartModel.getPrice()));

                                    userCart.child(foodModel.getKey())
                                            .updateChildren(updateData)
                                            .addOnSuccessListener(unused -> {
                                                Toast.makeText(viewGroup.getContext(), "Add sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(viewGroup.getContext(), "Add sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                                else{
                                        CartModel cartModel = new CartModel();
                                        cartModel.setName(foodModel.getName());
                                        cartModel.setDescription(foodModel.getDescription());
                                        cartModel.setImage(foodModel.getImage());
                                        cartModel.setKey(foodModel.getKey());
                                        cartModel.setQuantity(1);
                                        cartModel.setPrice(foodModel.getPrice());
                                        cartModel.setTotalPrice(Float.parseFloat(foodModel.getPrice()));

                                        userCart.child(foodModel.getKey())
                                                .setValue(cartModel)
                                                .addOnSuccessListener(unused -> {
                                                    Toast.makeText(viewGroup.getContext(), "Add sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(viewGroup.getContext(), "Add sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
        });
        return converView;
    }
}


