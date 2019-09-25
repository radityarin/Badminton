package com.radityarin.badminton.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.radityarin.badminton.R;
import com.radityarin.badminton.penyedia.KonfirmasiPesananPage;
import com.radityarin.badminton.pojo.Rating;
import com.radityarin.badminton.pojo.Sewa;

import java.util.ArrayList;

public class AdapterKomentar extends RecyclerView.Adapter<AdapterKomentar.ViewHolder> {

    private final ArrayList<Rating> listrating;
    private final Context context;

    public AdapterKomentar(ArrayList<Rating> listrating, Context context) {
        this.listrating = listrating;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_komentar, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.display(listrating.get(position).getNamapenyewa(),listrating.get(position).getKomentar());
    }

    @Override
    public int getItemCount() {
        return listrating.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final View view;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        void display(String namapenyewa, String komentar) {
            TextView tvnamapenyewa = view.findViewById(R.id.namapenyewa);
            tvnamapenyewa.setText(namapenyewa);
            TextView tvkomentarpenyewa = view.findViewById(R.id.komentarpenyewa);
            tvkomentarpenyewa.setText(komentar);
        }

    }
}