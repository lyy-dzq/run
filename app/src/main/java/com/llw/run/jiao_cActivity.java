package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class jiao_cActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_jiao_c);
    }
}