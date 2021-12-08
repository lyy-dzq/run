package com.llw.run;

import android.graphics.Bitmap;

public class Order {
    private String username;
    private Bitmap imageId;
    private String mile;
    public Order(String username,Bitmap imageId,String mile){
        this.username=username;
        this.imageId=imageId;
        this.mile=mile;
    }
    public String getUsername(){
        return username;
    }
    public Bitmap getImageId(){
        return imageId;
    }
    public  String getMile(){
        return mile;
    }
}
