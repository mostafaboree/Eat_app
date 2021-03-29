package com.mostafabor3e.eat_app.Model;

import java.util.List;

public class Request {
    private String phone;
    private String name;
    private String address;
    private List<FoodM>foods;
    private String stute;
    private String total;
    private String id;

    public Request() {
    }

    public Request(String phone, String name, String address, List<FoodM> foods,String total) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.foods = foods;
        this.total=total;
        this.stute="0";
    }
    public Request(String phone, String address,String id) {
        this.phone = phone;
        this.address = address;
        this.id=id;
        this.stute="0";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStute() {
        return stute;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setStute(String stute) {
        this.stute = stute;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FoodM> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodM> foods) {
        this.foods = foods;
    }
}
