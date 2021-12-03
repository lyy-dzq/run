package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class xzActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_xz);
    }
}