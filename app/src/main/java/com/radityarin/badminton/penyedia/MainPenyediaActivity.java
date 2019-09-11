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

//    public static class BottomNavigationViewHelper {
//        @SuppressLint("RestrictedApi")
//        public static void disableShiftMode(BottomNavigationView view) {
//            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
//            try {
//                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
//                shiftingMode.setAccessible(true);
//                shiftingMode.setBoolean(menuView, false);
//                shiftingMode.setAccessible(false);
//                for (int i = 0; i < menuView.getChildCount(); i++) {
//                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
//                    //noinspection RestrictedApi
//                    item.setShiftingMode(false);
//                    // set once again checked value, so view will be updated
//                    //noinspection RestrictedApi
//                    item.setChecked(item.getItemData().isChecked());
//                }
//            } catch (NoSuchFieldException e) {
//                Log.e("BNVHelper", "Unable to get shift mode field", e);
//            } catch (IllegalAccessException e) {
//                Log.e("BNVHelper", "Unable to change value of shift mode", e);
//            }
//        }
//    }
}
