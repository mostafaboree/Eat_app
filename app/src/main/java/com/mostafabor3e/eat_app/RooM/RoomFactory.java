package com.mostafabor3e.eat_app.RooM;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mostafabor3e.eat_app.Model.Fav;
import com.mostafabor3e.eat_app.Model.FoodM;

@Database(entities ={FoodM.class,Fav.class},version = 4)
public abstract class RoomFactory extends RoomDatabase {
    public static RoomFactory Instance;
    FoodM entitey;
    public abstract DataAcess dataAcess();
    public static RoomFactory getInstace(Context context){
        if (Instance ==null){
            Instance= Room.databaseBuilder(context.getApplicationContext(),RoomFactory.class,"DBN").fallbackToDestructiveMigration().build();
        }
        return Instance;

    }

}
