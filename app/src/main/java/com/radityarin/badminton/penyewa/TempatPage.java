package com.radityarin.badminton.penyewa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.radityarin.badminton.adapter.AdapterKomentar;
import com.radityarin.badminton.adapter.AdapterPenyedia;
import com.radityarin.badminton.adapter.SliderAdapterExample;
import com.radityarin.badminton.pojo.Penyedia;
import com.radityarin.badminton.pojo.Rating;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class TempatPage extends AppCompatActivity {

    private Penyedia penyedia;
    private RecyclerView recyclerView;
    private TextView tvparkiran, tvtoilet, tvruangganti, tvkantin, tvwifi, tvrating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempat_page);


        final ArrayList<Penyedia> listtempat = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Detail Penyedia");

        penyedia = getIntent().getParcelableExtra("penyedia");
        tvparkiran = findViewById(R.id.fasilitas_parkiran);
        tvtoilet = findViewById(R.id.fasilitas_toilet);
        tvruangganti = findViewById(R.id.fasilitas_ruangganti);
        tvkantin = findViewById(R.id.fasilitas_kantin);
        tvwifi = findViewById(R.id.fasilitas_wifi);
        tvrating = findViewById(R.id.rating);

        tvrating.setText(penyedia.getRating()+" / 5");

        String[] fasilitas = penyedia.getFasilitas().split(";");
        if (fasilitas[0].equals("false")) {
            tvparkiran.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close_black_24dp, 0, 0, 0);
        }
        if (fasilitas[1].equals("false")) {
            tvtoilet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close_black_24dp, 0, 0, 0);
        }
        if (fasilitas[2].equals("false")) {
            tvruangganti.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close_black_24dp, 0, 0, 0);
        }
        if (fasilitas[3].equals("false")) {
            tvkantin.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close_black_24dp, 0, 0, 0);
        }
        if (fasilitas[4].equals("false")) {
            tvwifi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close_black_24dp, 0, 0, 0);
        }

        String[] fotolapangan = penyedia.getFotolapangan().split(";");
        SliderView sliderView = findViewById(R.id.imageSlider);
        SliderAdapterExample adapter = new SliderAdapterExample(this, fotolapangan);

        sliderView.setSliderAdapter(adapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Penyedia mLokasi = dt.getValue(Penyedia.class);
                    if (!(Objects.requireNonNull(mLokasi).getNamalapangan().equalsIgnoreCase(penyedia.getNamalapangan())))
                        listtempat.add(mLokasi);
                }

                RecyclerView recyclerView = findViewById(R.id.recycler_view_lokasi);
                recyclerView.setAdapter(new AdapterPenyedia(listtempat, getApplicationContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Button btnBack = findViewById(R.id.backbutton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

        Button btntelepon = findViewById(R.id.btn_telepon);
        Button btnpeta = findViewById(R.id.btn_peta);
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

        recyclerView = findViewById(R.id.recycler_view_komentar);
        final ArrayList<Rating> listrating = new ArrayList<>();
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        myRef = database2.getReference("Rating").child(penyedia.getIdlapangan());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listrating.clear();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Rating mRating = dt.getValue(Rating.class);
                    listrating.add(mRating);
                }

                recyclerView.setAdapter(new AdapterKomentar(listrating, getApplicationContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}
