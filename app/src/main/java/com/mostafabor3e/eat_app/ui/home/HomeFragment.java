package com.mostafabor3e.eat_app.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mostafabor3e.eat_app.Adapter.AdapterGategroy;
import com.mostafabor3e.eat_app.Model.Gategroy;
import com.mostafabor3e.eat_app.R;
import com.mostafabor3e.eat_app.ui.Food;
import com.mostafabor3e.eat_app.ui.SetonClickLisener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    List<Gategroy> gategroyList;
    AdapterGategroy adapterGategroy;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPreferences.Editor editor;
    Button fab;
    ProgressBar progressDialog;
    SwipeRefreshLayout refreshLayout;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=root.findViewById(R.id.rec_home);
        fab=root.findViewById(R.id.fab);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressDialog=root.findViewById(R.id.p_load);
        refreshLayout=root.findViewById(R.id.refersh_home);
        gategroyList=new ArrayList<>();
        progressDialog.setVisibility(View.VISIBLE);





        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoadFood();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LayoutAnimationController controller= AnimationUtils.loadLayoutAnimation(getContext(),R.anim.animlayout);
                recyclerView.setLayoutAnimation(controller);
                LoadFood();
            }
        });

    }

public void LoadFood(){
    firebaseDatabase=FirebaseDatabase.getInstance();

    databaseReference=firebaseDatabase.getReference("Category");

    databaseReference.addValueEventListener(new ValueEventListener() {
        @SuppressLint("RestrictedApi")
        @Override
        public void onDataChange(@NonNull final DataSnapshot snapshot) {
            gategroyList.clear();
            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                Gategroy gategroy=dataSnapshot.getValue(Gategroy.class);
                assert gategroy != null;
                gategroy.setKey(dataSnapshot.getKey());
                gategroyList.add(gategroy);
                progressDialog.setVisibility(View.INVISIBLE);

            }
            adapterGategroy=new AdapterGategroy(gategroyList,getContext(),
                    new SetonClickLisener() {
                        @Override
                        public void onclick(View veiw, int postion, boolean isLongClick) {
                            Intent intent=new Intent(getContext(), Food.class);
                            String id=gategroyList.get(postion).getKey();
                            SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS",getContext().MODE_PRIVATE).edit();
                            editor.putString("user_id",id);
                            editor.putString("name",gategroyList.get(postion).getName());
                            editor.apply();

                            intent.putExtra("Item_id",id);
                            startActivity(intent);
                        }
                    });
            recyclerView.setAdapter(adapterGategroy);

            adapterGategroy.notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
            refreshLayout.setRefreshing(false);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

}

}