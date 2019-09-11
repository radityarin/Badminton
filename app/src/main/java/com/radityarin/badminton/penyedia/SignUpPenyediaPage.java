package com.radityarin.badminton.penyedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.radityarin.badminton.LoginPage;
import com.radityarin.badminton.R;
import com.radityarin.badminton.LandingPage;
import com.radityarin.badminton.pojo.Penyedia;
import com.sucho.placepicker.PlacePicker;

import java.io.IOException;
import java.util.List;


public class SignUpPenyediaPage extends AppCompatActivity {
    private final int PLACE_PICKER_REQUEST = 1;
    private FirebaseAuth auth;
    private EditText inputEmail, inputNama, inputJumlah, inputAlamat, inputNoTelepon, inputPassword, inputHarga;
    private Button btnDaftar, login;
    private String kordinat = "";
    private ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_penyedia_page);

        Button btnBack = findViewById(R.id.backbutton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpPenyediaPage.this, LandingPage.class));
            }
        });

        auth = FirebaseAuth.getInstance();

        inputNama = findViewById(R.id.namalapangan);
        inputEmail = findViewById(R.id.emaillapangan);
//        inputJumlah = findViewById(R.id.jumlahlapangan);
        inputAlamat = findViewById(R.id.alamatlapangan);
        inputNoTelepon = findViewById(R.id.noteleponlapangan);
        inputPassword = findViewById(R.id.passwordlapangan);
//        inputHarga = findViewById(R.id.hargalapangan);

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPenyediaPage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        btnDaftar = findViewById(R.id.buttondaftarpenyedia);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nama = inputNama.getText().toString();
                final String email = inputEmail.getText().toString();
                final String alamat = inputAlamat.getText().toString();
                final String notelepon = inputNoTelepon.getText().toString();
                final String password = inputPassword.getText().toString();

                PD = new ProgressDialog(SignUpPenyediaPage.this);
                PD.setMessage("Loading...");
                PD.setCancelable(true);
                PD.setCanceledOnTouchOutside(false);
                PD.show();

                Geocoder gc = new Geocoder(getApplicationContext());
                try {
                    List<Address> list = gc.getFromLocationName(alamat, 1);
                    Address add = list.get(0);
                    String locality = add.getLocality();
                    kordinat = String.valueOf(add.getLatitude()+", "+add.getLongitude());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    if (password.length() > 0 && email.length() > 0) {
                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignUpPenyediaPage.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(
                                                    SignUpPenyediaPage.this,
                                                    "Authentication Failed",
                                                    Toast.LENGTH_LONG).show();
                                            Log.v("error", task.getResult().toString());
                                        } else {
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference myRef = database.getReference("Detail Penyedia").child(auth.getUid());
                                            Penyedia penyedia = new Penyedia(auth.getUid(), email, nama, "", alamat, kordinat, "", notelepon, "", "", "", false);
                                            myRef.setValue(penyedia);
                                            Intent intent = new Intent(SignUpPenyediaPage.this, SignUp2PenyediaPage.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(
                                SignUpPenyediaPage.this,
                                "Fill All Fields",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
