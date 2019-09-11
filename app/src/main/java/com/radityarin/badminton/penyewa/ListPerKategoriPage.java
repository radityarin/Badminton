package com.radityarin.badminton.penyewa;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class ListPerKategoriPage extends AppCompatActivity {

    FirebaseRecyclerAdapter<Penyedia,TempatViewHolder> tempatadapter;
    String kategoritempat;
    DatabaseReference produkRef;
    TextView titleperkategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_per_kategori_page);

        titleperkategori = findViewById(R.id.titleperkategori);

        Button btnBack = findViewById(R.id.backbutton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListPerKategoriPage.this, MainActivity.class));
            }
        });

        kategoritempat = getIntent().getStringExtra("kategori");
        titleperkategori.setText(kategoritempat);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

//        produkRef = FirebaseDatabase.getInstance().getReference().child("Detail Penyedia");
//
//        Query query = produkRef.orderByChild("kategori").equalTo(kategoritempat);
//        FirebaseRecyclerOptions<Penyedia> options =
//                new FirebaseRecyclerOptions.Builder<Penyedia>()
//                        .setQuery(query, Penyedia.class)
//                        .build();
//
//        tempatadapter = new FirebaseRecyclerAdapter<Penyedia, TempatViewHolder>(options) {
//            @Override
//            public TempatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                // Create a new instance of the ViewHolder, in this case we are using a custom
//                // layout called R.layout.message for each item
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.card_lokasi_horizontal, parent, false);
//                return new TempatViewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(TempatViewHolder holder, int position, final Penyedia model) {
//                holder.display(model.getNama(), model.getUrlfoto(), model.getNotelepon(), model.getAlamat());
//                holder.view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                        Intent intent = new Intent(ListPerKategoriPage.this,TempatPage.class);
////                        intent.putExtra("namatempat",model.getNama());
////                        intent.putExtra("urlfoto",model.getUrlfoto());
////                        intent.putExtra("notelepon",model.getNotelepon());
////                        intent.putExtra("alamattempat",model.getAlamat());
////                        intent.putExtra("idtempat",model.getIduser());
////                        startActivity(intent);
//                        Intent intent = new Intent(ListPerKategoriPage.this,PesanPage.class);
////                        intent.putExtra("barang",barang);
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
