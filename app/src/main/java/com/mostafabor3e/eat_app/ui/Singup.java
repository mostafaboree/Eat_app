package com.mostafabor3e.eat_app.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mostafabor3e.eat_app.Model.User;
import com.mostafabor3e.eat_app.R;

import io.paperdb.Paper;

public class Singup extends AppCompatActivity {
Button sing;
EditText password,phone;
com.rey.material.widget.CheckBox checkBox;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        sing=findViewById(R.id.bt_sing_sing);
        password=findViewById(R.id.edit_password);
        phone=findViewById(R.id.edit_phone_number);
        checkBox= findViewById(R.id.ch_box);
        Paper.init(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("User");
        sing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()){
                    Paper.book().write("user",phone.getText().toString());
                    Paper.book().write("password",password.getText().toString());
                }
                final ProgressDialog dialog=new ProgressDialog(Singup.this);
                dialog.setMessage("please wait.............");
                dialog.show();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(phone.getText().toString()).exists()){
                            User user=snapshot.child(phone.getText().toString()).getValue(User.class);

                            if (user.getPassword().equals(password.getText().toString())){
                                Toast.makeText(Singup.this, "success", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                                editor.putString("phone",phone.getText().toString());
                                editor.apply();

                                dialog.dismiss();
                                Intent intent=new Intent(getBaseContext(),
                                        Home.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(Singup.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                        else {
                            Toast.makeText(Singup.this, "not find the mail", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}