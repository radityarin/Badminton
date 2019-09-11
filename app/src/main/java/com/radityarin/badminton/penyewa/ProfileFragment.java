package com.radityarin.badminton.penyewa;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radityarin.badminton.LandingPage;
import com.radityarin.badminton.R;
import com.radityarin.badminton.pojo.Profil;

import static android.content.ContentValues.TAG;

public class ProfileFragment extends Fragment {


    private LinearLayout linearLayout_setting;
    private FrameLayout frameLayout_profilesettings;
    private TextView btnLogOut;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        frameLayout_profilesettings = view.findViewById(R.id.main_frame);
        linearLayout_setting = view.findViewById(R.id.linearsettings);
        btnLogOut = view.findViewById(R.id.btnLogOut);
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
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Detail Pengguna").child(auth.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Profil profil = dataSnapshot.getValue(Profil.class);
                TextView nama = view.findViewById(R.id.namaprofile);
                TextView email = view.findViewById(R.id.emailprofile);
                TextView noktp = view.findViewById(R.id.noktpprofile);
                TextView nohp = view.findViewById(R.id.nohpprofile);
                TextView jeniskelamin = view.findViewById(R.id.jeniskelaminprofil);
                TextView alamat = view.findViewById(R.id.alamatprofil);
                TextView kota = view.findViewById(R.id.kota);
                nama.setText(profil.getNamaUser());
                email.setText(profil.getEmailUser());
//                noktp.setText(profil.getNoKTP());
//                nohp.setText(profil.getNomorHP());
//                jeniskelamin.setText(profil.getJeniskelamin());
//                alamat.setText(profil.getAlamat());
//                kota.setText(profil.getKota());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return view;
    }

}