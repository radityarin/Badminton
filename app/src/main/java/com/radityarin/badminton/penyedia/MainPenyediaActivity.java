package com.radityarin.badminton.penyedia;


import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

import com.radityarin.badminton.R;

public class MainPenyediaActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.orderbutton:
                    OrderFragmentPenyedia orderFragmentPenyedia = new OrderFragmentPenyedia();
                    fragmentTransaction.replace(R.id.main_frame, orderFragmentPenyedia,"Histori Fragment");
                    fragmentTransaction.commit();
                    setTitle("History");
                    return true;
                case R.id.historybutton:
                    HistoryFragmentPenyedia historyFragmentPenyedia = new HistoryFragmentPenyedia();
                    fragmentTransaction.replace(R.id.main_frame,historyFragmentPenyedia,"History Fragment");
                    fragmentTransaction.commit();
                    setTitle("History");
                    return true;
                case R.id.profilebutton:
                    ProfileFragmentPenyedia profileFragment = new ProfileFragmentPenyedia();
                    fragmentTransaction.replace(R.id.main_frame, profileFragment, "Profile Fragment");
                    fragmentTransaction.commit();
                    setTitle("Profile");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_penyedia);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        OrderFragmentPenyedia homeFragment = new OrderFragmentPenyedia();
        fragmentTransaction.replace(R.id.main_frame, homeFragment, "Home Fragment");
        fragmentTransaction.commit();
        setTitle("Home");
    }

}
