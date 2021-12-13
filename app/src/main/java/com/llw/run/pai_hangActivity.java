package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pai_hangActivity extends AppCompatActivity {
    private List<Order> orderList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_pai_hang);
        initOrder();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        OrderAdapter adapter=new OrderAdapter(orderList);
        recyclerView.setAdapter(adapter);
    }

    private void initOrder(){

        new Thread(){
            @Override
            public void run() {
                try {
                    String JSON_URL="http://10.21.234.20:8080/queryFreeRunRank?flag=month";
                    URL url = new URL(JSON_URL);
                    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setRequestMethod("GET");
                    urlConn.setConnectTimeout(5000);
                    urlConn.setReadTimeout(5000);
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
                        Log.d("排行榜",result);

                    //转换
                    JSONArray result_json=new JSONArray(result);
                        for (int i = 0; i < result_json.length(); i++) {
                            JSONObject object=result_json.getJSONObject(i);
                            //将字符串转换成Bitmap类型
                            Bitmap bitmap=null;
                            try {
                                byte[]bitmapArray;
                                bitmapArray=Base64.decode(object.getString("avator"), Base64.DEFAULT);
                                bitmap= BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                                if(bitmap==null){
                                    Resources res = pai_hangActivity.this.getResources();
                                    bitmap= BitmapFactory.decodeResource(res, R.drawable.touxiang);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Order a=new Order(object.getString("username"),bitmap,"公里数: "+object.getString("totalMile"));
                            orderList.add(a);

//                            Resources res = pai_hangActivity.this.getResources();
//                            Bitmap bmp2= BitmapFactory.decodeResource(res, R.drawable.icon_home);
//                            Order b=new Order("你是谁啊",bmp2,"23");
//                            orderList.add(b);
                        }



                     } catch (Exception e) {
                         e.printStackTrace();

                }
            }
        }.start();


    }
}