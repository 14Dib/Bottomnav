package com.example.bottomnav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnav.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repplaceFragment(new HomeFragment());
        binding.bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if(id == R.id.action_home){
                repplaceFragment(new HomeFragment());
            } else if (id == R.id.action_profile) {
                repplaceFragment(new ProfileFragment());
            } else if (id == R.id.action_setting) {
                repplaceFragment(new SettingFragment());
            }

            return true;
        });

    }

    private void repplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout3,fragment);
        fragmentTransaction.commit();
    }
}