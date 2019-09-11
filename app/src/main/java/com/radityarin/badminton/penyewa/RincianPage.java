package com.radityarin.badminton.penyewa;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.radityarin.badminton.DatePickerFragment;
import com.radityarin.badminton.R;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

public class RincianPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private final int PICK_IMAGE_REQUEST = 1;
    private String nama,kategori,metode,urlbarang,namatempat,alamattempat,kordinat,idtempat, namadonatur;
    String tanggaldonasi ="";
    private ImageView uploadfotoproduk;
    private FirebaseAuth auth;
    private TextView tvnamabarang,tvmetode, tvnamatempat,tvalamattempat;
    private StorageReference imageStorage;
    private ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_page);

        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database1.getReference("tempat");

        Button btnBack = findViewById(R.id.backbutton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RincianPage.this, PesanPage.class));
            }
        });

        namatempat="";
        alamattempat="";
        kordinat="";
        idtempat="";

        nama = getIntent().getStringExtra("nama");
        kategori = getIntent().getStringExtra("kategori");
        metode = getIntent().getStringExtra("metode");
        namatempat = getIntent().getStringExtra("namatempat");
        alamattempat = getIntent().getStringExtra("alamattempat");
        kordinat = getIntent().getStringExtra("kordinat");
        idtempat = getIntent().getStringExtra("idtempat");
        namadonatur = getIntent().getStringExtra("namadonatur");

        tvnamabarang = findViewById(R.id.namabarang);
        tvmetode = findViewById(R.id.metodebarang);
        tvnamatempat = findViewById(R.id.namatempat);
        tvalamattempat = findViewById(R.id.alamattempat);

        tvnamabarang.setText(nama);
        tvmetode.setText(metode);

        Button btntgl = findViewById(R.id.buttontanggal);
        btntgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker ");
            }
        });

        tvnamatempat.setText(namatempat);
        tvalamattempat.setText(alamattempat);

//        myRef1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(tvnamatempat.getText().equals("")) {
//                    String random = String.valueOf((int) (Math.random() * dataSnapshot.getChildrenCount()) + 1);
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference myRef = database.getReference("tempat").child(random);
//
//                    myRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Penyedia penyedia = dataSnapshot.getValue(Penyedia.class);
//                            tvnamatempat.setText(penyedia.getNama());
//                            tvalamattempat.setText(penyedia.getAlamat());
//                            namatempat = penyedia.getNama();
//                            kordinat = penyedia.getKordinat();
//                            idtempat = penyedia.getIduser();
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError error) {
//                            // Failed to read value
//                            Log.w(TAG, "Failed to read value.", error.toException());
//                        }
//                    });
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });



        imageStorage = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        uploadfotoproduk = findViewById(R.id.uploadfotoproduk);
        uploadfotoproduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            uploadfotoproduk.setImageBitmap(bitmap);


//membuat folder di firebase storage
            final StorageReference filepath = imageStorage.child("Barang Sewa").child(auth.getUid()).child(UUID.randomUUID().toString() + ".jpg");

            Button button = findViewById(R.id.konfirmasibutton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PD = new ProgressDialog(RincianPage.this);
                    PD.setMessage("Loading...");
                    PD.setCancelable(true);
                    PD.setCanceledOnTouchOutside(false);
                    PD.show();
                    filepath.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return filepath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {

                                //mendapatkan link foto
                                Uri downloadUri = task.getResult();
                                urlbarang = downloadUri.toString();
                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                                String key = myRef.push().getKey();

//                                Sewa donasi = new Sewa(key,nama,kategori,metode,namatempat,urlbarang,tanggaldonasi,"Sedang diproses",auth.getUid(),idtempat,namadonatur);
//                                myRef.child("List Barang Sewa").child(key).setValue(donasi).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            PD.dismiss();
//                                            alamattempat = tvalamattempat.getText().toString();
//                                            Intent intent = new Intent(RincianPage.this, KonfirmasiPage.class);
//                                            intent.putExtra("metode",metode);
//                                            intent.putExtra("alamat",alamattempat);
//                                            intent.putExtra("kordinat",kordinat);
//                                            intent.putExtra("tanggaldonasi",tanggaldonasi);
//                                            startActivity(intent);
//                                            finish();
//                                        } else {
//                                            Toast.makeText(RincianPage.this, "Upload gagal, coba lagi", Toast.LENGTH_LONG).show();
//                                        }
//                                    }
//                                });
                            }
                        }
                    });
                }
            });


        }
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        tanggaldonasi=currentDateString;
        Button button = findViewById(R.id.buttontanggal);
        button.setText(currentDateString);
    }
}
