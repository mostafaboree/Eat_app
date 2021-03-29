package com.mostafabor3e.eat_app.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "food")
public class FoodM implements Serializable {
    private String description;
    private String image;
    private String discount;
    private String name;
    private String menuId;
    private int quentity;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String price;

    public FoodM(String description, String image, String discount, String name, String menuId, String price) {
        this.description = description;
        this.image = image;
        this.discount = discount;
        this.name = name;
        this.menuId = menuId;
        this.price = price;
    }



    public FoodM(String description, String image, String discount, String name, String menuId, long id, String price) {
        this.description = description;
        this.image = image;
        this.discount = discount;
        this.name = name;
        this.menuId = menuId;
        this.id = id;
        this.price = price;
    }

    public FoodM(String description, String image, String discount, String name, String menuId, int quentity, long id, String price) {
        this.description = description;
        this.image = image;
        this.discount = discount;
        this.name = name;
        this.menuId = menuId;
        this.quentity = quentity;
        this.id = id;
        this.price = price;
    }

    public FoodM() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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

    public String getPrice() {
        return price;
    }

    public int getQuentity() {
        return quentity;
    }

    public void setQuentity(int quentity) {
        this.quentity = quentity;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
