package com.radityarin.badminton.admin;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radityarin.badminton.LandingPage;
import com.radityarin.badminton.R;
import com.radityarin.badminton.adapter.AdapterPenyediaAdmin;
import com.radityarin.badminton.pojo.Penyedia;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NonActiveFragment extends Fragment {

    private ArrayList<Penyedia> penyedias;
    private Button btnlogout;

    public NonActiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_non_active, container, false);
        penyedias = new ArrayList<>();

        btnlogout = view.findViewById(R.id.btnLogOut);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LandingPage.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Detail Penyedia");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Penyedia mLokasi = dt.getValue(Penyedia.class);
                    if (mLokasi.getActive() == false) {
                        penyedias.add(mLokasi);
                    }
                }

                RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
                recyclerView.setAdapter(new AdapterPenyediaAdmin(penyedias,getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

}
