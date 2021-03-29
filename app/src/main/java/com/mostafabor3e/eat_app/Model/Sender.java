package com.mostafabor3e.eat_app.Model;

public class Sender {
    public  String to;
    public Notification notification;

    public Sender(String to, Notification notification) {
        this.to = to;
        this.notification = notification;
    }

    public Sender() {
    }
}
