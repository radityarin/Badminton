package com.radityarin.badminton.penyedia;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.radityarin.badminton.pojo.Penyedia;
import com.radityarin.badminton.pojo.Sewa;

import java.util.ArrayList;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class OrderFragmentPenyedia extends Fragment {
    private ArrayList<Sewa> listsewa;
    private FirebaseAuth auth;

    public OrderFragmentPenyedia() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_fragment_order, parent, false);

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
                    if (Objects.requireNonNull(mSewa).getIdlapangan().equals(auth.getUid()) && (mSewa.getStatussewa().equalsIgnoreCase("Belum dikonfirmasi") || mSewa.getStatussewa().equalsIgnoreCase("Pesanan dikonfirmasi"))) {
                        listsewa.add(mSewa);
                    }
                }
                recyclerView.setAdapter(new AdapterOrder(listsewa, getContext(),true));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Detail Penyedia").child(Objects.requireNonNull(auth.getUid()));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Penyedia penyedia = dataSnapshot.getValue(Penyedia.class);
                if(penyedia.getFotolapangan().equals("")) {
                    Intent intent = new Intent(getContext(),SignUp2PenyediaPage.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
        return view;
    }
}
