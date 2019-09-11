package com.radityarin.badminton.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.radityarin.badminton.R;
import com.radityarin.badminton.admin.MainAdminActivity;
import com.radityarin.badminton.admin.TempatPageAdmin;
import com.radityarin.badminton.pojo.Penyedia;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterPenyediaAdmin extends RecyclerView.Adapter<AdapterPenyediaAdmin.ViewHolder> {

    private ArrayList<Penyedia> listlokasi;
    private Context context;

    public AdapterPenyediaAdmin(ArrayList<Penyedia> listlokasi, Context context) {
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
                Intent intent = new Intent(context, TempatPageAdmin.class);
                intent.putExtra("penyedia", listlokasi.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listlokasi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_url;
        private TextView tv_nama, tv_alamat;
        private CardView cv_lokasi;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_url = itemView.findViewById(R.id.urllapangan);
            tv_nama = itemView.findViewById(R.id.namalapangan);
            tv_alamat = itemView.findViewById(R.id.alamatlapangan);
            cv_lokasi = itemView.findViewById(R.id.cardlokasi);
        }
    }
}