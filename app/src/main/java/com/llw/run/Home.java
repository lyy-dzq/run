package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;

import com.google.android.material.appbar.AppBarLayout;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button bt3=findViewById(R.id.manpao);
        Button bt4=findViewById(R.id.free_run);
        int cx=bt3.getWidth()/2;
        int cy=bt3.getHeight()/2;
        float radius=bt3.getWidth();
        float endius=radius*3/4;
        int start=0;
//        for (int i =start; i <=start+1; i++){
            Animator anim= ViewAnimationUtils.createCircularReveal(bt3,cx,cy,radius,endius);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
            anim.start();
//
//            Animator anim2= ViewAnimationUtils.createCircularReveal(bt3,cx,cy,endius,radius);
//            anim.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    super.onAnimationEnd(animation);
//                }
//            });
//            anim.start();
//        }

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

}