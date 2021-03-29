package com.mostafabor3e.eat_app.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mostafabor3e.eat_app.Adapter.AdapterFood;
import com.mostafabor3e.eat_app.Adapter.CartAdapter;
import com.mostafabor3e.eat_app.Model.FoodM;
import com.mostafabor3e.eat_app.Model.Notification;
import com.mostafabor3e.eat_app.Model.Request;
import com.mostafabor3e.eat_app.Model.Response;
import com.mostafabor3e.eat_app.Model.Sender;
import com.mostafabor3e.eat_app.Model.Token;
import com.mostafabor3e.eat_app.Model.User;
import com.mostafabor3e.eat_app.R;
import com.mostafabor3e.eat_app.Remot.ApiServec;
import com.mostafabor3e.eat_app.RooM.RoomFactory;

import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;

public class Cart extends AppCompatActivity {
    RoomFactory roomFactory;
    List<FoodM>mList;
    CartAdapter adapterFood;
    RecyclerView recyclerView;
    Button btcart;
    FirebaseUser firebaseUser;
    DatabaseReference Request;
    String phone;
    User user1;
    ImageView delet,add,sub;
    TextView total,quentity;
    public static int count;
    ApiServec apiServec;

    int sum;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        delet=findViewById(R.id.cart_clear);
        btcart=findViewById(R.id.bt_cart);
        recyclerView=findViewById(R.id.rec_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);
        mList=new ArrayList<>();
        total=findViewById(R.id.tv_totel_price_cart);totalPrise();
        totalPrise();

        apiServec=Commen.getFcm();

        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Clear();
                get();
                adapterFood.notifyDataSetChanged();

            }
        });
        SharedPreferences  preferences=getSharedPreferences("PREFS",MODE_PRIVATE);
        phone=preferences.getString("phone","none");
        DatabaseReference userdata=FirebaseDatabase.getInstance().getReference("User").child(phone);
        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              User user=snapshot.getValue(User.class);
              user1=new User(user.getName(),user.getPassword(),user.getPhone());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //get data from room
        get();



       // Toast.makeText(this, ""+user1.getName().toString(), Toast.LENGTH_SHORT).show();



        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Request= FirebaseDatabase.getInstance().getReference("Request");
        btcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAlart();

            }
        });
    }
    private void get(){
        mList.clear();
        roomFactory=RoomFactory.getInstace(getBaseContext());
        roomFactory.dataAcess().get().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<FoodM>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(final List<FoodM> foodMS) {
                //Toast.makeText(Cart.this, "m", Toast.LENGTH_SHORT).show();
               adapterFood=new CartAdapter(foodMS,getBaseContext());
               mList=foodMS;
               count=foodMS.size();
                recyclerView.setAdapter(adapterFood);
                adapterFood.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(Cart.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    public  void ShowAlart(){
        final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("One more Step ! ");
        dialog.setMessage("Enter your Address  ");
        dialog.setIcon(R.drawable.ic_cart);
        final EditText editText=new EditText(Cart.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(layoutParams);
        dialog.setView(editText);
        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Request request=new Request(user1.getPhone(),user1.getName()
                        ,editText.getText().toString(),mList,"2000$");

                Request.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                Toast.makeText(Cart.this, "Thank you , Order Place", Toast.LENGTH_SHORT).show();
                Clear();
                finish();
                adapterFood.notifyDataSetChanged();
                String order_id=String.valueOf(System.currentTimeMillis());
                senNotifiactionOrder(order_id);



            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        dialog.show();

    }

    private void senNotifiactionOrder(final String order_id) {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Tokens");
        Query data=databaseReference.orderByChild("istoken").equalTo(true);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Token token=dataSnapshot.getValue(Token.class);

                    Notification  notification=new Notification("  You have new order  "+order_id,"  Eat App");
                    Sender sender=new Sender(token.getTekon(),notification);
                    apiServec.sendNotifacation(sender).enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            if (response.isSuccessful())
                                Toast.makeText(Cart.this, "thank you order place holder", Toast.LENGTH_SHORT).show();
                                
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            Toast.makeText(Cart.this, "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Clear(){
        roomFactory=RoomFactory.getInstace(getBaseContext());
        roomFactory.dataAcess().Clear().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(Cart.this, "clear", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("delete")){
            deleteItem(mList.get(item.getOrder()).getId());

        }
        return super.onContextItemSelected(item);
    }

    private void deleteItem(long id) {
        roomFactory.dataAcess().deleteItem(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(Cart.this, "success delete", Toast.LENGTH_SHORT).show();
                adapterFood.notifyDataSetChanged();
                get();

            }

            @Override
            public void onError(Throwable e) {

            }
        });
        
    }
    public void   totalPrise(){
        if (mList.size()>0){
           // Toast.makeText(this, "not", Toast.LENGTH_SHORT).show();
        total.setText( Integer.parseInt(mList.get(1).getPrice()));
        }
    }
}