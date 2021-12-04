package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class qian_mActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qian_m);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        EditText qm=findViewById(R.id.qian_ming);
        Button saveqm=findViewById(R.id.save_qm);
        saveqm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}