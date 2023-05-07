package com.example.lab7v2;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class ProfileData {
    private String name;
    private String quote;
    private String imageLocation;
    private String status;
    private ArrayList<String> friends = new ArrayList<>();
    public ProfileData(){

    }
    public void setName(String name){
        this.name=name;
    }
    public void setQuote(String quote){
        this.quote=quote;
    }
    public void setPicture(String picture){
        this.imageLocation=picture;
    }
    public String getName(){
        return name;
    }
    public String getQuote(){
        return quote;
    }
    public Image getImage(){
        return new Image(imageLocation);
    }
    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return status;
    }
    public void addFriend(String friend){
        friends.add(friend);
    }
    public void removeFriend(String friend){
        friends.remove(friend);
    }
    public ArrayList<String> getFriend(){
        return friends;
    }
    @Override
    public String toString() {
        return "Profile "+name+" with the quote "+quote+" and status "+status;
    }
}
