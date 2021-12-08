package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

            Records a=new Records("2021年12月08日 12:23:09","漫跑","公里数      23","时长         02:12:09");
            recordsList.add(a);
            Records b=new Records("2021年11月18日 26:26:10","自由跑","公里数      5","时长         00:30:09");
            recordsList.add(b);


    }
}