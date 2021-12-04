package com.llw.run;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

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

            //调用后端删除账号

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