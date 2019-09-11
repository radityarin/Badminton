package com.radityarin.badminton.penyewa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.radityarin.badminton.R;

import java.util.Locale;

public class KonfirmasiPage extends AppCompatActivity {

    private Button btnselesai;
    private LinearLayout lljemput, llantar;
    private TextView alamattempat;
    String alamat, kordinat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_page);

        alamattempat = findViewById(R.id.alamattempat);

        lljemput = findViewById(R.id.lljemput);
        llantar = findViewById(R.id.llantar);
        String metode = getIntent().getStringExtra("metode");
        alamat = getIntent().getStringExtra("alamat");
        String tanggaldonasi = getIntent().getStringExtra("tanggaldonasi");
        TextView tglpenjemputan = findViewById(R.id.tanggalpenjemputan);

        kordinat = getIntent().getStringExtra("kordinat");
        if (metode.equals("Jemput di rumah")) {
            lljemput.setVisibility(View.VISIBLE);
            tglpenjemputan.setText(tanggaldonasi);
            alamattempat.setText(alamat);
        } else {
            Button test = findViewById(R.id.test);
            test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] latilongi = kordinat.split(",");
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%s,%s (%s)", latilongi[0], latilongi[1], "Where the party is at");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                }
            });
            llantar.setVisibility(View.VISIBLE);
            alamattempat.setText(alamat);
        }

        btnselesai = findViewById(R.id.selesaibutton);
        btnselesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
