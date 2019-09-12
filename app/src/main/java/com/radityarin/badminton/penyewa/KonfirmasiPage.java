package com.radityarin.badminton.penyewa;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.radityarin.badminton.R;

public class KonfirmasiPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_page);

        Button btnselesai = findViewById(R.id.selesaibutton);
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
