package com.radityarin.badminton.penyewa;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.radityarin.badminton.R;
import com.radityarin.badminton.pojo.Penyedia;
import com.squareup.picasso.Picasso;

public class ListPerKebutuhan extends AppCompatActivity {

    FirebaseRecyclerAdapter<Penyedia,ListPerKebutuhan.TempatViewHolder> tempatadapter;
    DatabaseReference produkRef;
    String barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_per_kebutuhan);

        Button btnBack = findViewById(R.id.backbutton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListPerKebutuhan.this, MainActivity.class));
            }
        });

        barang = "";
        barang = getIntent().getStringExtra("barang");
        TextView title = findViewById(R.id.title);
        title.setText(barang);

//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setHasFixedSize(false);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//
//        produkRef = FirebaseDatabase.getInstance().getReference().child("Detail Penyedia");
//        Query query = produkRef.orderByChild("kebutuhan").equalTo(barang);
//        FirebaseRecyclerOptions<Penyedia> options =
//                new FirebaseRecyclerOptions.Builder<Penyedia>()
//                        .setQuery(query, Penyedia.class)
//                        .build();
//
//        tempatadapter = new FirebaseRecyclerAdapter<Penyedia, ListPerKebutuhan.TempatViewHolder>(options) {
//            @Override
//            public ListPerKebutuhan.TempatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                // Create a new instance of the ViewHolder, in this case we are using a custom
//                // layout called R.layout.message for each item
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.card_lokasi_horizontal, parent, false);
//                return new ListPerKebutuhan.TempatViewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(ListPerKebutuhan.TempatViewHolder holder, int position, final Penyedia model) {
//                holder.display(model.getNama(), model.getUrlfoto(), model.getNotelepon(), model.getAlamat());
//                holder.view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(ListPerKebutuhan.this,PesanPage.class);
//                        intent.putExtra("barang",barang);
//                        intent.putExtra("namatempat",model.getNama());
//                        intent.putExtra("alamattempat",model.getAlamat());
//                        intent.putExtra("kordinat",model.getKordinat());
//                        intent.putExtra("idtempat",model.getIduser());
//                        startActivity(intent);
//                    }
//                });
//            }
//        };
//        recyclerView.setAdapter(tempatadapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        tempatadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tempatadapter.stopListening();
    }

    public class TempatViewHolder extends RecyclerView.ViewHolder {

        View view;

        public TempatViewHolder(View itemView) {
            super(itemView);

            view = itemView;

        }

        public void display(String namaTempat, String urlPhoto, String notelepon, String alamatTempat){
            TextView namatempat = view.findViewById(R.id.namapanti);
            namatempat.setText(namaTempat);
            TextView noTelepon = view.findViewById(R.id.notelepon);
            noTelepon.setText(notelepon);
            ImageView fototempat = view.findViewById(R.id.fotoPanti);
            Picasso.get().load(urlPhoto).into(fototempat);
            TextView alamat = view.findViewById(R.id.alamat);
            alamat.setText(alamatTempat);
        }

    }
}
