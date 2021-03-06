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
                    showMsg("????????????????????????");
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
                            //??????httpurlconnection
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");              //??????POST??????????????????
                            conn.setConnectTimeout(5000);              //????????????????????????5???
                            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  //?????????????????????post???????????????????????????
                            //?????????????????????,????????????????????????????????????post???????????????
                            String data="email="+ URLEncoder.encode(mQq2,"utf-8")+"&password="+ URLEncoder.encode(mPwd2,"utf-8");
//                            String data="userID="+ URLEncoder.encode("123456","utf-8")+"&userPwd="+URLEncoder.encode("123456","utf-8")+"&userName="+URLEncoder.encode("123456","utf-8")+"&phoneNumber="+URLEncoder.encode("123456","utf-8");
                            //????????????
                            conn.setRequestProperty("Content-length",String.valueOf(data.length()));
                            conn.setDoOutput(true); //??????????????????
                            conn.getOutputStream().write(data.getBytes());  //????????????????????????????????????
                            int code = conn.getResponseCode();         // ??????response?????????200???????????????????????????404?????????????????????
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
                                Log.d("??????", result);
                                app.setinto(mQq2);

                                if(!result.equals("???????????????")&&!result.equals("????????????")){
                                    app.setUid(result);
                                    Intent intent = new Intent(MainActivity.this, BottomNavigationActivity.class);
                                    startActivity(intent);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("ZhuCeActivity", "cuowu");

//                            showMsg("?????????");


                        }
                    }
                }.start();





            }
        });

                //??????
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