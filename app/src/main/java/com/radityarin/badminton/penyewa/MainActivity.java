package com.radityarin.badminton.penyewa;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import com.radityarin.badminton.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.homebutton:
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.main_frame, homeFragment, "Home Fragment");
                    fragmentTransaction.commit();
                    setTitle("Home");
                    return true;
                case R.id.orderbutton:
                    OrderFragment orderFragment = new OrderFragment();
                    fragmentTransaction.replace(R.id.main_frame, orderFragment,"Order Fragment");
                    fragmentTransaction.commit();
                    setTitle("History");
                    return true;
                case R.id.historybutton:
                    HistoryFragment historyFragment = new HistoryFragment();
                    fragmentTransaction.replace(R.id.main_frame, historyFragment, "History Fragment");
                    fragmentTransaction.commit();
                    setTitle("Inbox");
                    return true;
                case R.id.profilebutton:
                    ProfileFragment profileFragment = new ProfileFragment();
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
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.main_frame,homeFragment,"Home Fragment");
        fragmentTransaction.commit();
        setTitle("Home");
    }

}
