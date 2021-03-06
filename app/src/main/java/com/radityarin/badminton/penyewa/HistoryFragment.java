package com.radityarin.badminton.penyewa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radityarin.badminton.R;
import com.radityarin.badminton.adapter.AdapterOrder;
import com.radityarin.badminton.pojo.Sewa;

import java.util.ArrayList;
import java.util.Objects;


public class HistoryFragment extends Fragment {

    private ArrayList<Sewa> listsewa;
    private FirebaseAuth auth;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_notification, container, false);
        auth = FirebaseAuth.getInstance();
        listsewa = new ArrayList<>();

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseReference sewaRef = FirebaseDatabase.getInstance().getReference().child("Detail Sewa");
        sewaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listsewa.clear();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Sewa mSewa = dt.getValue(Sewa.class);
                    if (Objects.requireNonNull(mSewa).getIdpenyewa().equals(auth.getUid()) && (mSewa.getStatussewa().equalsIgnoreCase("Pesanan selesai") || mSewa.getStatussewa().equalsIgnoreCase("Jam tidak tersedia"))) {
                        listsewa.add(mSewa);
                    }
                }
                recyclerView.setAdapter(new AdapterOrder(listsewa, getContext(),false));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

}