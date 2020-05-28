package com.radityarin.badminton.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.radityarin.badminton.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private String[] fotolapangan;

    public SliderAdapterExample(Context context, String[] fotolapangan) {
        this.context = context;
        this.fotolapangan = fotolapangan;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        viewHolder.display(fotolapangan[position]);
    }

    @Override
    public int getCount() {
        return fotolapangan.length;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.fotolapangan);
            this.itemView = itemView;
        }

        void display(String fotolapangan){
            Picasso.get().load(fotolapangan).into(imageViewBackground);
        }
    }
}