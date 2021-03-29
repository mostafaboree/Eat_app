package com.mostafabor3e.eat_app.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mostafabor3e.eat_app.Adapter.AdapterOrder;
import com.mostafabor3e.eat_app.Model.Request;
import com.mostafabor3e.eat_app.R;

import java.util.ArrayList;
import java.util.List;

public class Order extends AppCompatActivity {
    private static final String TAG ="Order" ;
    List<Request> orderes;
   RecyclerView recyclerView;
   DatabaseReference order;
   AdapterOrder adapterOrder;
   String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        orderes= new ArrayList<>();
        recyclerView=findViewById(R.id.rec_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);
        getOrder();
        SharedPreferences preferences=getSharedPreferences("PREFS",MODE_PRIVATE);
        phone=preferences.getString("phone","none");

    }
    public void getOrder(){
        order= FirebaseDatabase.getInstance()
                .getReference("Request");
        order.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderes.clear();
                for(DataSnapshot id:snapshot.getChildren()){
                    Request request=id.getValue(Request.class);
                    String order_id=id.getKey();
                    request.setId(order_id);
                   String statues= ConvertCodeToStatu(request.getStute());
                   request.setStute(statues);
                    if (request.getPhone().equals(phone)){
                    orderes.add(request);
                    }

                }
                adapterOrder=new AdapterOrder(orderes,getBaseContext());
                recyclerView.setAdapter(adapterOrder);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public String ConvertCodeToStatu(String status){
        if (status.equals("0"))
            return "placed";

        else if (status.equals("1"))
            return "On My Way";

        else return "Shipped";

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        order= FirebaseDatabase.getInstance()
                .getReference("Request");

        return super.onContextItemSelected(item);
    }
}