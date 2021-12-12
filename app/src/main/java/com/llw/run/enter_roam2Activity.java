package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
public class enter_roam2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_enter_roam2);
        final Data app = (Data)getApplication();

        Button enterroam=findViewById(R.id.enterroam);
        enterroam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float data= (float) 0;
                app.setJixu(data);
                Intent intent = new Intent(enter_roam2Activity.this, tian_an_menActivity.class);
                startActivity(intent);
            }
        });
        //继续漫跑
        Button enterroam2=findViewById(R.id.enterroam2);
        enterroam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递数据

                float data= (float) 34.5;
                app.setJixu(data);

                //跳转
                Intent intent = new Intent(enter_roam2Activity.this, tian_an_menActivity.class);
                startActivity(intent);
            }
        });
        //选择框
        Spinner s=(Spinner)findViewById(R.id.spinner1);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String result = parent.getItemAtPosition(position).toString();
                if(result=="1:2"){
                    app.setA("1:2");
                }else if(result=="1:5"){
                    app.setA("1:5");
                }else if(result=="1:10"){
                    app.setA("1:10");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //选择是否打开语音播报
        RadioGroup on=(RadioGroup)findViewById(R.id.group);
        on.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == findViewById(R.id.on).getId()) {
                    app.setA2("开");
                }
            }
        });
    }
}