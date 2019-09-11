package com.radityarin.badminton.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radityarin.badminton.R;
import com.radityarin.badminton.penyewa.PesanPage;
import com.radityarin.badminton.penyewa.TempatPage;
import com.radityarin.badminton.pojo.Penyedia;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class TempatPageAdmin extends AppCompatActivity {

    private Penyedia penyedia;
    private Button btntelepon, btnpeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempat_page_admin);

        penyedia = getIntent().getParcelableExtra("penyedia");
        Button btnBack = findViewById(R.id.backbutton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TempatPageAdmin.this, MainAdminActivity.class));
            }
        });

        ImageView ivurl = findViewById(R.id.urltempat);
        Picasso.get().load(penyedia.getFotolapangan()).into(ivurl);
        TextView tvnama = findViewById(R.id.namatempat);
        tvnama.setText(penyedia.getNamalapangan());
        TextView tvalamat = findViewById(R.id.alamattempat);
        tvalamat.setText(penyedia.getAlamatlapangan());
        TextView tvnotelepon = findViewById(R.id.notelepon);
        tvnotelepon.setText(penyedia.getNotelepon());
        TextView tvjambuka = findViewById(R.id.jambuka);
        tvjambuka.setText(penyedia.getJambuka() + " - " + penyedia.getJamtutup());
        TextView tvhargalapangan = findViewById(R.id.harga);
        tvhargalapangan.setText("Rp " + penyedia.getHarga() + " / jam");

        Button order = findViewById(R.id.aktivasibutton);
        if (penyedia.getActive()) {
            order.setText("Deaktivasi");
        }
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref = database.getReference().child("Detail Penyedia");
                if (penyedia.getActive()) {
                    myref.child(penyedia.getIdlapangan()).child("active").setValue(false);
                } else {
                    myref.child(penyedia.getIdlapangan()).child("active").setValue(true);
                }
                Intent intent = new Intent(TempatPageAdmin.this, MainAdminActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btntelepon = findViewById(R.id.btn_telepon);
        btnpeta = findViewById(R.id.btn_peta);
        btntelepon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + penyedia.getNotelepon()));
                startActivity(intent);
            }
        });
        btnpeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] latilongi = penyedia.getKordinatlapangan().split(",");
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%s,%s (%s)", latilongi[0], latilongi[1], "Where the party is at");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
    }
}
