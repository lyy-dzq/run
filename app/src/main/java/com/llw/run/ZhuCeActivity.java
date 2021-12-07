package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;
import android.os.Bundle;

import com.mob.MobSDK;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static java.util.Locale.US;

public class ZhuCeActivity extends AppCompatActivity   {
    String APPKEY = "34b103c906c35";
    String APPSECRETE = "54f47d66da37f091ead750c9e3c21416";

    // 获取验证码按钮
    // 注册按钮
    private Button sub;
    private String mQq;
    private String mPwd;
    private static final int LOAD_SUCCESS =1 ;
    private static final int LOAD_ERROR =2 ;
    private EditText mQqNum;
    private EditText mQqPwd;

    int i=30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.zhu_ce);
        //
        final Data app = (Data)getApplication();

        mQqNum= (EditText) findViewById(R.id.login_input_phone_et);
        mQqPwd = (EditText) findViewById(R.id.password);
        sub = (Button) findViewById(R.id.login_commit_btn);
        sub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mQq = mQqNum.getText().toString().trim();
                mPwd = mQqPwd.getText().toString().trim();
                //mCb_rember.getText().toString().trim();
                if (TextUtils.isEmpty(mQq)||TextUtils.isEmpty(mPwd)){
                    showMsg("账号或者密码为空");
                }
                sub.setEnabled(false);
                new Thread(){
                    @Override
                    public void run() {
                        try {
//                    Thread.sleep(5000);
//                            String path="http://47.113.226.119:8080/register";
                            String path="http://10.21.234.20:8080/register";
                            URL url = new URL(path);
                            //打开httpurlconnection
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");              //设置POST方式获取数据
                            conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                            //将数据进行编码,然后会自动的将该数据放到post中传到后台
                            String data="username="+ URLEncoder.encode(mQq,"utf-8")+"&password="+ URLEncoder.encode(mPwd,"utf-8");
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
                                Log.d("ZhuCeActivity", result);
                                app.setinto(mQq);
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

    }


    private void showMsg(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }



}