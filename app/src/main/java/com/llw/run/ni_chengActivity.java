package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            String JSON_URL="http://101.35.202.198:8080/";
                            JSON_URL=JSON_URL+app.getUid()+"/insertUserNameByUid?"+"username="+nic.getText().toString();
                            Log.d("昵称", JSON_URL);
                            URL url = new URL(JSON_URL);
                            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                            urlConn.setRequestMethod("GET");
                            urlConn.setConnectTimeout(80000);
                            urlConn.setReadTimeout(80000);
//                    int code = urlConn.getResponseCode();
//                    if (code==200){
                            InputStream is=urlConn.getInputStream();
                            BufferedReader br=new BufferedReader(new InputStreamReader(is));
                            StringBuffer sb=new StringBuffer();
                            String len=null;
                            while((len=br.readLine())!=null){
                                sb.append(len);
                            }
                            String result=sb.toString();
                            Log.d("",result);
                            if(result.equals("更新成功")){
                                Intent intent = new Intent(ni_chengActivity.this, ge_renActivity.class);
                                startActivity(intent);
                            }
                        } catch (Exception e) {
                            Log.d("跑", "....");
                            e.printStackTrace();

                        }
                    }
                }.start();
            }
        });
    }

}