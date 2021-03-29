package com.mostafabor3e.eat_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mostafabor3e.eat_app.Model.Gategroy;
import com.mostafabor3e.eat_app.R;
import com.mostafabor3e.eat_app.ui.SetonClickLisener;

import java.util.List;

public class AdapterGategroy extends RecyclerView.Adapter<AdapterGategroy.HolderGategory> {
    private List<Gategroy>gategroys;
    private Context context;
    private SetonClickLisener setonClickLisener;


    public AdapterGategroy(List<Gategroy> gategroys, Context context, SetonClickLisener setonClickLisener) {
        this.gategroys = gategroys;
        this.context = context;
        this.setonClickLisener = setonClickLisener;
    }

    @NonNull
    @Override
    public HolderGategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_gategroy,null,false);
        return new HolderGategory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderGategory holder, final int position) {
        Gategroy gategroy=gategroys.get(position);
        holder.name.setText(gategroy.getName().toString());
        Glide.with(context).load(gategroy.getImage()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setonClickLisener.onclick(view,position,false);

            }
        });

    }

    @Override
    public int getItemCount() {
        return  gategroys.size();
    }

    class HolderGategory extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView;
        public HolderGategory(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tv_geta_item);
            imageView=itemView.findViewById(R.id.iv_gate_item);
        }
    }
}
