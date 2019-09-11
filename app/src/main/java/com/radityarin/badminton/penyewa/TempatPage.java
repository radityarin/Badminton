package com.radityarin.badminton.penyewa;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radityarin.badminton.R;
import com.radityarin.badminton.adapter.AdapterPenyedia;
import com.radityarin.badminton.pojo.Penyedia;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class TempatPage extends AppCompatActivity {

    private Penyedia penyedia;
    private Button btntelepon, btnpeta;
    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;
    private String jammulai = "";
    private String jamselesai = "";
    private SpinnerAdapter spinnerAdapter,spinnerAdapter2;
//    private ArrayAdapter<String> spinnerArrayAdapter,spinnerArrayAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempat_page);
//
//        String[] jambukaarray = getResources().getStringArray(R.array.jambuka);
//        spinnerArrayAdapter = new ArrayAdapter<String>(
//                this, R.layout.spinner_item, jambukaarray
//        );
//        String[] jamtutuparray = getResources().getStringArray(R.array.jamtutup);
//        spinnerArrayAdapter2 = new ArrayAdapter<String>(
//                this, R.layout.spinner_item, jamtutuparray
//        );

        final ArrayList<Penyedia> listtempat = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Detail Penyedia");

        penyedia = getIntent().getParcelableExtra("penyedia");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Penyedia mLokasi = dt.getValue(Penyedia.class);
                    if (!(mLokasi.getNamalapangan().equalsIgnoreCase(penyedia.getNamalapangan())))
                        listtempat.add(mLokasi);
                }

                RecyclerView recyclerView = findViewById(R.id.recycler_view_lokasi);
                recyclerView.setAdapter(new AdapterPenyedia(listtempat, getApplicationContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Button btnBack = findViewById(R.id.backbutton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TempatPage.this, MainActivity.class));
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

        Button order = findViewById(R.id.pesanbutton);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TempatPage.this, PesanPage.class);
                intent.putExtra("penyedia", penyedia);
                startActivity(intent);
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
