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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_page);

        btnselesai = findViewById(R.id.selesaibutton);
        btnselesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KonfirmasiPage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
