package com.radityarin.badminton.penyedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.radityarin.badminton.R;
import com.radityarin.badminton.pojo.Sewa;

public class KonfirmasiPesananPage extends AppCompatActivity{

    private Sewa sewa;
    private EditText edtnolapangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pesanan_page);

        Button backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayout llinputnolapangan = findViewById(R.id.llinputnolapangan);
        LinearLayout llbuttonkonfirmasi = findViewById(R.id.llbuttonkonfirmasi);
        Button selesai = findViewById(R.id.selesaibutton);

        sewa = getIntent().getParcelableExtra("sewa");

        String cek = getIntent().getStringExtra("konfirmasi");

        if(cek.equalsIgnoreCase("Pesanan dikonfirmasi")){
            llinputnolapangan.setVisibility(View.INVISIBLE);
            llbuttonkonfirmasi.setVisibility(View.INVISIBLE);
            selesai.setVisibility(View.VISIBLE);
        }

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myref = database.getReference().child("Detail Sewa");
                myref.child(sewa.getIdsewa()).child("statussewa").setValue("Pesanan selesai");
                Intent intent = new Intent(KonfirmasiPesananPage.this,MainPenyediaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView tvnamapenyewa = findViewById(R.id.namapenyewa);
        tvnamapenyewa.setText(sewa.getNamapenyewa());
        TextView tvtanggalsewa = findViewById(R.id.tanggalsewa);
        tvtanggalsewa.setText(sewa.getTglsewa());
        TextView tvjamsewa = findViewById(R.id.jamsewa);
        tvjamsewa.setText(sewa.getJamsewa());
        edtnolapangan = findViewById(R.id.inputnolapangan);

        Button btnkonfirmasi = findViewById(R.id.approve);
        btnkonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtnolapangan.getText().toString().trim().equalsIgnoreCase("")) {
                    edtnolapangan.setError("Isi nomor lapangan");
                } else {
                    final String nolapangan = edtnolapangan.getText().toString();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myref = database.getReference().child("Detail Sewa");
                    myref.child(sewa.getIdsewa()).child("statussewa").setValue("Pesanan dikonfirmasi");
                    myref.child(sewa.getIdsewa()).child("nomorlapangan").setValue(nolapangan);
                    Intent intent = new Intent(KonfirmasiPesananPage.this, MainPenyediaActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        Button btnunapprove = findViewById(R.id.unapprove);
        btnunapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myref = database.getReference().child("Detail Sewa");
                myref.child(sewa.getIdsewa()).child("statussewa").setValue("Jam tidak tersedia");
                Intent intent = new Intent(KonfirmasiPesananPage.this,MainPenyediaActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
