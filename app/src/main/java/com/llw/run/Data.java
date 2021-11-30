package com.llw.run;

import android.app.Application;

public class Data extends Application {
    private String a;
    private String a2;

    public String getA(){
        return this.a;
    }
    public void setA(String s){
        this.a= s;
    }
    public String getA2(){
        return this.a2;
    }
    public void setA2(String s){
        this.a2= s;
    }
    @Override
    public void onCreate(){
        a = "1:1";
        a2="关";
        super.onCreate();
    }

}
