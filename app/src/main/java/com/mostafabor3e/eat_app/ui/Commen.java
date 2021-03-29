package com.mostafabor3e.eat_app.ui;

import android.net.Uri;

import com.mostafabor3e.eat_app.Remot.ApiServec;
import com.mostafabor3e.eat_app.Remot.RetrioftClient;

public class Commen {
    public static final String baseUrl="https://fcm.googleapis.com";


    public static ApiServec getFcm(){
     return    RetrioftClient.getClient(baseUrl).create(ApiServec.class);
    }



    public static  String ConvertCodeToStatu(String status){
        if (status.equals("0"))
            return "placed";

        else if (status.equals("1"))
            return "On My Way";

        else return "Shipped";

    }

}
