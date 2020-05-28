package com.radityarin.badminton.penyewa;


import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radityarin.badminton.R;
import com.radityarin.badminton.adapter.AdapterPenyedia;
import com.radityarin.badminton.pojo.Penyedia;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private ArrayList<Penyedia> listtempat;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        SearchView svlapangan = view.findViewById(R.id.sv_lapangan);

        svlapangan.setFocusable(true);
        svlapangan.setIconified(false);
        svlapangan.requestFocusFromTouch();
        svlapangan.setOnQueryTextListener(this);

        recyclerView = view.findViewById(R.id.recycler_view_lokasi);
        listtempat = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Detail Penyedia");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listtempat.clear();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Penyedia mLokasi = dt.getValue(Penyedia.class);
                    if (mLokasi.isActive()) {
                        listtempat.add(mLokasi);
                    }
                }

                recyclerView.setAdapter(new AdapterPenyedia(listtempat, getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        query = query.toLowerCase();
        database.getReference().child("Detail Penyedia").orderByChild("namalapangansmallcase").startAt(query).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listtempat.clear();
                for(DataSnapshot dt : dataSnapshot.getChildren()){
                    Penyedia penyedia = dt.getValue(Penyedia.class);
                    Log.d("cek", Objects.requireNonNull(penyedia).getNamalapangan());
                    listtempat.add(penyedia);
                }
                recyclerView.setAdapter(new AdapterPenyedia(listtempat, getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
