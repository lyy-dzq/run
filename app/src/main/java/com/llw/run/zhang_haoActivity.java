package com.llw.run;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class zhang_haoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_zhang_hao);
        LinearLayout change=findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(zhang_haoActivity.this, changeActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout delet=findViewById(R.id.delet);
        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 showdialog(delet);
            }
        });
    }
    //退出提示框
    public void showdialog(View view) {
        //定义一个新的对话框对象
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
        //设置对话框提示内容
        alertdialogbuilder.setMessage("确定要删除吗？");
        //定义对话框2个按钮标题及接受事件的函数
        alertdialogbuilder.setPositiveButton("确定",click1);
        alertdialogbuilder.setNegativeButton("取消",click2);
        //创建并显示对话框
        AlertDialog alertdialog1=alertdialogbuilder.create();
        alertdialog1.show();
    }
    private DialogInterface.OnClickListener click1=new DialogInterface.OnClickListener() {
        //使用该标记是为了增强程序在编译时候的检查，如果该方法并不是一个覆盖父类的方法，在编译时编译器就会报告错误。
        @Override
        public void onClick(DialogInterface arg0,int arg1)
        {
            //当按钮click1被按下时执行结束进程
            final Data app = (Data)getApplication();
            //调用后端删除账号
            new Thread(){
                @Override
                public void run() {
                    try {
//                    Thread.sleep(5000);
//                            String path="http://47.113.226.119:8080/register";
                        String path="http://10.21.234.20:8080/"+app.getUid()+"/delete";
                        URL url = new URL(path);
                        //打开httpurlconnection
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");              //设置POST方式获取数据
                        conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                        conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                        //将数据进行编码,然后会自动的将该数据放到post中传到后台
//                        String data="password="+ URLEncoder.encode(new_mima.getText().toString().trim(),"utf-8");
                        //指定长度
//                        conn.setRequestProperty("Content-length",String.valueOf(data.length()));
                        conn.setDoOutput(true); //指定输出模式
//                        conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
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
                            Log.d("删除", result);

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

            Intent intent = new Intent(zhang_haoActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };
    private DialogInterface.OnClickListener click2=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0,int arg1)
        {
            //当按钮click2被按下时则取消操作
            arg0.cancel();
        }
    };
}