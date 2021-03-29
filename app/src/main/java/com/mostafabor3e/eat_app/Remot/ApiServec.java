package com.mostafabor3e.eat_app.Remot;

import com.mostafabor3e.eat_app.Model.Response;
import com.mostafabor3e.eat_app.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServec {


        @Headers(
                {
                        "Content-Type:application/json",
                        "Authorization:key=AAAArQePlOg:APA91bEClnzJPZIGcECgtnthrpT_proFFKjvEBkHb6_ujlW856IGTJwqvjGPfobYAje7o0bEYA5J1TYGKBq0uLpz4rdJRAFH8zNirxHCPXnBNeM8jyyFBn314NFU1I_SnIffxLitGW-4"

                }
        )
        @POST("fcm/send")
        Call<Response> sendNotifacation (@Body Sender sender );
    }


