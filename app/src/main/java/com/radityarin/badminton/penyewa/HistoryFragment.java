package com.radityarin.badminton.penyewa;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.radityarin.badminton.R;
import com.radityarin.badminton.pojo.Sewa;


public class HistoryFragment extends Fragment {

//    FirebaseRecyclerAdapter<Sewa, HistoryFragment.DonasiViewHolder> donasiadapter;
//    DatabaseReference donasiRef;
//    FirebaseAuth auth;
//

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_notification, container, false);
//        auth = FirebaseAuth.getInstance();

//        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setHasFixedSize(false);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        sewaRef = FirebaseDatabase.getInstance().getReference().child("List Barang Sewa");
//
//        Query query = sewaRef.orderByChild("iddonatur").equalTo(auth.getUid());
//        FirebaseRecyclerOptions<Sewa> options =
//                new FirebaseRecyclerOptions.Builder<Sewa>()
//                        .setQuery(query, Sewa.class)
//                        .build();
//
//        sewaAdapter = new FirebaseRecyclerAdapter<Sewa, HistoryFragment.DonasiViewHolder>(options) {
//            @Override
//            public HistoryFragment.DonasiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                // Create a new instance of the ViewHolder, in this case we are using a custom
//                // layout called R.layout.message for each item
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.card_order, parent, false);
//
//                return new HistoryFragment.DonasiViewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(HistoryFragment.DonasiViewHolder holder, int position, final Sewa model) {
//                if(!model.getStatusdonasi().equals("Selesai")) {
//                    holder.display(model.getKategori(), model.getTujuan(), model.getTgldonasi(), model.getStatusdonasi());
//                }
////                holder.view.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        Intent intent = new Intent(getContext(),produkPage.class);
////                        intent.putExtra("judulproduk",model.getNamaProduk());
////                        intent.putExtra("urlproduk",model.getUrlProduk());
////                        intent.putExtra("deskripsiproduk",model.getDeskripsiProduk());
////                        intent.putExtra("hargaproduk",model.getHargaProduk());
////                        intent.putExtra("kategoriproduk",model.getKategoriProduk());
////                        startActivity(intent);
////                    }
////                });
//            }
//        };
//        recyclerView.setAdapter(sewaAdapter);


        return view;
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        donasiadapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        donasiadapter.stopListening();
//    }
//
//    public class DonasiViewHolder extends RecyclerView.ViewHolder {
//
//        View view;
//
//        public DonasiViewHolder(View itemView) {
//            super(itemView);
//            view = itemView;
//        }
//
//        public void display(String kategoribarang, String namatempat, String tanggaldonasi, String statusdonasi){
//            ImageView ivlogokategori = view.findViewById(R.id.logokategori);
//            if(kategoribarang.equals("Pakaian")){
//                ivlogokategori.setImageResource(R.drawable.baju);
//            } else if(kategoribarang.equals("Elektronik")){
//                ivlogokategori.setImageResource(R.drawable.elektronik);
//            } else if(kategoribarang.equals("Furnitur")){
//                ivlogokategori.setImageResource(R.drawable.furniture);
//            } else if(kategoribarang.equals("Buku")){
//                ivlogokategori.setImageResource(R.drawable.book);
//            }
//            TextView tvnamatempat = view.findViewById(R.id.namatempat);
//            tvnamatempat.setText(namatempat);
//            TextView tvtanggaldonasi = view.findViewById(R.id.tanggaldonasi);
//            tvtanggaldonasi.setText(tanggaldonasi);
//            TextView tvstatusdonasi = view.findViewById(R.id.statusdonasi);
//            tvstatusdonasi.setText(statusdonasi);
//        }
//
//    }
}