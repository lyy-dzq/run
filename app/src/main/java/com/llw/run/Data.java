package com.llw.run;

import android.app.Application;

import org.litepal.LitePal;

public class Data extends Application {
    private String suer_into;//用户名
    private String a;  //西湖
    private String a2;
    private String b;  //天安门
    private String b2;
    private String uid;//用户uid
    private float jixu; //是否继续漫跑标志
    private float jixulishu;//用户继续跑的上一次公里数
    private float jixulishu2;//用户继续跑的上一次公里数

    //继续漫跑
    public float getJixu(){return this.jixu;}
    public void setJixu(float s){ this.jixu=s;}

    //继续漫跑
    public float getJixulishu(){return this.jixulishu;}
    public void setJixulishu(float s){ this.jixulishu=s;}
    //继续漫跑
    public float getJixulishu2(){return this.jixulishu2;}
    public void setJixulishu2(float s){ this.jixulishu2=s;}
    //uid
    public String getUid(){
        return this.uid;
    }
    public void setUid(String s){
        this.uid= s;
    }
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
        jixulishu=0;
        uid=null;
        jixu=0;
        a = "1:1";
        a2="关";
        b = "1:1";
        b2="关";
        super.onCreate();
        LitePal.initialize(this);
    }

}
