package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
   public int a=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        final Data app = (Data)getApplication();
        setContentView(R.layout.activity_main);
        Button bt1 = findViewById(R.id.denglu);
        Button bt2 = findViewById(R.id.zhuce);
       final EditText user = findViewById(R.id.user);
       final EditText password = findViewById(R.id.password);
        TextView bt3=findViewById(R.id.forgetmima);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username1 = user.getText().toString();
                String password1 = password.getText().toString();
                String ok = "登录成功";
                String fail = "登录失败";
                if (username1.equals("152") && password1.equals("123")) {
                    app.setinto(username1);
                    Toast.makeText(MainActivity.this,ok, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, BottomNavigationActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this,fail,Toast.LENGTH_SHORT).show();
                }
            }
        });
       bt2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, ZhuCeActivity.class);
               startActivity(intent);
           }
       });
       bt3.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, ZhaohuiMimaActivity.class);
               startActivity(intent);
           }
       });

    }
}