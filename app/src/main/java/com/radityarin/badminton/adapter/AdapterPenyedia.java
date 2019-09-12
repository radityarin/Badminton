package com.radityarin.badminton.adapter;


import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.radityarin.badminton.R;
import com.radityarin.badminton.pojo.Penyedia;
import com.radityarin.badminton.penyewa.TempatPage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterPenyedia extends RecyclerView.Adapter<AdapterPenyedia.ViewHolder> {

    private final ArrayList<Penyedia> listlokasi;
    private final Context context;

    public AdapterPenyedia(ArrayList<Penyedia> listlokasi, Context context) {
        this.listlokasi = listlokasi;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_lokasi, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_nama.setText(listlokasi.get(position).getNamalapangan());
        holder.tv_alamat.setText(listlokasi.get(position).getAlamatlapangan());
        Picasso.get().load(listlokasi.get(position).getFotolapangan()).into(holder.iv_url);
        holder.cv_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TempatPage.class);
                intent.putExtra("penyedia", listlokasi.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listlokasi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_url;
        private final TextView tv_nama;
        private final TextView tv_alamat;
        private final CardView cv_lokasi;

        ViewHolder(View itemView) {
            super(itemView);
            iv_url = itemView.findViewById(R.id.urllapangan);
            tv_nama = itemView.findViewById(R.id.namalapangan);
            tv_alamat = itemView.findViewById(R.id.alamatlapangan);
            cv_lokasi = itemView.findViewById(R.id.cardlokasi);
        }
    }
}