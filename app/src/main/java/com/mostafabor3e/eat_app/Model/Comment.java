package com.mostafabor3e.eat_app.Model;

public class Comment {
    private String comment;
    private Float rating;
    private String userPhone;
    private  String food;
    private String id;

    public Comment() {
    }

    public Comment(String comment, Float rating, String userPhone, String food, String id) {
        this.comment = comment;
        this.rating = rating;
        this.userPhone = userPhone;
        this.food = food;
        this.id = id;
    }

    public Comment(String comment, Float rating, String userPhone, String food) {
        this.comment = comment;
        this.rating = rating;
        this.userPhone = userPhone;
        this.food = food;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}
