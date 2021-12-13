package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    private List<Records> recordsList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_record);

        initRecords();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecordAdapter adapter=new RecordAdapter(recordsList);
        recyclerView.setAdapter(adapter);
    }
    private void initRecords(){
        final Data app = (Data)getApplication();

        new Thread(){
            @Override
            public void run() {
                try {
//                    Thread.sleep(5000);
//                            String path="http://47.113.226.119:8080/register";
                    String path="http://10.21.234.20:8080/"+app.getUid()+"/queryRunByUid";
                    URL url = new URL(path);
                    //打开httpurlconnection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");              //设置POST方式获取数据
                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                    conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                    //将数据进行编码,然后会自动的将该数据放到post中传到后台
                    String data="type="+ URLEncoder.encode("1","utf-8");
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
                        Log.d("跑步记录", result);
                        JSONArray result_json=new JSONArray(result);
                        for (int i = 0; i < result_json.length(); i++) {
                            JSONObject object=result_json.getJSONObject(i);
                            //将字符串转换成Bitmap类型
                            String type;
                           String ty=object.getString("type");
                           if(ty.equals("2")){
                               type="自由跑";
                           }else {
                               type="漫跑";
                           }
                            Records a=new Records(object.getString("runDate"),type,"公里数: "+object.getString("totalMile"),"时长： "+object.getString("runTime"));
                            recordsList.add(a);
//                            Resources res = pai_hangActivity.this.getResources();
//                            Bitmap bmp2= BitmapFactory.decodeResource(res, R.drawable.icon_home);
//                            Order b=new Order("你是谁啊",bmp2,"23");
//                            orderList.add(b);
                        }

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
}