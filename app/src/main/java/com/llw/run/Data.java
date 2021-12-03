package com.llw.run;

import android.app.Application;

public class Data extends Application {
    private String suer_into;
    private String a;  //西湖
    private String a2;
    private String b;  //天安门
    private String b2;
    //用户名
    public String getinto(){
        return this.suer_into;
    }
    public void setinto(String s){
        this.suer_into= s;
    }
    //西湖
    public String getA(){
        return this.a;
    }  //比例
    public void setA(String s){
        this.a= s;
    }
    public String getA2(){
        return this.a2;
    }   //语音开关
    public void setA2(String s){
        this.a2= s;
    }
    //天安门
    public String getB(){
        return this.b;
    }  //比例
    public void setB(String s){ this.b= s;}
    public String getB2(){return this.b2; }   //语音开关
    public void setB2(String s){ this.b2= s; }
    @Override
    public void onCreate(){
        a = "1:1";
        a2="关";
        b = "1:1";
        b2="关";
        super.onCreate();
    }

}
