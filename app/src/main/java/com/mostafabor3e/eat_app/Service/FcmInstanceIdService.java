package com.mostafabor3e.eat_app.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mostafabor3e.eat_app.Model.Token;

public class FcmInstanceIdService extends FirebaseInstanceIdService {
    String  phone;

    private static final String REG_TOKEN = "REG_TOKEN";
    @Override
    public void onTokenRefresh() {

        String recent_token= FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("FCM_TOKEN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FCM_TOKEN",recent_token);
        editor.apply();
        Log.d(REG_TOKEN,recent_token);
        UpdateTokenToFirbase(recent_token);

    }
    private void UpdateTokenToFirbase(String tokenRefershed){
        SharedPreferences preferences=getSharedPreferences("PREFS",MODE_PRIVATE);
        phone=preferences.getString("phone","none");
        FirebaseDatabase  database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("Tokens");
        Token token=new Token(tokenRefershed,false);
        reference.child(phone).setValue(token);

    }
}