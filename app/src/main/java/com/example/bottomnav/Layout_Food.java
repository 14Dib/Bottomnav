package com.example.bottomnav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Layout_Food extends AppCompatActivity {

    TextView txtname , txtback;

    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_food);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
//        int img = intent.getIntExtra("img");

        txtname = (TextView) findViewById(R.id.txtName);
        txtback = (TextView) findViewById(R.id.txtBack);
//        imgView = (ImageView) findViewById(R.id.imageView2);

        txtname.setText(name);
//        imgView.setImageResource(img);

        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}