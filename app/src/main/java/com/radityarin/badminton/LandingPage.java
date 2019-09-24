package com.radityarin.badminton;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radityarin.badminton.penyedia.MainPenyediaActivity;
import com.radityarin.badminton.penyedia.SignUpPenyediaPage;
import com.radityarin.badminton.penyewa.MainActivity;
import com.radityarin.badminton.penyewa.SignUpPage;

import java.util.Objects;

public class LandingPage extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        auth = FirebaseAuth.getInstance();

        Button masuk = findViewById(R.id.buttonmasuk);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this, LoginPage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        Button daftar = findViewById(R.id.buttondaftar);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this, SignUpPage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        Button daftarpenyedia = findViewById(R.id.daftarpenyedia);
        daftarpenyedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPage.this, SignUpPenyediaPage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Detail Penyedia");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean penyedia = false;
                    for (DataSnapshot dt : dataSnapshot.getChildren()) {
                        if(Objects.requireNonNull(dt.getKey()).equals(auth.getUid())){
                            penyedia = true;
                        }
                    }
                    if(penyedia){
                        Intent intent = new Intent(LandingPage.this, MainPenyediaActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        startActivity(new Intent(LandingPage.this, MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                }
            });
        }
        super.onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (auth.getCurrentUser() != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Detail Penyedia");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean penyedia = false;
                    for (DataSnapshot dt : dataSnapshot.getChildren()) {
                        if(Objects.requireNonNull(dt.getKey()).equals(auth.getUid())){
                            penyedia = true;
                        }
                    }
                    if(penyedia){
                        Intent intent = new Intent(LandingPage.this, MainPenyediaActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        startActivity(new Intent(LandingPage.this, MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                }
            });
        }
    }
}
