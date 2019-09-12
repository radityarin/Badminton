package com.radityarin.badminton.admin;


import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

import com.radityarin.badminton.R;

public class MainAdminActivity extends AppCompatActivity {

    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.orderbutton:
                    NonActiveFragment orderFragmentPenyedia = new NonActiveFragment();
                    fragmentTransaction.replace(R.id.main_frame, orderFragmentPenyedia,"Non Active Fragment");
                    fragmentTransaction.commit();
                    setTitle("Non Active");
                    return true;
                case R.id.historybutton:
                    ActiveFragment historyFragmentPenyedia = new ActiveFragment();
                    fragmentTransaction.replace(R.id.main_frame,historyFragmentPenyedia,"Active Fragment");
                    fragmentTransaction.commit();
                    setTitle("Active");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        NonActiveFragment homeFragment = new NonActiveFragment();
        fragmentTransaction.replace(R.id.main_frame, homeFragment, "Non Active Fragment");
        fragmentTransaction.commit();
        setTitle("Home");
    }

}
