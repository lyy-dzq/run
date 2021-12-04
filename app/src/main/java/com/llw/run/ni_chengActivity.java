package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ni_chengActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        final Data app = (Data)getApplication();
        setContentView(R.layout.activity_ni_cheng);
        EditText nic=findViewById(R.id.ni_cheng);
        Button bt=findViewById(R.id.save_nc);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.setinto(nic.getText().toString());
            }
        });
    }

}