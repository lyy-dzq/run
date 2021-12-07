package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ExpandableListView;

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


        for(int i=0;i<2;i++){
            Order a=new Order("lalala",R.drawable.ic_launcher,"5公里");
            orderList.add(a);
            Order b=new Order("hao",R.drawable.ic_launcher,"51公里");
            orderList.add(b);
            Order c=new Order("不一样的小伙",R.drawable.ic_launcher,"10公里");
            orderList.add(c);
        }
    }


}