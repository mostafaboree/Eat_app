package com.mostafabor3e.eat_app.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mostafabor3e.eat_app.Model.Request;
import com.mostafabor3e.eat_app.R;

import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.ViewHolderOrder> {
    List<Request>orderes;
    Context context;

    public AdapterOrder(List<Request> orderes, Context context) {
        this.orderes = orderes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderOrder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_item,null,false);
        return new ViewHolderOrder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOrder holder, int position) {
        Request order=orderes.get(position);
        holder.status.setText(order.getStute());
        holder.order_id.setText(order.getId());
        holder.phone.setText(order.getPhone());
        holder.address.setText(order.getAddress());


    }

    @Override
    public int getItemCount() {
        return orderes.size();
    }

    public class ViewHolderOrder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {
        TextView phone,order_id,address,status;
        CardView cardView;
        public ViewHolderOrder(@NonNull View itemView) {
            super(itemView);
            phone=itemView.findViewById(R.id.tv_order_phone);
            address=itemView.findViewById(R.id.tv_order_address);
            order_id=itemView.findViewById(R.id.tv_order_id);
            status=itemView.findViewById(R.id.tv_order_stut);
            cardView=itemView.findViewById(R.id.order_card);
            cardView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("select Action");
            contextMenu.add(getAdapterPosition(),101,101,"delete");
        }
    }
}
