package com.radityarin.badminton.penyedia;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.radityarin.badminton.R;
import com.radityarin.badminton.penyewa.OrderFragment;
import com.radityarin.badminton.pojo.Sewa;

import java.util.ArrayList;

public class OrderFragmentPenyedia extends Fragment {
    FirebaseAuth auth;
    FirebaseRecyclerAdapter<Sewa, OrderFragmentPenyedia.DonasiViewHolder> sewaAdapter;

    public OrderFragmentPenyedia() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_fragment_order, parent, false);
        auth = FirebaseAuth.getInstance();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final ArrayList<Sewa> listnotifikasi =new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference sewaRef = database.getReference().child("Detail Sewa");
        Query query = sewaRef.orderByChild("idlapangan").equalTo(auth.getUid());

        FirebaseRecyclerOptions<Sewa> options =
                new FirebaseRecyclerOptions.Builder<Sewa>()
                        .setQuery(query, Sewa.class)
                        .build();

        sewaAdapter = new FirebaseRecyclerAdapter<Sewa, OrderFragmentPenyedia.DonasiViewHolder>(options) {
            @Override
            public OrderFragmentPenyedia.DonasiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_order, parent, false);

                return new OrderFragmentPenyedia.DonasiViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(OrderFragmentPenyedia.DonasiViewHolder holder, int position, final Sewa model) {
                holder.display(model.getNamalapangan(),model.getTglsewa(),model.getJamsewa(),model.getStatussewa());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(),KonfirmasiPesananPage.class);
                        intent.putExtra("sewa",model);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(sewaAdapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        sewaAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        sewaAdapter.stopListening();
    }

    public class DonasiViewHolder extends RecyclerView.ViewHolder {

        View view;

        public DonasiViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void display(String lapangansewa, String tanggalsewa, String jamsewa, String statussewa){
            TextView tvnamalapangan = view.findViewById(R.id.namalapangansewa);
            tvnamalapangan.setText(lapangansewa);
            TextView tvtanggalsewa = view.findViewById(R.id.tanggalsewa);
            tvtanggalsewa.setText(tanggalsewa);
            TextView tvjamsewa = view.findViewById(R.id.jamsewa);
            tvjamsewa.setText(jamsewa);
            TextView tvstatussewa = view.findViewById(R.id.statussewa);
            tvstatussewa.setText(statussewa);
        }

    }

}
