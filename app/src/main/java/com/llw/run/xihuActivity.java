package com.llw.run;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class xihuActivity extends AppCompatActivity {
    MapView mMapView = null;
    AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_xihu);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.getUiSettings().setZoomControlsEnabled(false);
            CameraPosition cameraPosition = new CameraPosition(new LatLng(30.235068, 120.13999), 14, 0, 30);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
           aMap.moveCamera(cameraUpdate);
            List<LatLng> latLngs = new ArrayList<LatLng>();
            latLngs.add(new LatLng(30.22964,120.148924));
            latLngs.add(new LatLng(30.229686,120.149316));
            latLngs.add(new LatLng(30.229765,120.149718));
            latLngs.add(new LatLng(30.229816,120.150115));
            latLngs.add(new LatLng(30.229895,120.150576));
            latLngs.add(new LatLng(30.229895,120.150764));
            latLngs.add(new LatLng(30.229844,120.151156));
            latLngs.add(new LatLng(30.229797,120.151343));
            latLngs.add(new LatLng(30.229709,120.15159));
            latLngs.add(new LatLng(30.229635,120.151928));
            latLngs.add(new LatLng(30.229616,120.152105));
            latLngs.add(new LatLng(30.229621,120.152304));
            latLngs.add(new LatLng(30.229718,120.153114));
            latLngs.add(new LatLng(30.229742,120.153258));
            latLngs.add(new LatLng(30.22989,120.154358));
            latLngs.add(new LatLng(30.230094,120.155506));
            latLngs.add(new LatLng(30.230302,120.155501));
            latLngs.add(new LatLng(30.230404,120.15549));
            latLngs.add(new LatLng(30.230585,120.155469));
            latLngs.add(new LatLng(30.232124,120.155254));
            latLngs.add(new LatLng(30.232198,120.155275));
            latLngs.add(new LatLng(30.232388,120.155286));
            latLngs.add(new LatLng(30.232383,120.155292));
            latLngs.add(new LatLng(30.232689,120.155361));
            latLngs.add(new LatLng(30.232986,120.155533));
            latLngs.add(new LatLng(30.233375,120.15593));
            latLngs.add(new LatLng(30.233501,120.155951));

            latLngs.add(new LatLng(30.233946,120.156241));
            latLngs.add(new LatLng(30.234942,120.156472));
            latLngs.add(new LatLng(30.234984,120.156541));
            latLngs.add(new LatLng(30.235243,120.156643));
            latLngs.add(new LatLng(30.235424,120.156788));
            latLngs.add(new LatLng(30.235892,120.157384));
            latLngs.add(new LatLng(30.236087,120.157593));
            latLngs.add(new LatLng(30.237204,120.158317));

            latLngs.add(new LatLng(30.237227,120.158333));
            latLngs.add(new LatLng(30.237408,120.158371));
            latLngs.add(new LatLng(30.239345,120.158215));
            latLngs.add(new LatLng(30.24106,120.157985));
