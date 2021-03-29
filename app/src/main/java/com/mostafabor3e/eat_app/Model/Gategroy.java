package com.mostafabor3e.eat_app.Model;

public class Gategroy {
    private String name;
    private  String image;
    private String Key;

    public Gategroy() {
    }

    public Gategroy(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
