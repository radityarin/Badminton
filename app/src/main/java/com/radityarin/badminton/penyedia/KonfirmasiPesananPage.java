package com.radityarin.badminton.penyedia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.radityarin.badminton.R;
import com.radityarin.badminton.pojo.Sewa;

public class KonfirmasiPesananPage extends AppCompatActivity {

    private Sewa sewa;
    private TextView tvnamapenyewa,tvtanggalsewa,tvjamsewa;
    private EditText edtnolapangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pesanan_page);

        sewa = getIntent().getParcelableExtra("sewa");

        tvnamapenyewa = findViewById(R.id.namapenyewa);
        tvnamapenyewa.setText(sewa.getNamapenyewa());
        tvtanggalsewa = findViewById(R.id.tanggalsewa);
        tvtanggalsewa.setText(sewa.getTglsewa());
        tvjamsewa = findViewById(R.id.jamsewa);
        tvjamsewa.setText(sewa.getJamsewa());
        edtnolapangan = findViewById(R.id.inputnolapangan);

        Button btnkonfirmasi = findViewById(R.id.approve);
        btnkonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nolapangan = edtnolapangan.getText().toString();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myref = database.getReference().child("Detail Sewa");
                Query query = myref.orderByChild("idlapangan").equalTo(auth.getUid());
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        myref.child(dataSnapshot.getKey()).child("statussewa").setValue("Pesanan dikonfirmasi");
                        myref.child(dataSnapshot.getKey()).child("nomorlapangan").setValue(nolapangan);
                        Intent intent = new Intent(KonfirmasiPesananPage.this,MainPenyediaActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        Button btnunapprove = findViewById(R.id.unapprove);
        btnunapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myref = database.getReference().child("Detail Sewa");
                Query query = myref.orderByChild("idlapangan").equalTo(auth.getUid());
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        myref.child(dataSnapshot.getKey()).child("statussewa").setValue("Lapangan tidak tersedia");
                        Intent intent = new Intent(KonfirmasiPesananPage.this,MainPenyediaActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