//玲珑小镇
            latLngs.add(new LatLng(30.241347,120.158129));
            latLngs.add(new LatLng(30.241991,120.158714));
            //西湖春天餐厅
            latLngs.add(new LatLng(30.24314,120.159449));
            //国防教育主题公园
            latLngs.add(new LatLng(30.243683,120.159717));
            latLngs.add(new LatLng(30.244711,120.160179));
            latLngs.add(new LatLng(30.245773,120.160822));
            latLngs.add(new LatLng(30.246032,120.160876));
            //西湖天地东门
            latLngs.add(new LatLng(30.247117,120.160736));
            latLngs.add(new LatLng(30.247839,120.160672));
            latLngs.add(new LatLng(30.248375,120.160739));
            latLngs.add(new LatLng(30.248847,120.160911));
            latLngs.add(new LatLng(30.249654,120.161316));
            //湖滨第一公园
            latLngs.add(new LatLng(30.250191,120.161463));
            latLngs.add(new LatLng(30.250211,120.1616));
            latLngs.add(new LatLng(30.250499,120.161713));
            latLngs.add(new LatLng(30.250573,120.161806));
            latLngs.add(new LatLng(30.250673,120.162105));
            //西湖风景区-荷花区
            latLngs.add(new LatLng(30.250967,120.1625));
            latLngs.add(new LatLng(30.251403,120.162654));
            latLngs.add(new LatLng(30.251642,120.162632));
            latLngs.add(new LatLng(30.251806,120.162557));
            //湖滨二公园
            latLngs.add(new LatLng(30.252539,120.162071));
            //西湖景区音乐喷泉
            latLngs.add(new LatLng(30.253839,120.161186));
            //松鼠喂食点
            latLngs.add(new LatLng(30.255567,120.159987));
            latLngs.add(new LatLng(30.256121,120.159636));
            latLngs.add(new LatLng(30.256462,120.159231));
            latLngs.add(new LatLng(30.256577,120.158946));
            latLngs.add(new LatLng(30.256573,120.158627));
            latLngs.add(new LatLng(30.25652,120.158405));
            latLngs.add(new LatLng(30.256754,120.158222));
            //杭州风味小吃
            latLngs.add(new LatLng(30.257092,120.157849));
            latLngs.add(new LatLng(30.257928,120.157291));
            latLngs.add(new LatLng(30.258093,120.157426));
            latLngs.add(new LatLng(30.258352,120.15734));
            latLngs.add(new LatLng(30.258387,120.15716));
            latLngs.add(new LatLng(30.259522,120.156669));
            latLngs.add(new LatLng(30.25967,120.156589));

            latLngs.add(new LatLng(30.260023,120.156618));
            latLngs.add(new LatLng(30.26025,120.156594));
            latLngs.add(new LatLng(30.260402,120.156304));
            latLngs.add(new LatLng(30.259786,120.154733));
            latLngs.add(new LatLng(30.259737,120.154375));
            //杭州抗击非典纪念雕塑
            latLngs.add(new LatLng(30.259841,120.154342));
            latLngs.add(new LatLng(30.259128,120.15257));
            latLngs.add(new LatLng(30.259186,120.152237));
            latLngs.add(new LatLng(30.258182,120.151333));
            //风景名胜断桥残雪
            latLngs.add(new LatLng(30.255736,120.149187));
            //孤山路
            latLngs.add(new LatLng(30.255147,120.148704));
            latLngs.add(new LatLng(30.253159,120.147047));
            latLngs.add(new LatLng(30.25056,120.144106));

            latLngs.add(new LatLng(30.250409,120.143742));
            latLngs.add(new LatLng(30.250046,120.14164));
            latLngs.add(new LatLng(30.250115,120.14131));
            latLngs.add(new LatLng(30.250087,120.139837));
            latLngs.add(new LatLng(30.250189,120.139365));
            latLngs.add(new LatLng(30.250375,120.138952));
            latLngs.add(new LatLng(30.250491,120.13886));
            latLngs.add(new LatLng(30.250653,120.138823));
            latLngs.add(new LatLng(30.251668,120.138818));
            latLngs.add(new LatLng(30.251909,120.138522));
            latLngs.add(new LatLng(30.251867,120.137804));
            //苏堤
            latLngs.add(new LatLng(30.25114,120.135813));
            latLngs.add(new LatLng(30.249594,120.136254));

            latLngs.add(new LatLng(30.246512,120.137295));

            latLngs.add(new LatLng(30.243486,120.138288));

            //望山桥
            latLngs.add(new LatLng(30.239343,120.139478));
            latLngs.add(new LatLng(30.235283,120.140728));
            //花港观鱼
            latLngs.add(new LatLng(30.232094,120.142558));
            latLngs.add(new LatLng(30.228674,120.144441));
            latLngs.add(new LatLng(30.229184,120.145648));
            latLngs.add(new LatLng(30.229179,120.145626));
            latLngs.add(new LatLng(30.22929,120.146978));
            latLngs.add(new LatLng(30.22964,120.148924));

//            latLngs.add(new LatLng(30.239343,120.139478));
//            latLngs.add(new LatLng(30.239343,120.139478));

            aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(10).color(Color.argb(255,1,1,1)));

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);

    }


}