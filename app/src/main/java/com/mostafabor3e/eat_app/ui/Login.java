package com.mostafabor3e.eat_app.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mostafabor3e.eat_app.Model.User;
import com.mostafabor3e.eat_app.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Login extends AppCompatActivity {
MaterialEditText name,password,phone;
Button singIn;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
SharedPreferences.Editor phoneuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name=findViewById(R.id.edit_name_singin);
        password=findViewById(R.id.edit_password_singIn);
        phone=findViewById(R.id.edit_phone_number_singin);
        singIn=findViewById(R.id.bt_sing_singin);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("User");
        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog dialog=new ProgressDialog(Login.this);
                dialog.setMessage("please wait.............");
                dialog.show();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(phone.getText().toString()).exists()){
                            dialog.dismiss();
                            Toast.makeText(Login.this, "phoneNumber aready register", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            dialog.dismiss();
                            User user=new User(name.getText().toString(),password.getText().toString());
                            user.setPhone(phone.getText().toString());
                            databaseReference.child(phone.getText().toString()).setValue(user);

                            Toast.makeText(Login.this, "sin up", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getBaseContext(),Singup.class);
                            startActivity(intent);
                            finish();
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