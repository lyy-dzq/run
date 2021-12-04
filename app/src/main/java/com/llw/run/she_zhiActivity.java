package com.llw.run;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

public class she_zhiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_she_zhi);
        //账号管理
        LinearLayout zhao_hao=findViewById(R.id.zhang_hao);
        zhao_hao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(she_zhiActivity.this, zhang_haoActivity.class);
                startActivity(intent);
            }
        });
        //关于漫跑
        LinearLayout guan_yu=findViewById(R.id.guan_yu);
        guan_yu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(she_zhiActivity.this, guan_yuActivity.class);
                startActivity(intent);
            }
        });
        //退出程序
        Button exam=findViewById(R.id.exam);
        exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 showdialog(exam);
            }
        });

    }

    //退出提示框
    public void showdialog(View view) {
              //定义一个新的对话框对象
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
        //设置对话框提示内容
        alertdialogbuilder.setMessage("确定要退出吗？");
        //定义对话框2个按钮标题及接受事件的函数
        alertdialogbuilder.setPositiveButton("退出程序",click1);
        alertdialogbuilder.setNegativeButton("退出登录",click2);
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
                    Intent intent=new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    System.exit(0);
                }
    };
    private DialogInterface.OnClickListener click2=new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface arg0,int arg1)
                 {
                         //当按钮click2被按下时则取消操作
                     Intent intent = new Intent(she_zhiActivity.this, MainActivity.class);
                     startActivity(intent);
                 }
    };


}