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
import com.radityarin.badminton.pojo.Sewa;

import java.util.ArrayList;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.ViewHolder> {

    private final ArrayList<Sewa> listsewa;
    private final Context context;
    private final boolean penyedia;

    public AdapterOrder(ArrayList<Sewa> listsewa, Context context, boolean penyedia) {
        this.listsewa = listsewa;
        this.context = context;
        this.penyedia = penyedia;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_order, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.display(listsewa.get(position).getNamalapangan(), listsewa.get(position).getTglsewa(), listsewa.get(position).getJamsewa(), listsewa.get(position).getStatussewa(),listsewa.get(position).getNomorlapangan());
        if (penyedia){
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, KonfirmasiPesananPage.class);
                    intent.putExtra("sewa",listsewa.get(position));
                    intent.putExtra("konfirmasi",listsewa.get(position).getStatussewa());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listsewa.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final View view;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        void display(String lapangansewa, String tanggalsewa, String jamsewa, String statussewa, String nomorlapangan) {
            TextView tvnamalapangan = view.findViewById(R.id.namalapangansewa);
            if(statussewa.equalsIgnoreCase("Belum dikonfirmasi")) {
                tvnamalapangan.setText(lapangansewa);
            } else {
                tvnamalapangan.setText(lapangansewa + " Lap. no " + nomorlapangan);
            }
            TextView tvtanggalsewa = view.findViewById(R.id.tanggalsewa);
            tvtanggalsewa.setText(tanggalsewa);
            TextView tvjamsewa = view.findViewById(R.id.jamsewa);
            tvjamsewa.setText(jamsewa);
            TextView tvstatussewa = view.findViewById(R.id.statussewa);
            tvstatussewa.setText(statussewa);
        }

    }
}