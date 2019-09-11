package com.radityarin.badminton.penyewa;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radityarin.badminton.DatePickerFragment;
import com.radityarin.badminton.LandingPage;
import com.radityarin.badminton.R;
import com.radityarin.badminton.penyedia.MainPenyediaActivity;
import com.radityarin.badminton.penyedia.SignUpPenyediaPage;
import com.radityarin.badminton.pojo.Penyedia;
import com.radityarin.badminton.pojo.Profil;
import com.radityarin.badminton.pojo.Sewa;

import java.text.DateFormat;
import java.util.Calendar;

public class PesanPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Penyedia penyedia;
    private String jammulai, jamselesai, tanggal;
    private TextView tvtanggal;
    private Button btnpesan;
    private FirebaseAuth auth;
    private Profil profil;
    private int child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_page);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference cek = database.getReference("Detail Sewa");
        cek.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                child = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        auth = FirebaseAuth.getInstance();
        ambilDataPengguna();

        tvtanggal = findViewById(R.id.tanggal);
        tvtanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker ");
            }
        });

        penyedia = getIntent().getParcelableExtra("penyedia");

        final Spinner spn_jambuka = findViewById(R.id.jammulai);
        Spinner spn_jamtutup = findViewById(R.id.jamselesai);
        String[] jambukaarray = getResources().getStringArray(R.array.jammulai);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, jambukaarray
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_jambuka.setAdapter(spinnerArrayAdapter);
        spn_jambuka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jammulai = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] jamtutuparray = getResources().getStringArray(R.array.jamselesai);
        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this, R.layout.spinner_item, jamtutuparray
        );
        spn_jamtutup.setAdapter(spinnerArrayAdapter2);
        spn_jamtutup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jamselesai = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnpesan = findViewById(R.id.btnpesan);
        btnpesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!jammulai.equals("") && !jamselesai.equals("") && !tanggal.equals("")) {
                    DatabaseReference myRef = database.getReference("Detail Sewa").child(String.valueOf(++child));
                    Sewa sewa = new Sewa(penyedia.getIdlapangan(), penyedia.getNamalapangan(), "", tanggal, jammulai + " - " + jamselesai, auth.getUid(), profil.getNamaUser(), "Belum di Konfirmasi");
                    myRef.setValue(sewa);
                    Intent intent = new Intent(PesanPage.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        tanggal = currentDateString;
        tvtanggal.setText(tanggal);
    }

    private void ambilDataPengguna() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Detail Pengguna");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                 value = dataSnapshot.getValue(String.class);
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Profil mProfil = dt.getValue(Profil.class);
                    if (mProfil.getUserId().equals(auth.getUid())) {
                        profil = mProfil;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}
