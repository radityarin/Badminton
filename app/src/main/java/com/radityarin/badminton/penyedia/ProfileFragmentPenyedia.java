package com.radityarin.badminton.penyedia;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.radityarin.badminton.pojo.Penyedia;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class ProfileFragmentPenyedia extends Fragment {

    ImageView ivurl;
    final String url = "https://t4.ftcdn.net/jpg/02/15/84/43/240_F_215844325_ttX9YiIIyeaR7Ne6EaLLjMAmy4GvPC69.jpg";

    public ProfileFragmentPenyedia() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_fragment_profile, container, false);
        final LinearLayout linearLayout_setting = view.findViewById(R.id.linearsettings);

        TextView btnLogOut = view.findViewById(R.id.btnLogOut);

        Button btnSetting = view.findViewById(R.id.buttonsetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_setting.setVisibility(View.VISIBLE);
            }
        });
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Detail Penyedia").child(auth.getUid());
        ivurl = view.findViewById(R.id.gambarpanti);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Penyedia penyedia = dataSnapshot.getValue(Penyedia.class);
                TextView nama = view.findViewById(R.id.namapanti);
                TextView kategori = view.findViewById(R.id.kategori);
                TextView email = view.findViewById(R.id.emailpanti);
                TextView alamat = view.findViewById(R.id.alamatpanti);
                TextView kordinat = view.findViewById(R.id.kordinatpanti);
                TextView notelepon = view.findViewById(R.id.nopanti);
                nama.setText(penyedia.getNamalapangan());
                email.setText(penyedia.getEmaillapangan());
                alamat.setText(penyedia.getAlamatlapangan());
                kordinat.setText(penyedia.getKordinatlapangan());
                notelepon.setText(penyedia.getNotelepon());
//                kategori.setText(.getKategori());
//                email.setText(tempat.getEmail());
//                alamat.setText(tempat.getAlamat());
//                kordinat.setText(tempat.getKordinat());
//                notelepon.setText(tempat.getNotelepon());
//                Picasso.get().load(tempat.getUrlfoto()).into(ivurl);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());

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
        return view;
    }

}
