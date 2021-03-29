package com.mostafabor3e.eat_app.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mostafabor3e.eat_app.Adapter.AdapterFood;
import com.mostafabor3e.eat_app.Adapter.AdapterGategroy;
import com.mostafabor3e.eat_app.Model.Fav;
import com.mostafabor3e.eat_app.Model.FoodM;
import com.mostafabor3e.eat_app.Model.Gategroy;
import com.mostafabor3e.eat_app.R;
import com.mostafabor3e.eat_app.RooM.RoomFactory;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Food extends AppCompatActivity  {
    RecyclerView recyclerView;
    List<FoodM> foodMList;
    List<Fav> favlist;
    AdapterFood adapterFood;
     List<String>suggestion;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText search;
    private MaterialSearchBar searchBar;
    String id;
    FloatingActionButton faCart;
    ProgressBar progressBar;
    TextView nameCategri,textcate;
    ImageView iconSearch,imageBackground;
   ImageView imageCategries;
   SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Intent intent=new Intent();
        progressBar=findViewById(R.id.p_loadfood);
        favlist=new ArrayList<>();
        search=findViewById(R.id.ed_search_ser);
        textcate=findViewById(R.id.textCategory);
        imageBackground=findViewById(R.id.imageCategoryBg);
        imageCategries=findViewById(R.id.imageCategory);
        progressBar.setVisibility(View.VISIBLE);
        suggestion=new ArrayList<>();
        nameCategri=findViewById(R.id.tv_name_geta);
        iconSearch=findViewById(R.id.iv_search_search);
        refreshLayout=findViewById(R.id.refersh_food);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFoodMenu();
            }
        });
        iconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setVisibility(View.VISIBLE);
                nameCategri.setVisibility(View.GONE);
            }
        });




        foodMList=new ArrayList<>();
        recyclerView=findViewById(R.id.rec_food);
        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
        recyclerView.setClipToPadding(true);
        LayoutAnimationController controller= AnimationUtils.loadLayoutAnimation(this,R.anim.animlayout);
        recyclerView.setLayoutAnimation(controller);

        SharedPreferences preferences=getSharedPreferences("PREFS", Context.MODE_PRIVATE);
       id=preferences.getString("user_id","none");
       String name=preferences.getString("name","none");
       nameCategri.setText(name);
       getcategrois(id);
        adapterFood=new AdapterFood(foodMList,getBaseContext(),
                new SetonClickLisener() {
                    @Override
                    public void onclick(View veiw, int postion, boolean isLongClick) {

//
                          Intent intent=new Intent(getBaseContext(), FoodDetalis.class);
                          intent.putExtra("name",foodMList.get(postion));

                          startActivity(intent);

                    }
                });
        getFoodMenu();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SearchUser(charSequence.toString().toUpperCase());



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private void getFoodMenu(){
        if (search.getText().toString().equals("")) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Foods");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onDataChange(@NonNull final DataSnapshot snapshot) {
                    foodMList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        FoodM food = dataSnapshot.getValue(FoodM.class);
                        assert food != null;
                        if (food.getMenuId().equals(id)) {
                            foodMList.add(food);
                        }
                        //     Log.d(TAG, "onDataChange: "+gategroy.getName().toString());

                        //Toast.makeText(getC, "j"+gategroy.getKey(), Toast.LENGTH_SHORT).show();
                    }
                progressBar.setVisibility(View.INVISIBLE);

                    recyclerView.setAdapter(adapterFood);
                recyclerView.getAdapter().notifyDataSetChanged();
                    recyclerView.scheduleLayoutAnimation();
                    refreshLayout.setRefreshing(false);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


    private void SearchUser(String s){
        Query query=FirebaseDatabase.getInstance().getReference("Foods").orderByChild("name").startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                foodMList.clear();
                for (DataSnapshot snaop:snapshot.getChildren()){
                    FoodM item=snaop.getValue(FoodM.class);
                    assert item != null;
                    if (item.getMenuId().equals(id)) {
                        foodMList.add(item);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    suggestion.add(item.getName());
                }
                adapterFood.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void getcategrois(String id){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Category").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Gategroy gategroy=snapshot.getValue(Gategroy.class);
                Glide.with(getBaseContext()).load(gategroy.getImage()).into(imageBackground);
                Glide.with(getBaseContext()).load(gategroy.getImage()).into(imageCategries);
                textcate.setText(gategroy.getName());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}