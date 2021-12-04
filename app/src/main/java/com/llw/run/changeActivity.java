package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class changeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        EditText new_mima=findViewById(R.id.new_mima);
        Button change=findViewById(R.id.change_mima);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将修改后密码保存到后端
            }
        });
    }
}