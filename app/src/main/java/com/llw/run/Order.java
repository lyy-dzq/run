package com.llw.run;

public class Order {
    private String username;
    private int imageId;
    private String mile;
    public Order(String username,int imageId,String mile){
        this.username=username;
        this.imageId=imageId;
        this.mile=mile;
    }
    public String getUsername(){
        return username;
    }
    public int getImageId(){
        return imageId;
    }
    public  String getMile(){
        return mile;
    }
}
