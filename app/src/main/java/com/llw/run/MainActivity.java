package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    public int a=1;
    private String mQq2;
    private String mPwd2;
    private static final int LOAD_SUCCESS =1 ;
    private static final int LOAD_ERROR =2 ;
    private EditText mQqNum2;
    private EditText mQqPwd2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        mQqNum2= (EditText) findViewById(R.id.user2);
        mQqPwd2 = (EditText) findViewById(R.id.password2);
        final Data app = (Data)getApplication();
        Button bt1 = findViewById(R.id.denglu);
        Button bt2 = findViewById(R.id.zhuce);
        TextView bt3=findViewById(R.id.forgetmima);

        //
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mQq2 = mQqNum2.getText().toString().trim();
//                mPwd2 = mQqPwd2.getText().toString().trim();
                //mCb_rember.getText().toString().trim();
                mQq2=mQqNum2.getText().toString().trim();
                mPwd2=mQqPwd2.getText().toString().trim();
                if (TextUtils.isEmpty(mQq2) || TextUtils.isEmpty(mPwd2)) {
                    showMsg("账号或者密码为空");
                }
//                bt1.setEnabled(false);
                new Thread(){
                    @Override
                    public void run() {
                        try {
//                    Thread.sleep(5000);
//                            String path="http://47.113.226.119:8080/register";
                            String path="http://101.35.202.198:8080/login";
                            URL url = new URL(path);
                            //打开httpurlconnection
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");              //设置POST方式获取数据
                            conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                            //将数据进行编码,然后会自动的将该数据放到post中传到后台
                            String data="email="+ URLEncoder.encode(mQq2,"utf-8")+"&password="+ URLEncoder.encode(mPwd2,"utf-8");
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

//                                showMsg(result);
                                Log.d("登陆", result);
                                app.setinto(mQq2);

                                if(!result.equals("邮箱不存在")&&!result.equals("密码错误")){
                                    app.setUid(result);
                                    Intent intent = new Intent(MainActivity.this, BottomNavigationActivity.class);
                                    startActivity(intent);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("ZhuCeActivity", "cuowu");

//                            showMsg("不成功");


                        }
                    }
                }.start();





            }
        });

                //注册
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

            private void showMsg(String msg) { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }

}