package com.radityarin.badminton.penyewa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.radityarin.badminton.LandingPage;
import com.radityarin.badminton.R;
import com.radityarin.badminton.pojo.Profil;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class ProfileFragment extends Fragment {

    private final int PICK_IMAGE_REQUEST = 1;
    private LinearLayout linearLayout_setting;
    private FirebaseAuth auth;
    private String urlfotouser;
    private StorageReference imageStorage;
    private CircleImageView civ_profile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        FrameLayout frameLayout_profilesettings = view.findViewById(R.id.main_frame);

        imageStorage = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        civ_profile = view.findViewById(R.id.profile_image);
        civ_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        linearLayout_setting = view.findViewById(R.id.linearsettings);
        TextView btnLogOut = view.findViewById(R.id.btnLogOut);
        Button btnSetting = view.findViewById(R.id.buttonsetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_setting.setVisibility(View.VISIBLE);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LandingPage.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        frameLayout_profilesettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_setting.setVisibility(View.INVISIBLE);
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Detail Pengguna").child(Objects.requireNonNull(auth.getUid()));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profil profil = dataSnapshot.getValue(Profil.class);
                TextView nama = view.findViewById(R.id.namaprofile);
                TextView email = view.findViewById(R.id.emailprofile);
                TextView nohp = view.findViewById(R.id.nohpprofile);
                nama.setText(Objects.requireNonNull(profil).getNamaUser());
                email.setText(profil.getEmailUser());
                nohp.setText(profil.getNomorHP());
                if (!profil.getFotoUser().equals("")) {
                    Picasso.get().load(profil.getFotoUser()).into(civ_profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            final Uri imageUri = Objects.requireNonNull(data).getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            civ_profile.setImageBitmap(bitmap);

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
                        urlfotouser = Objects.requireNonNull(downloadUri).toString();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference mDatabaseRef = database.getReference();
                        mDatabaseRef.child("Detail Pengguna").child(Objects.requireNonNull(auth.getUid())).child("fotoUser").setValue(urlfotouser);
                    }
                }
            });
        }
    }

}