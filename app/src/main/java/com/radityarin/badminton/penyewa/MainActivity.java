package com.radityarin.badminton.penyewa;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radityarin.badminton.AlarmReceiver;
import com.radityarin.badminton.R;
import com.radityarin.badminton.pojo.Sewa;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    public static final String EXTRA_MESSAGE = "message";
    private final static int ID_REMINDER = 100;


    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                    fragmentTransaction.replace(R.id.main_frame, orderFragment, "Order Fragment");
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
        fragmentTransaction.add(R.id.main_frame, homeFragment, "Home Fragment");
        fragmentTransaction.commit();
        setTitle("Home");

        auth = FirebaseAuth.getInstance();

        DatabaseReference sewaRef = FirebaseDatabase.getInstance().getReference().child("Detail Sewa");
        sewaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Sewa mSewa = dt.getValue(Sewa.class);
                    if (mSewa.getIdpenyewa().equals(auth.getUid()) && (mSewa.getStatussewa().equalsIgnoreCase("Pesanan dikonfirmasi"))) {
                        showalarm(mSewa);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showalarm(Sewa sewa){
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, sewa.getNamalapangan());

        String jam[] = sewa.getJamsewa().split(" - ");
        String jam2[] = jam[1].split(":");

        int jamnya = Integer.parseInt(jam2[0])-1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, jamnya);
        calendar.set(Calendar.MINUTE, 50);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), ID_REMINDER, intent, 0);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

}
