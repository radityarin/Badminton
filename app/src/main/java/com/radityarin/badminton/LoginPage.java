package com.radityarin.badminton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radityarin.badminton.admin.MainAdminActivity;
import com.radityarin.badminton.penyedia.MainPenyediaActivity;
import com.radityarin.badminton.penyewa.MainActivity;

import java.util.Objects;

public class LoginPage extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Button backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        inputEmail = findViewById(R.id.emaillogin);
        inputPassword = findViewById(R.id.passwordlogin);
        Button btnMasuk = findViewById(R.id.buttonlogin);
        auth = FirebaseAuth.getInstance();

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                PD = new ProgressDialog(LoginPage.this);
                PD.setMessage("Loading...");
                PD.setCancelable(true);
                PD.setCanceledOnTouchOutside(false);
                PD.show();

                if (email.equals("admin") && password.equals("passwordadmin")) {
                    Intent intent = new Intent(LoginPage.this, MainAdminActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    try {

                        if (password.length() > 0 && email.length() > 0) {
                            auth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                PD.dismiss();
                                                // Sign in success, update UI with the signed-in user's information
                                                Toast.makeText(LoginPage.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                                final FirebaseAuth auth = FirebaseAuth.getInstance();
                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                DatabaseReference myRef = database.getReference("Detail Penyedia");

                                                myRef.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        boolean penyedia = false;
                                                        for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                                            if (Objects.requireNonNull(dt.getKey()).equals(auth.getUid())) {
                                                                penyedia = true;
                                                            }
                                                        }
                                                        if (penyedia) {
                                                            Intent intent = new Intent(LoginPage.this, MainPenyediaActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                        // Failed to read value
                                                    }
                                                });
                                                Intent i = new Intent(LoginPage.this, MainActivity.class);
                                                startActivity(i);
                                                finish();
                                            } else {
                                                PD.dismiss();
                                                // If sign in fails, display a message to the user.
                                                Toast.makeText(LoginPage.this, "GAGAL", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(
                                    LoginPage.this,
                                    "Fill All Fields",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    protected void onResume() {
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginPage.this, MainActivity.class));
            finish();
        }
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Detail Penyedia");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                boolean penyedia = false;
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    if (Objects.requireNonNull(dt.getKey()).equals(auth.getUid())) {
                        penyedia = true;
                    }
                }
                if (penyedia) {
                    Intent intent = new Intent(LoginPage.this, MainPenyediaActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
            }
        });
    }
}
