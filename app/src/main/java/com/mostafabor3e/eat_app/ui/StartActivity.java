package com.mostafabor3e.eat_app.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mostafabor3e.eat_app.Model.User;
import com.mostafabor3e.eat_app.R;

import io.paperdb.Paper;

public class StartActivity extends AppCompatActivity {
Button login,sinIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        login=findViewById(R.id.login);
        sinIn=findViewById(R.id.bt_sinIn);
        Paper.init(this);
        sinIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),Singup.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),Login.class);
                startActivity(intent);

            }
        });
        String phone=Paper.book().read("user");
        String password=Paper.book().read("password");
        if (phone!=null&&password!=null){
            if (!phone.isEmpty()&&!password.isEmpty()) {
                  login(phone,password);
            }
        }
    }

    private void login(final String phone, final String password) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User");
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("please wait.............");
        dialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(phone).exists()){
                    User user=snapshot.child(phone).getValue(User.class);

                    if (user.getPassword().equals(password)){
                        //Toast.makeText(Singup.this, "success", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                        editor.putString("phone",phone);
                        editor.apply();

                        dialog.dismiss();
                        Intent intent=new Intent(getBaseContext(),
                                Home.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                       // Toast.makeText(Singup.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
                else {
                    //Toast.makeText(, "not find the mail", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


        }
