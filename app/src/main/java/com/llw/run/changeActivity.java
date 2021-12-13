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
import java.net.URLEncoder;

public class changeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        final Data app = (Data)getApplication();
        EditText new_mima=findViewById(R.id.new_mima);
        Button change=findViewById(R.id.change_mima);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //将修改后密码保存到后端
                new Thread(){
                    @Override
                    public void run() {
                        try {
//                    Thread.sleep(5000);
//                            String path="http://47.113.226.119:8080/register";
                            String path="http://10.21.234.20:8080/"+app.getUid()+"/update";
                            URL url = new URL(path);
                            //打开httpurlconnection
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");              //设置POST方式获取数据
                            conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                            //将数据进行编码,然后会自动的将该数据放到post中传到后台
                            String data="password="+ URLEncoder.encode(new_mima.getText().toString().trim(),"utf-8");
//                            String data="userID="+ URLEncoder.encode("123456","utf-8")+"&userPwd="+URLEncoder.encode("123456","utf-8")+"&userName="+URLEncoder.encode("123456","utf-8")+"&phoneNumber="+URLEncoder.encode("123456","utf-8");
                            //指定长度
                            conn.setRequestProperty("Content-length",String.valueOf(data.length()));
                            conn.setDoOutput(true); //指定输出模式
                            conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
                            int code = conn.getResponseCode();         // 获取response状态，200表示成功获取资源，404表示资源不存在
                            if (code==200){
                                InputStream is=conn.getInputStream();
                                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                                StringBuffer sb=new StringBuffer();
                                String len=null;
                                while((len=br.readLine())!=null){
                                    sb.append(len);
                                }

                                String result=sb.toString();
                                Log.d("更新密码", result);
                                Intent intent = new Intent(changeActivity.this, zhang_haoActivity.class);
                                startActivity(intent);
//                                showMsg(result);
                            }
                        } catch (Exception e) {
                            Log.d("ZhuCeActivity", ".");

                            e.printStackTrace();
                            Log.d("ZhuCeActivity", ".");
//                            showMsg("不成功");
                        }
                    }
                }.start();
            }
        });
    }
}