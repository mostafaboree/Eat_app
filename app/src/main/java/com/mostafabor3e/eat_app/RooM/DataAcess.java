package com.mostafabor3e.eat_app.RooM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mostafabor3e.eat_app.Model.Fav;
import com.mostafabor3e.eat_app.Model.FoodM;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface DataAcess {
    @Insert
    Completable insert(FoodM entity);

    @Query("Select * From food ")
    Single<List<FoodM>> get();

    @Query("Delete  From food ")
    Completable Clear();






    @Query("Delete From food Where id= :id")
    Completable deleteItem(long id);

    @Insert
    Completable insetId(Fav fav);

    @Query("Select * From fav ")
    Single<List<Fav>> getfav();

    @Query("Delete From fav Where name=:id")
    Completable notFav(String id);

    @Query("Update food Set  quentity = :quen Where id=:id")
    Completable update(int quen,long id);

}
