package com.mostafabor3e.eat_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mostafabor3e.eat_app.Model.Fav;
import com.mostafabor3e.eat_app.Model.FoodM;
import com.mostafabor3e.eat_app.R;
import com.mostafabor3e.eat_app.RooM.RoomFactory;
import com.mostafabor3e.eat_app.ui.SetonClickLisener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.HolderGategory> {
    private List<FoodM>gategroys;
    private Context context;
    private SetonClickLisener setonClickLisener;
    private List<Fav> favlist;


    public AdapterFood(List<FoodM> gategroys, Context context, SetonClickLisener setonClickLisener) {
        this.gategroys = gategroys;
        this.context = context;
        this.setonClickLisener = setonClickLisener;
    }

    @NonNull
    @Override
    public HolderGategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_food_itrem,null,false);
        return new HolderGategory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderGategory holder, final int position) {
        final FoodM foodM =gategroys.get(position);
        Glide.with(context).load(foodM.getImage()).into(holder.imageView);
        holder.name.setText(foodM.getName());


        holder.vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.love.getVisibility()==View.VISIBLE){
                    holder.love.setVisibility(View.INVISIBLE);
                    holder.notlove.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "remove favourite", Toast.LENGTH_SHORT).show();
                    RoomFactory.getInstace(context).dataAcess().notFav(foodM.getName())
                            .subscribeOn(Schedulers.computation()).observeOn(Schedulers.io())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onComplete() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            });


                }
                else {
                    holder.love.setVisibility(View.VISIBLE);
                    holder.notlove.setVisibility(View.INVISIBLE);
                    Fav fav=new Fav(foodM.getName(),foodM.getMenuId());
                    Toast.makeText(context, "add to favourite", Toast.LENGTH_SHORT).show();
                    inserfav(fav);

                }


            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setonClickLisener.onclick(view,position,false);




            }
        });

        RoomFactory.getInstace(context).dataAcess().getfav()
                .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Fav>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<Fav> favs) {
                for (Fav fav:favs
                     ) {
                    if (foodM.getName().equals(fav.getName())){
                        holder.love.setVisibility(View.VISIBLE);
                        holder.notlove.setVisibility(View.INVISIBLE);
                    }

                }
               // Toast.makeText(context, "siaz"+favs.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    public int getItemCount() {
        return  gategroys.size();
    }

    class HolderGategory extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView,love,notlove;
        View vi;


        public HolderGategory(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tv_food_item);
            imageView=itemView.findViewById(R.id.iv_food_item);
            love=itemView.findViewById(R.id.loveb);
            notlove=itemView.findViewById(R.id.love);
            vi=itemView.findViewById(R.id.view);
        }
    }
    public void inserfav(final Fav fav){
        RoomFactory.getInstace(context).dataAcess().insetId(fav)
                .subscribeOn(Schedulers.computation()).observeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
             //  Toast.makeText(context, "add Fav", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "e"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getFav(final Button like){
        favlist=new ArrayList<>();
        RoomFactory.getInstace(context).dataAcess().getfav()
                .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Fav>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<Fav> favs) {
                if (like.getTag().equals("notlike")){
                    like.setVisibility(View.VISIBLE);
                }
                else
                    like.setVisibility(View.INVISIBLE);



            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
