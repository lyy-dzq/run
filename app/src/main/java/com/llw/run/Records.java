package com.llw.run;

import android.graphics.Bitmap;

public class Records {
    private String time;
    private String type;
    private String glis;
    private String schangs;
    public Records(String time,String type,String glis,String schangs){
        this.time=time;
        this.type=type;
        this.glis=glis;
        this.schangs=schangs;
    }
    public String getTime(){
        return time;
    }
    public String getType(){
        return type;
    }
    public  String getGlis(){
        return glis;
    }
    public  String getSchangs(){
        return schangs;
    }
}
