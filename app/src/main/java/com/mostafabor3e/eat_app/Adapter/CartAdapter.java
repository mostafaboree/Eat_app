package com.mostafabor3e.eat_app.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mostafabor3e.eat_app.Model.FoodM;
import com.mostafabor3e.eat_app.R;
import com.mostafabor3e.eat_app.RooM.RoomFactory;


import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.AllMenuViewHolder> {
    List<FoodM>foodMList;
    Context context;
    int x;

    public CartAdapter(List<FoodM> foodMList, Context context) {
        this.foodMList = foodMList;
        this.context = context;
    }

    @NonNull
    @Override
    public AllMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,null,false);

        return new AllMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllMenuViewHolder holder, int position) {
        final FoodM food=foodMList.get(position);
        holder.allMenuName.setText(food.getName());
        holder.allMenuPrice.setText(food.getPrice());
        holder.discount.setText(food.getDiscount()+"%");
        //holder.allMenuNote.setText(food.getDescription());
        Glide.with(context).load(food.getImage()).into(holder.allMenuImage);
        holder.quntity.setText(""+food.getQuentity());
         x= food.getQuentity();
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int x= food.getQuentity();
                ++x;
               holder.quntity.setText(""+x);
               food.setQuentity(x);
               updateQue(food.getId(),x);

            }
        });
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                --x;
                holder.quntity.setText(""+x);
                food.setQuentity(x);
                updateQue(food.getId(),x);


            }
        });



    }

    @Override
    public int getItemCount() {
        return foodMList.size();
    }

    public static class AllMenuViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        TextView allMenuName, allMenuNote, allMenuRating, allMenuTime, allMenuCharges, allMenuPrice,quntity,discount;
        ImageView allMenuImage,add,sub;
        CardView cardView;

        public AllMenuViewHolder(@NonNull View itemView) {
            super(itemView);

            allMenuName = itemView.findViewById(R.id.all_menu_name);
           // allMenuNote = itemView.findViewById(R.id.all_menu_note);

            allMenuRating = itemView.findViewById(R.id.all_menu_rating);
            allMenuPrice = itemView.findViewById(R.id.all_menu_price);
            allMenuImage = itemView.findViewById(R.id.all_menu_image);
            quntity=itemView.findViewById(R.id.tv_quntity);
            cardView=itemView.findViewById(R.id.order_card);
            discount=itemView.findViewById(R.id.tv_discount);
            sub=itemView.findViewById(R.id.iv_qun_sub);
            add=itemView.findViewById(R.id.iv_add_quen);
            cardView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("select action");
            contextMenu.add(10,101,getAdapterPosition(),"delete");


        }
    }
    public void updateQue(long id,int que){
        RoomFactory.getInstace(context).dataAcess().update(que,id).subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io()).subscribe(new CompletableObserver() {
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

}
