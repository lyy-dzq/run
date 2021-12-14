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

public class qian_mActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qian_m);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        final Data app = (Data)getApplication();

        EditText qm=findViewById(R.id.qian_ming);

        Button saveqm=findViewById(R.id.save_qm);
        saveqm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            String JSON_URL="http://10.21.234.20:8080/";
                            JSON_URL=JSON_URL+app.getUid()+"/insertSigNatureByUid?"+"sigNature="+qm.getText().toString();
                            Log.d("签名", JSON_URL);
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
                            Log.d("签名",result);
                            if(result.equals("更新成功")){
                                Intent intent = new Intent(qian_mActivity.this, ge_renActivity.class);
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