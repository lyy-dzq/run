package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class leiji_Activity extends AppCompatActivity {
    private CircleScaleView circleScaleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_leiji_);
        circleScaleView = (CircleScaleView) findViewById(R.id.circleScaleView);
        if (circleScaleView != null) {
            circleScaleView.setCirclePercent(1, 1);
        }
    }

}