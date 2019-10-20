package com.radityarin.badminton.penyedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.radityarin.badminton.generalactivity.LoginPage;
import com.radityarin.badminton.R;
import com.radityarin.badminton.pojo.Penyedia;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class SignUpPenyediaPage extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText inputEmail;
    private EditText inputNama;
    private EditText inputAlamat;
    private EditText inputNoTelepon;
    private EditText inputPassword;
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
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();

        inputNama = findViewById(R.id.namalapangan);
        inputEmail = findViewById(R.id.emaillapangan);
        inputAlamat = findViewById(R.id.alamatlapangan);
        inputNoTelepon = findViewById(R.id.noteleponlapangan);
        inputPassword = findViewById(R.id.passwordlapangan);

        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPenyediaPage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        Button btnDaftar = findViewById(R.id.buttondaftarpenyedia);
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
                    kordinat = add.getLatitude() + ", " + add.getLongitude();
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
                                            Log.v("error", Objects.requireNonNull(task.getResult()).toString());
                                        } else {
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference myRef = database.getReference("Detail Penyedia").child(Objects.requireNonNull(auth.getUid()));
                                            Penyedia penyedia = new Penyedia(auth.getUid(), email, nama, "", alamat, kordinat, "", notelepon, "", "", "",nama.toLowerCase(),"",2.5, false);
                                            myRef.setValue(penyedia);
                                            PD.dismiss();
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
