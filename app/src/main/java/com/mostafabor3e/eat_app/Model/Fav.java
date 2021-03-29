package com.mostafabor3e.eat_app.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fav")
public class Fav {
    private String name;
    private String menuId;
    @PrimaryKey(autoGenerate = true)
    private long idf;

    public Fav() {
    }

    public Fav(String name, String menuId) {
        this.name = name;
        this.menuId = menuId;
    }

    public Fav(String name, String menuId, long idf) {
        this.name = name;
        this.menuId = menuId;
        this.idf = idf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public long getIdf() {
        return idf;
    }

    public void setIdf(long idf) {
        this.idf = idf;
    }
}
