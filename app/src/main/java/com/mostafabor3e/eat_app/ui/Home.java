package com.mostafabor3e.eat_app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.andremion.counterfab.CounterFab;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mostafabor3e.eat_app.Model.FoodM;
import com.mostafabor3e.eat_app.Model.Token;
import com.mostafabor3e.eat_app.R;
import com.mostafabor3e.eat_app.RooM.RoomFactory;
import com.mostafabor3e.eat_app.Service.FcmMessagingService;
import com.mostafabor3e.eat_app.ui.Cart;
import com.mostafabor3e.eat_app.ui.Food;
import com.mostafabor3e.eat_app.ui.StartActivity;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import io.paperdb.Paper;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    CounterFab fab;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);


        Toolbar toolbar = findViewById(R.id.toolbar);
        Paper.init(getBaseContext());
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);
         fab = findViewById(R.id.fab);
        //fab.setCount(5);
        fab.setCount(Cart.count);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(), Cart.class);
                startActivity(intent);
            }
        });
        get();

        // start service

        Intent intt=new Intent(getBaseContext(), FcmMessagingService.class);
        startService(intt);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        Paper.init(getBaseContext());
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.order,R.id.logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        updateTaken(FirebaseInstanceId.getInstance().getToken());


    }

    private void updateTaken(String token) {
        SharedPreferences preferences=getSharedPreferences("PREFS",MODE_PRIVATE);
        String phone=preferences.getString("phone","none");
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("Tokens");
        Token data=new Token(token,false);
        reference.child(phone).setValue(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Logout")){
            Paper.book().destroy();
            Intent intent1 = new Intent(getBaseContext(), StartActivity.class);
            startActivity(intent1);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent(getBaseContext(), Food.class);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_gallery:
                Intent in = new Intent(getBaseContext(), Cart.class);
                startActivity(in);
                finish();

            case R.id.logout:
                Paper.book().destroy();
                Intent intent1 = new Intent(getBaseContext(), StartActivity.class);
                startActivity(intent1);
            default:
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void get(){
        //final int count;
        fab.setCount(0);
      RoomFactory  roomFactory= RoomFactory.getInstace(getBaseContext());
        roomFactory.dataAcess().get().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<FoodM>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(final List<FoodM> foodMS) {
                //Toast.makeText(Cart.this, "m", Toast.LENGTH_SHORT).show();
               // count=foodMS.size();
                fab.setCount(foodMS.size());



            }

            @Override
            public void onError(Throwable e) {

            }
        });


    }

}