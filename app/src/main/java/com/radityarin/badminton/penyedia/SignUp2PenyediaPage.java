package com.radityarin.badminton.penyedia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.radityarin.badminton.R;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class SignUp2PenyediaPage extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 1;
    private String jambuka = "";
    private String jamtutup = "";
    private ImageView ivfotolapangan;
    private EditText inputJumlah, inputHarga;
    private FirebaseAuth auth;
    private String urlfotolapangan;
    private StorageReference imageStorage;
    private String jumlah,harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2_penyedia_page);

        auth = FirebaseAuth.getInstance();
        imageStorage = FirebaseStorage.getInstance().getReference();
        inputJumlah = findViewById(R.id.jumlahlapangan);
        inputHarga = findViewById(R.id.hargalapangan);

        Button backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivfotolapangan = findViewById(R.id.uploadfotolapangan);
        ivfotolapangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        final Spinner spn_jambuka = findViewById(R.id.jambuka);
        Spinner spn_jamtutup = findViewById(R.id.jamtutup);
        String[] jambukaarray = getResources().getStringArray(R.array.jambuka);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_item, jambukaarray
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_jambuka.setAdapter(spinnerArrayAdapter);
        spn_jambuka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jambuka = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] jamtutuparray = getResources().getStringArray(R.array.jamtutup);
        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<>(
                this, R.layout.spinner_item, jamtutuparray
        );
        spn_jamtutup.setAdapter(spinnerArrayAdapter2);
        spn_jamtutup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jamtutup = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btnkonfirmasi = findViewById(R.id.buttondaftarpenyedia2);
        btnkonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah = inputJumlah.getText().toString();
                harga = inputHarga.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference mDatabaseRef = database.getReference();
                mDatabaseRef.child("Detail Penyedia").child(Objects.requireNonNull(auth.getUid())).child("fotolapangan").setValue(urlfotolapangan);
                mDatabaseRef.child("Detail Penyedia").child(auth.getUid()).child("jamsewa").setValue(jambuka+" - "+jamtutup);
                mDatabaseRef.child("Detail Penyedia").child(auth.getUid()).child("jumlahlapangan").setValue(jumlah);
                mDatabaseRef.child("Detail Penyedia").child(auth.getUid()).child("harga").setValue(harga);
                mDatabaseRef.child("Detail Penyedia").child(auth.getUid()).child("jambuka").setValue(jambuka);
                mDatabaseRef.child("Detail Penyedia").child(auth.getUid()).child("jamtutup").setValue(jamtutup);
                Intent intent = new Intent(SignUp2PenyediaPage.this, MainPenyediaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            final Uri imageUri = Objects.requireNonNull(data).getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ivfotolapangan.setImageBitmap(bitmap);


            final StorageReference filepath = imageStorage.child("Foto Lapangan").child(UUID.randomUUID().toString() + ".jpg");
            filepath.putFile(Objects.requireNonNull(imageUri)).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }

                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        //mendapatkan link foto
                        Uri downloadUri = task.getResult();
                        urlfotolapangan = Objects.requireNonNull(downloadUri).toString();
                    }
                }
            });
        }
    }
}
