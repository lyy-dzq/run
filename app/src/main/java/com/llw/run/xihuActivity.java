package com.llw.run;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.route.DistanceResult;
import com.amap.api.services.route.DistanceSearch;
import com.llw.run.ui.home.HomeFragment;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class xihuActivity extends AppCompatActivity implements AMapLocationListener{
    MapView mMapView = null;
    AMap aMap;
    //?????????

    //??????AMapLocationClient?????????
    public AMapLocationClient mLocationClient = null;
    //??????AMapLocationClientOption??????
    public AMapLocationClientOption mLocationOption = null;
//????????????
 //
    final Data app = (Data)getApplication();

 //??????????????????
    private LocationSource.OnLocationChangedListener mListener;
    private LatLng currentLatLng;
    private float  totalDistance=0;
    private float movedDisdance;
    int i=0;
    float ss1,ss2,ss3,ss4,ss5,ss6,ss7,ss8,ss9;
    List<LatLng> latLngs2 = new ArrayList<LatLng>();
    List<LatLng> latLngs3 = new ArrayList<LatLng>();//????????????
    List<LatLng> latLngs4 = new ArrayList<LatLng>();//???????????????
    List<LatLng> latLngs5 = new ArrayList<LatLng>();//?????????????????????
    List<LatLng> latLngs6 = new ArrayList<LatLng>();//????????????
    List<LatLng> latLngs7 = new ArrayList<LatLng>();//????????????
    List<LatLng> latLngs8 = new ArrayList<LatLng>();//?????????
    List<LatLng> latLngs9 = new ArrayList<LatLng>();//??????
    List<LatLng> latLngs10 = new ArrayList<LatLng>();//???????????????
    private String sppeed;
    Chronometer timer;
    //




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_xihu);
//      totalDistance=app2.getJixu();
      Log.d("?????????????????????????????????", totalDistance+"");

//??????
       LinearLayout contentLayout=findViewById(R.id.tanchu);
        //????????????????????????
        mMapView = (MapView) findViewById(R.id.map);
        timer=(Chronometer)findViewById(R.id.timer);

        //???activity??????onCreate?????????mMapView.onCreate(savedInstanceState)???????????????
        mMapView.onCreate(savedInstanceState);
     final Data app2 = (Data)getApplication();
     totalDistance=app2.getJixu();


     //?????????
        initLocation();
        mLocationClient.startLocation();
//????????????
        LinearLayout tanchu=findViewById(R.id.tanchu);
        TextView xxi=findViewById(R.id.xxi);
        TextView distance2=findViewById(R.id.distance2);
        TextView speed2=findViewById(R.id.speed2);
        TextView timer2=findViewById(R.id.timer2);
        Button stoprun=findViewById(R.id.stoprun);
        stoprun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.stop();
             //??????
             new Thread(){
              @Override
              public void run() {
               try {
                String JSON_URL="http://101.35.202.198:8080/";
                JSON_URL=JSON_URL+app2.getUid()+"/insertUserRomanticRun?"+"totalMile="+totalDistance/1000+"&runTime="+timer.getText();
                Log.d("??????", JSON_URL);
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
                Log.d("??????",result);
               } catch (Exception e) {
                Log.d("???", "....");
                e.printStackTrace();

               }
              }
             }.start();
             app2.setJixulishu(totalDistance);
             tanchu.setVisibility(View.VISIBLE);
                mLocationClient.stopLocation();
                mLocationClient.onDestroy();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy???MM???dd??? HH:mm:ss");// HH:mm:ss
                //??????????????????
                Date date = new Date(System.currentTimeMillis());
                xxi.setText(simpleDateFormat.format(date));
                //???????????????????????????
                distance2.setText(totalDistance/1000+"km");
                speed2.setText(sppeed);
                timer2.setText(timer.getText());
                stoprun.setVisibility(View.INVISIBLE);
                //???????????????????????????

            }
        });
        //????????????
        TextView tchu=findViewById(R.id.tchu);
        tchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(xihuActivity.this, BottomNavigationActivity.class);
                startActivity(intent);
            }
        });
        TextView fxiang=findViewById(R.id.fxiang);
        fxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentLayout.setDrawingCacheEnabled(true);
               contentLayout.buildDrawingCache();
                Bitmap bitmap = Bitmap.createBitmap(contentLayout.getDrawingCache());
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
                
                         // ??????????????????
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM,uri);
                intent.setType("image/*");
                startActivity(Intent.createChooser(intent,"??????????????????"));

            }
        });


        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.getUiSettings().setZoomControlsEnabled(false);
            CameraPosition cameraPosition = new CameraPosition(new LatLng(30.235068, 120.14800), 15, 0, 10);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
           aMap.moveCamera(cameraUpdate);
            List<LatLng> latLngs = new ArrayList<LatLng>();

            latLngs.add(new LatLng(30.22964,120.148924));
            latLngs.add(new LatLng(30.229686,120.149316));//v1
                latLngs2.add(new LatLng(30.22964,120.148924));
                latLngs2.add(new LatLng(30.229686,120.149316));//v1
            float dis1=AMapUtils.calculateLineDistance(new LatLng(30.22964,120.148924),new LatLng(30.229686,120.149316));
            latLngs.add(new LatLng(30.229765,120.149718));//2
                latLngs2.add(new LatLng(30.229765,120.149718));//2
            float dis2=AMapUtils.calculateLineDistance(new LatLng(30.229765,120.149718),new LatLng(30.229686,120.149316));
            latLngs.add(new LatLng(30.229816,120.150115));//3
                latLngs2.add(new LatLng(30.229816,120.150115));//3
            float dis3=AMapUtils.calculateLineDistance(new LatLng(30.229765,120.149718),new LatLng(30.229816,120.150115));
            latLngs.add(new LatLng(30.229895,120.150576));//4
                latLngs2.add(new LatLng(30.229895,120.150576));
            float dis4=AMapUtils.calculateLineDistance(new LatLng(30.229895,120.150576),new LatLng(30.229816,120.150115));

            latLngs.add(new LatLng(30.229895,120.150764));//5
                latLngs2.add(new LatLng(30.229895,120.150764));//5
            float dis5=AMapUtils.calculateLineDistance(new LatLng(30.229895,120.150576),new LatLng(30.229895,120.150764));
            latLngs.add(new LatLng(30.229844,120.151156));//6
                latLngs2.add(new LatLng(30.229844,120.151156));//6
            float dis6=AMapUtils.calculateLineDistance(new LatLng(30.229844,120.151156),new LatLng(30.229895,120.150764));
            latLngs.add(new LatLng(30.229797,120.151343));//7
                latLngs2.add(new LatLng(30.229797,120.151343));//7
            float dis7=AMapUtils.calculateLineDistance(new LatLng(30.229844,120.151156),new LatLng(30.229797,120.151343));
            latLngs.add(new LatLng(30.229709,120.15159));//8
                latLngs2.add(new LatLng(30.229709,120.15159));//8
            float dis8=AMapUtils.calculateLineDistance(new LatLng(30.229709,120.15159),new LatLng(30.229797,120.151343));
            latLngs.add(new LatLng(30.229635,120.151928));//9
                latLngs2.add(new LatLng(30.229635,120.151928));//9
            float dis9=AMapUtils.calculateLineDistance(new LatLng(30.229635,120.151928),new LatLng(30.229709,120.15159));
            latLngs.add(new LatLng(30.229616,120.152105));//10
            latLngs2.add(new LatLng(30.229616,120.152105));//10
            float dis10=AMapUtils.calculateLineDistance(new LatLng(30.229616,120.152105),new LatLng(30.229635,120.151928));
            latLngs.add(new LatLng(30.229621,120.152304));
            latLngs2.add(new LatLng(30.229621,120.152304));
            float dis11=AMapUtils.calculateLineDistance(new LatLng(30.229621,120.152304),new LatLng(30.229616,120.152105));
            latLngs.add(new LatLng(30.229718,120.153114));
            latLngs2.add(new LatLng(30.229718,120.153114));
            float dis12=AMapUtils.calculateLineDistance(new LatLng(30.229718,120.153114),new LatLng(30.229621,120.152304));
            latLngs.add(new LatLng(30.229742,120.153258));
            latLngs2.add(new LatLng(30.229742,120.153258));
            float dis13=AMapUtils.calculateLineDistance(new LatLng(30.229742,120.153258),new LatLng(30.229718,120.153114));
            latLngs.add(new LatLng(30.22989,120.154358));
            latLngs2.add(new LatLng(30.22989,120.154358));
            float dis14=AMapUtils.calculateLineDistance(new LatLng(30.22989,120.154358),new LatLng(30.229742,120.153258));
            latLngs.add(new LatLng(30.230094,120.155506));
            latLngs2.add(new LatLng(30.230094,120.155506));
            float dis15=AMapUtils.calculateLineDistance(new LatLng(30.230094,120.155506),new LatLng(30.22989,120.154358));
            latLngs.add(new LatLng(30.230302,120.155501));
            latLngs2.add(new LatLng(30.230302,120.155501));
            float dis16=AMapUtils.calculateLineDistance(new LatLng(30.230302,120.155501),new LatLng(30.230094,120.155506));
            latLngs.add(new LatLng(30.230404,120.15549));
            latLngs2.add(new LatLng(30.230404,120.15549));
            float dis17=AMapUtils.calculateLineDistance(new LatLng(30.230404,120.15549),new LatLng(30.230302,120.155501));
            latLngs.add(new LatLng(30.230585,120.155469));
            latLngs2.add(new LatLng(30.230585,120.155469));
            float dis18=AMapUtils.calculateLineDistance(new LatLng(30.230585,120.155469),new LatLng(30.230404,120.15549));
            latLngs.add(new LatLng(30.232124,120.155254));
            latLngs2.add(new LatLng(30.232124,120.155254));
            float dis19=AMapUtils.calculateLineDistance(new LatLng(30.230585,120.155469),new LatLng(30.232124,120.155254));
            latLngs.add(new LatLng(30.232198,120.155275));
            latLngs2.add(new LatLng(30.232198,120.155275));
            float dis20=AMapUtils.calculateLineDistance(new LatLng(30.232198,120.155275),new LatLng(30.232124,120.155254));
            latLngs.add(new LatLng(30.232388,120.155286));
            latLngs2.add(new LatLng(30.232388,120.155286));
            float dis21=AMapUtils.calculateLineDistance(new LatLng(30.232198,120.155275),new LatLng(30.232388,120.155286));

            latLngs.add(new LatLng(30.232383,120.155292));
            latLngs2.add(new LatLng(30.232383,120.155292));
            float dis22=AMapUtils.calculateLineDistance(new LatLng(30.232383,120.155292),new LatLng(30.232388,120.155286));

            latLngs.add(new LatLng(30.232689,120.155361));
            latLngs2.add(new LatLng(30.232689,120.155361));
            float dis23=AMapUtils.calculateLineDistance(new LatLng(30.232383,120.155292),new LatLng(30.232689,120.155361));

            latLngs.add(new LatLng(30.232986,120.155533));
            latLngs2.add(new LatLng(30.232986,120.155533));
            float dis24=AMapUtils.calculateLineDistance(new LatLng(30.232986,120.155533),new LatLng(30.232689,120.155361));

            latLngs.add(new LatLng(30.233375,120.15593));
            latLngs2.add(new LatLng(30.233375,120.15593));
            float dis25=AMapUtils.calculateLineDistance(new LatLng(30.232986,120.155533),new LatLng(30.233375,120.15593));

            latLngs.add(new LatLng(30.233501,120.155951));
            latLngs2.add(new LatLng(30.233501,120.155951));
            float dis26=AMapUtils.calculateLineDistance(new LatLng(30.233501,120.155951),new LatLng(30.233375,120.15593));
//????????????????????????
            latLngs.add(new LatLng(30.233946,120.156241));
            latLngs2.add(new LatLng(30.233946,120.156241));
            float dis27=AMapUtils.calculateLineDistance(new LatLng(30.233501,120.155951),new LatLng(30.233946,120.156241));
            ss1=dis1+dis2+dis3+dis4+dis5+dis6+dis7+dis8+dis9+dis10+dis11+dis12+dis13+dis14+dis15+dis16+dis17+dis18+dis19+dis20+dis21+dis22+dis23+dis24+dis25+dis26+dis27;

            latLngs.add(new LatLng(30.234942,120.156472));
            latLngs3.add(new LatLng(30.233946,120.156241));
            latLngs3.add(new LatLng(30.234942,120.156472));
            float dis28=AMapUtils.calculateLineDistance(new LatLng(30.234942,120.156472),new LatLng(30.233946,120.156241));

            latLngs.add(new LatLng(30.234984,120.156541));
            latLngs3.add(new LatLng(30.234984,120.156541));
            float dis29=AMapUtils.calculateLineDistance(new LatLng(30.234942,120.156472),new LatLng(30.234984,120.156541));

            latLngs.add(new LatLng(30.235243,120.156643));
            latLngs3.add(new LatLng(30.235243,120.156643));
            float dis30=AMapUtils.calculateLineDistance(new LatLng(30.235243,120.156643),new LatLng(30.234984,120.156541));

            latLngs.add(new LatLng(30.235424,120.156788));
            latLngs3.add(new LatLng(30.235424,120.156788));
            float dis31=AMapUtils.calculateLineDistance(new LatLng(30.235243,120.156643),new LatLng(30.235424,120.156788));

            latLngs.add(new LatLng(30.235892,120.157384));
            latLngs3.add(new LatLng(30.235892,120.157384));
            float dis32=AMapUtils.calculateLineDistance(new LatLng(30.235892,120.157384),new LatLng(30.235424,120.156788));

            latLngs.add(new LatLng(30.236087,120.157593));
            latLngs3.add(new LatLng(30.236087,120.157593));
            float dis33=AMapUtils.calculateLineDistance(new LatLng(30.235892,120.157384),new LatLng(30.236087,120.157593));

            latLngs.add(new LatLng(30.237204,120.158317));
            latLngs3.add(new LatLng(30.237204,120.158317));
            float dis34=AMapUtils.calculateLineDistance(new LatLng(30.237204,120.158317),new LatLng(30.236087,120.157593));


            latLngs.add(new LatLng(30.237227,120.158333));
            latLngs3.add(new LatLng(30.237227,120.158333));
            float dis35=AMapUtils.calculateLineDistance(new LatLng(30.237204,120.158317),new LatLng(30.237227,120.158333));

            latLngs.add(new LatLng(30.237408,120.158371));
            latLngs3.add(new LatLng(30.237408,120.158371));
            float dis36=AMapUtils.calculateLineDistance(new LatLng(30.237408,120.158371),new LatLng(30.237227,120.158333));

            latLngs.add(new LatLng(30.239345,120.158215));
            latLngs3.add(new LatLng(30.239345,120.158215));
            float dis37=AMapUtils.calculateLineDistance(new LatLng(30.237408,120.158371),new LatLng(30.239345,120.158215));
//??????????????????
            latLngs.add(new LatLng(30.24106,120.157985));
            latLngs3.add(new LatLng(30.24106,120.157985));
            float dis38=AMapUtils.calculateLineDistance(new LatLng(30.24106,120.157985),new LatLng(30.239345,120.158215));
            ss2=dis28+dis29+dis30+dis31+dis32+dis33+dis34+dis35+dis36+dis37+dis38;
            latLngs.add(new LatLng(30.241347,120.158129));
            latLngs4.add(new LatLng(30.241347,120.158129));
            latLngs4.add(new LatLng(30.24106,120.157985));
            float dis39=AMapUtils.calculateLineDistance(new LatLng(30.24106,120.157985),new LatLng(30.241347,120.158129));

            latLngs.add(new LatLng(30.241991,120.158714));
            latLngs4.add(new LatLng(30.241991,120.158714));
            float dis40=AMapUtils.calculateLineDistance(new LatLng(30.241991,120.158714),new LatLng(30.241347,120.158129));

            //??????????????????
            latLngs.add(new LatLng(30.24314,120.159449));
            latLngs4.add(new LatLng(30.24314,120.159449));
            float dis41=AMapUtils.calculateLineDistance(new LatLng(30.241991,120.158714),new LatLng(30.24314,120.159449));

            //????????????????????????
            latLngs.add(new LatLng(30.243683,120.159717));
            latLngs4.add(new LatLng(30.243683,120.159717));
            float dis42=AMapUtils.calculateLineDistance(new LatLng(30.243683,120.159717),new LatLng(30.24314,120.159449));

            latLngs.add(new LatLng(30.244711,120.160179));
            latLngs4.add(new LatLng(30.244711,120.160179));
            float dis43=AMapUtils.calculateLineDistance(new LatLng(30.243683,120.159717),new LatLng(30.244711,120.160179));

            latLngs.add(new LatLng(30.245773,120.160822));
            latLngs4.add(new LatLng(30.245773,120.160822));
            float dis44=AMapUtils.calculateLineDistance(new LatLng(30.245773,120.160822),new LatLng(30.244711,120.160179));

            latLngs.add(new LatLng(30.246032,120.160876));
            latLngs4.add(new LatLng(30.246032,120.160876));
            float dis45=AMapUtils.calculateLineDistance(new LatLng(30.245773,120.160822),new LatLng(30.246032,120.160876));

            //??????????????????
            latLngs.add(new LatLng(30.247117,120.160736));
            latLngs4.add(new LatLng(30.247117,120.160736));
            float dis46=AMapUtils.calculateLineDistance(new LatLng(30.247117,120.160736),new LatLng(30.246032,120.160876));

            latLngs.add(new LatLng(30.247839,120.160672));
            latLngs4.add(new LatLng(30.247839,120.160672));
            float dis47=AMapUtils.calculateLineDistance(new LatLng(30.247117,120.160736),new LatLng(30.247839,120.160672));

            latLngs.add(new LatLng(30.248375,120.160739));
            latLngs4.add(new LatLng(30.248375,120.160739));
            float dis48=AMapUtils.calculateLineDistance(new LatLng(30.248375,120.160739),new LatLng(30.247839,120.160672));

            latLngs.add(new LatLng(30.248847,120.160911));
            latLngs4.add(new LatLng(30.248847,120.160911));
            float dis49=AMapUtils.calculateLineDistance(new LatLng(30.248375,120.160739),new LatLng(30.248847,120.160911));

            latLngs.add(new LatLng(30.249654,120.161316));
            latLngs4.add(new LatLng(30.249654,120.161316));
            float dis50=AMapUtils.calculateLineDistance(new LatLng(30.249654,120.161316),new LatLng(30.248847,120.160911));

// ??????????????????
            latLngs.add(new LatLng(30.250191,120.161463));
            latLngs4.add(new LatLng(30.250191,120.161463));
            float dis51=AMapUtils.calculateLineDistance(new LatLng(30.249654,120.161316),new LatLng(30.250191,120.161463));
            ss3=dis39+dis40+dis41+dis42+dis43+dis44+dis45+dis46+dis47+dis48+dis49+dis50+dis51;
            latLngs.add(new LatLng(30.250211,120.1616));
            latLngs5.add(new LatLng(30.250191,120.161463));
            latLngs5.add(new LatLng(30.250211,120.1616));
            float dis52=AMapUtils.calculateLineDistance(new LatLng(30.250211,120.1616),new LatLng(30.250191,120.161463));

            latLngs.add(new LatLng(30.250499,120.161713));
            latLngs5.add(new LatLng(30.250499,120.161713));
            float dis53=AMapUtils.calculateLineDistance(new LatLng(30.250211,120.1616),new LatLng(30.250499,120.161713));

            latLngs.add(new LatLng(30.250573,120.161806));
            latLngs5.add(new LatLng(30.250573,120.161806));
            float dis54=AMapUtils.calculateLineDistance(new LatLng(30.250573,120.161806),new LatLng(30.250499,120.161713));

            latLngs.add(new LatLng(30.250673,120.162105));
            latLngs5.add(new LatLng(30.250673,120.162105));
            float dis55=AMapUtils.calculateLineDistance(new LatLng(30.250573,120.161806),new LatLng(30.250673,120.162105));

            //???????????????-?????????
            latLngs.add(new LatLng(30.250967,120.1625));
            latLngs5.add(new LatLng(30.250967,120.1625));
            float dis56=AMapUtils.calculateLineDistance(new LatLng(30.250967,120.1625),new LatLng(30.250673,120.162105));

            latLngs.add(new LatLng(30.251403,120.162654));
            latLngs5.add(new LatLng(30.251403,120.162654));
            float dis57=AMapUtils.calculateLineDistance(new LatLng(30.250967,120.1625),new LatLng(30.251403,120.162654));

            latLngs.add(new LatLng(30.251642,120.162632));
            latLngs5.add(new LatLng(30.251642,120.162632));
            float dis58=AMapUtils.calculateLineDistance(new LatLng(30.251642,120.162632),new LatLng(30.251403,120.162654));

            latLngs.add(new LatLng(30.251806,120.162557));
            latLngs5.add(new LatLng(30.251806,120.162557));
            float dis59=AMapUtils.calculateLineDistance(new LatLng(30.251642,120.162632),new LatLng(30.251806,120.162557));

            //???????????????
            latLngs.add(new LatLng(30.252539,120.162071));
            latLngs5.add(new LatLng(30.252539,120.162071));
            float dis60=AMapUtils.calculateLineDistance(new LatLng(30.252539,120.162071),new LatLng(30.251806,120.162557));

            //????????????????????????
            latLngs.add(new LatLng(30.253839,120.161186));
            latLngs5.add(new LatLng(30.253839,120.161186));
            float dis61=AMapUtils.calculateLineDistance(new LatLng(30.252539,120.162071),new LatLng(30.253839,120.161186));

            //???????????????
            latLngs.add(new LatLng(30.255567,120.159987));
            latLngs5.add(new LatLng(30.255567,120.159987));
            float dis62=AMapUtils.calculateLineDistance(new LatLng(30.255567,120.159987),new LatLng(30.253839,120.161186));

            latLngs.add(new LatLng(30.256121,120.159636));
            latLngs5.add(new LatLng(30.256121,120.159636));
            float dis63=AMapUtils.calculateLineDistance(new LatLng(30.255567,120.159987),new LatLng(30.256121,120.159636));

            latLngs.add(new LatLng(30.256462,120.159231));
            latLngs5.add(new LatLng(30.256462,120.159231));
            float dis64=AMapUtils.calculateLineDistance(new LatLng(30.256462,120.159231),new LatLng(30.256121,120.159636));

            latLngs.add(new LatLng(30.256577,120.158946));
            latLngs5.add(new LatLng(30.256577,120.158946));
            float dis65=AMapUtils.calculateLineDistance(new LatLng(30.256462,120.159231),new LatLng(30.256577,120.158946));

            latLngs.add(new LatLng(30.256573,120.158627));
            latLngs5.add(new LatLng(30.256573,120.158627));
            float dis66=AMapUtils.calculateLineDistance(new LatLng(30.256573,120.158627),new LatLng(30.256577,120.158946));

            latLngs.add(new LatLng(30.25652,120.158405));
            latLngs5.add(new LatLng(30.25652,120.158405));
            float dis67=AMapUtils.calculateLineDistance(new LatLng(30.256573,120.158627),new LatLng(30.25652,120.158405));

            latLngs.add(new LatLng(30.256754,120.158222));
            latLngs5.add(new LatLng(30.256754,120.158222));
            float dis68=AMapUtils.calculateLineDistance(new LatLng(30.256754,120.158222),new LatLng(30.25652,120.158405));

            //??????????????????
            latLngs.add(new LatLng(30.257092,120.157849));
            latLngs5.add(new LatLng(30.257092,120.157849));
            float dis69=AMapUtils.calculateLineDistance(new LatLng(30.256754,120.158222),new LatLng(30.257092,120.157849));

            latLngs.add(new LatLng(30.257928,120.157291));
            latLngs5.add(new LatLng(30.257928,120.157291));
            float dis70=AMapUtils.calculateLineDistance(new LatLng(30.257928,120.157291),new LatLng(30.25652,120.158405));

            latLngs.add(new LatLng(30.258093,120.157426));
            latLngs5.add(new LatLng(30.258093,120.157426));
            float dis71=AMapUtils.calculateLineDistance(new LatLng(30.257928,120.157291),new LatLng(30.258093,120.157426));

            latLngs.add(new LatLng(30.258352,120.15734));
            latLngs5.add(new LatLng(30.258352,120.15734));
            float dis72=AMapUtils.calculateLineDistance(new LatLng(30.258352,120.15734),new LatLng(30.258093,120.157426));
//???????????????
            latLngs.add(new LatLng(30.258387,120.15716));
            latLngs5.add(new LatLng(30.258387,120.15716));
            float dis73=AMapUtils.calculateLineDistance(new LatLng(30.258352,120.15734),new LatLng(30.258387,120.15716));
            ss4=dis52+dis53+dis54+dis55+dis56+dis57+dis58+dis59+dis60+dis61+dis62+dis63+dis64+dis65+dis66+dis67+dis68+dis69+dis70+dis71+dis72+dis73;
            latLngs.add(new LatLng(30.259522,120.156669));
            latLngs6.add(new LatLng(30.258387,120.15716));
            latLngs6.add(new LatLng(30.259522,120.156669));
            float dis74=AMapUtils.calculateLineDistance(new LatLng(30.259522,120.156669),new LatLng(30.258387,120.15716));

            latLngs.add(new LatLng(30.25967,120.156589));
            latLngs6.add(new LatLng(30.25967,120.156589));
            float dis75=AMapUtils.calculateLineDistance(new LatLng(30.259522,120.156669),new LatLng(30.25967,120.156589));


            latLngs.add(new LatLng(30.260023,120.156618));
            latLngs6.add(new LatLng(30.260023,120.156618));
            float dis76=AMapUtils.calculateLineDistance(new LatLng(30.260023,120.156618),new LatLng(30.25967,120.156589));

            latLngs.add(new LatLng(30.26025,120.156594));
            latLngs6.add(new LatLng(30.26025,120.156594));
            float dis77=AMapUtils.calculateLineDistance(new LatLng(30.260023,120.156618),new LatLng(30.26025,120.156594));

            latLngs.add(new LatLng(30.260402,120.156304));
            latLngs6.add(new LatLng(30.260402,120.156304));
            float dis78=AMapUtils.calculateLineDistance(new LatLng(30.260402,120.156304),new LatLng(30.26025,120.156594));

            latLngs.add(new LatLng(30.259786,120.154733));
            latLngs6.add(new LatLng(30.259786,120.154733));
            float dis79=AMapUtils.calculateLineDistance(new LatLng(30.260402,120.156304),new LatLng(30.259786,120.154733));

            latLngs.add(new LatLng(30.259737,120.154375));
            latLngs6.add(new LatLng(30.259737,120.154375));
            float dis80=AMapUtils.calculateLineDistance(new LatLng(30.259737,120.154375),new LatLng(30.259786,120.154733));

            //??????????????????????????????
            latLngs.add(new LatLng(30.259841,120.154342));
            latLngs6.add(new LatLng(30.259841,120.154342));
            float dis81=AMapUtils.calculateLineDistance(new LatLng(30.259737,120.154375),new LatLng(30.259841,120.154342));

            latLngs.add(new LatLng(30.259128,120.15257));
            latLngs6.add(new LatLng(30.259128,120.15257));
            float dis82=AMapUtils.calculateLineDistance(new LatLng(30.259128,120.15257),new LatLng(30.259841,120.154342));

            latLngs.add(new LatLng(30.259186,120.152237));
            latLngs6.add(new LatLng(30.259186,120.152237));
            float dis83=AMapUtils.calculateLineDistance(new LatLng(30.259128,120.15257),new LatLng(30.259186,120.152237));

            latLngs.add(new LatLng(30.258182,120.151333));
            latLngs6.add(new LatLng(30.258182,120.151333));
            float dis84=AMapUtils.calculateLineDistance(new LatLng(30.258182,120.151333),new LatLng(30.259186,120.152237));
 // ????????????
            latLngs.add(new LatLng(30.255736,120.149187));
            latLngs6.add(new LatLng(30.255736,120.149187));
            float dis85=AMapUtils.calculateLineDistance(new LatLng(30.258182,120.151333),new LatLng(30.255736,120.149187));
            ss5=dis74+dis75+dis76+dis77+dis78+dis79+dis80+dis81+dis82+dis83+dis84+dis85;
            //?????????
            latLngs.add(new LatLng(30.255147,120.148704));
            latLngs7.add(new LatLng(30.255736,120.149187));
            latLngs7.add(new LatLng(30.255147,120.148704));
            float dis86=AMapUtils.calculateLineDistance(new LatLng(30.255147,120.148704),new LatLng(30.255736,120.149187));

            latLngs.add(new LatLng(30.253159,120.147047));
            latLngs7.add(new LatLng(30.253159,120.147047));
            float dis87=AMapUtils.calculateLineDistance(new LatLng(30.255147,120.148704),new LatLng(30.253159,120.147047));

            latLngs.add(new LatLng(30.25056,120.144106));
            latLngs7.add(new LatLng(30.25056,120.144106));
            float dis88=AMapUtils.calculateLineDistance(new LatLng(30.25056,120.144106),new LatLng(30.253159,120.147047));

//????????????
            latLngs.add(new LatLng(30.250409,120.143742));
            latLngs7.add(new LatLng(30.250409,120.143742));
            float dis89=AMapUtils.calculateLineDistance(new LatLng(30.25056,120.144106),new LatLng(30.250409,120.143742));
            ss6=dis86+dis87+dis88+dis89;
            latLngs.add(new LatLng(30.250046,120.14164));
            latLngs8.add(new LatLng(30.250409,120.143742));
            latLngs8.add(new LatLng(30.250046,120.14164));
            float dis90=AMapUtils.calculateLineDistance(new LatLng(30.250046,120.14164),new LatLng(30.250409,120.143742));

            latLngs.add(new LatLng(30.250115,120.14131));
            latLngs8.add(new LatLng(30.250115,120.14131));
            float dis91=AMapUtils.calculateLineDistance(new LatLng(30.250046,120.14164),new LatLng(30.250115,120.14131));

            latLngs.add(new LatLng(30.250087,120.139837));
            latLngs8.add(new LatLng(30.250087,120.139837));
            float dis92=AMapUtils.calculateLineDistance(new LatLng(30.250087,120.139837),new LatLng(30.250115,120.14131));

            latLngs.add(new LatLng(30.250189,120.139365));
            latLngs8.add(new LatLng(30.250189,120.139365));
            float dis93=AMapUtils.calculateLineDistance(new LatLng(30.250087,120.139837),new LatLng(30.250189,120.139365));

            latLngs.add(new LatLng(30.250375,120.138952));
            latLngs8.add(new LatLng(30.250375,120.138952));
            float dis94=AMapUtils.calculateLineDistance(new LatLng(30.250375,120.138952),new LatLng(30.250189,120.139365));

            latLngs.add(new LatLng(30.250491,120.13886));
            latLngs8.add(new LatLng(30.250491,120.13886));
            float dis95=AMapUtils.calculateLineDistance(new LatLng(30.250375,120.138952),new LatLng(30.250491,120.13886));

            latLngs.add(new LatLng(30.250653,120.138823));
            latLngs8.add(new LatLng(30.250653,120.138823));
            float dis96=AMapUtils.calculateLineDistance(new LatLng(30.250653,120.138823),new LatLng(30.250491,120.13886));

            latLngs.add(new LatLng(30.251668,120.138818));
            latLngs8.add(new LatLng(30.251668,120.138818));
            float dis97=AMapUtils.calculateLineDistance(new LatLng(30.250653,120.138823),new LatLng(30.251668,120.138818));

            latLngs.add(new LatLng(30.251909,120.138522));
            latLngs8.add(new LatLng(30.251909,120.138522));
            float dis98=AMapUtils.calculateLineDistance(new LatLng(30.251909,120.138522),new LatLng(30.251668,120.138818));

            latLngs.add(new LatLng(30.251867,120.137804));
            latLngs8.add(new LatLng(30.251867,120.137804));
            float dis99=AMapUtils.calculateLineDistance(new LatLng(30.251909,120.138522),new LatLng(30.251867,120.137804));

            //??????
            latLngs.add(new LatLng(30.25114,120.135813));
            latLngs8.add(new LatLng(30.25114,120.135813));
            float dis100=AMapUtils.calculateLineDistance(new LatLng(30.25114,120.135813),new LatLng(30.251867,120.137804));

            latLngs.add(new LatLng(30.249594,120.136254));
            latLngs8.add(new LatLng(30.249594,120.136254));
            float dis101=AMapUtils.calculateLineDistance(new LatLng(30.25114,120.135813),new LatLng(30.249594,120.136254));


            latLngs.add(new LatLng(30.246512,120.137295));
            latLngs8.add(new LatLng(30.246512,120.137295));
            float dis102=AMapUtils.calculateLineDistance(new LatLng(30.246512,120.137295),new LatLng(30.249594,120.136254));


            latLngs.add(new LatLng(30.243486,120.138288));
            latLngs8.add(new LatLng(30.243486,120.138288));
            float dis103=AMapUtils.calculateLineDistance(new LatLng(30.246512,120.137295),new LatLng(30.243486,120.138288));
//?????????
            latLngs.add(new LatLng(30.239343,120.139478));
            latLngs8.add(new LatLng(30.239343,120.139478));
            float dis104=AMapUtils.calculateLineDistance(new LatLng(30.239343,120.139478),new LatLng(30.243486,120.138288));
            ss7=dis90+dis91+dis92+dis93+dis94+dis95+dis96+dis97+dis98+dis99+dis100+dis101+dis102+dis103+dis104;
            latLngs.add(new LatLng(30.235283,120.140728));
            latLngs9.add(new LatLng(30.239343,120.139478));
            latLngs9.add(new LatLng(30.235283,120.140728));
            float dis105=AMapUtils.calculateLineDistance(new LatLng(30.239343,120.139478),new LatLng(30.235283,120.140728));

            //????????????
            latLngs.add(new LatLng(30.232094,120.142558));
            latLngs9.add(new LatLng(30.232094,120.142558));
            float dis106=AMapUtils.calculateLineDistance(new LatLng(30.232094,120.142558),new LatLng(30.235283,120.140728));
//??????
            latLngs.add(new LatLng(30.228674,120.144441));
            latLngs9.add(new LatLng(30.228674,120.144441));
            float dis107=AMapUtils.calculateLineDistance(new LatLng(30.232094,120.142558),new LatLng(30.228674,120.144441));
            ss8=dis105+dis106+dis107;
            latLngs.add(new LatLng(30.229184,120.145648));
            latLngs10.add(new LatLng(30.228674,120.144441));
            latLngs10.add(new LatLng(30.229184,120.145648));
            float dis108=AMapUtils.calculateLineDistance(new LatLng(30.229184,120.145648),new LatLng(30.228674,120.144441));

            latLngs.add(new LatLng(30.22929,120.146978));
            latLngs10.add(new LatLng(30.22929,120.146978));
            float dis109=AMapUtils.calculateLineDistance(new LatLng(30.22929,120.146978),new LatLng(30.229179,120.145626));
//???????????????
            latLngs.add(new LatLng(30.22964,120.148924));
            latLngs10.add(new LatLng(30.22964,120.148924));
            float dis110=AMapUtils.calculateLineDistance(new LatLng(30.22929,120.146978),new LatLng(30.22964,120.148924));

            ss9=dis108+dis110+dis109;
            //????????????
            aMap.addMarker(new MarkerOptions().position(new LatLng(30.22964,120.148924)).icon(BitmapDescriptorFactory.fromResource(R.drawable.begin_end)).title("??????/??????").snippet("?????????????????????"));//??????????????????
            aMap.addMarker(new MarkerOptions().position(new LatLng(30.233946,120.156241)).icon(BitmapDescriptorFactory.fromResource(R.drawable.gross)).title("?????????").snippet("??????????????????"));//??????
            aMap.addMarker(new MarkerOptions().position(new LatLng(30.24106,120.157985)).icon(BitmapDescriptorFactory.fromResource(R.drawable.gross)).title("?????????").snippet("????????????"));//????????????
            aMap.addMarker(new MarkerOptions().position(new LatLng(30.250191,120.161463)).icon(BitmapDescriptorFactory.fromResource(R.drawable.gross)).title("?????????").snippet("???????????????"));//???????????????
            aMap.addMarker(new MarkerOptions().position(new LatLng(30.258387,120.15716)).icon(BitmapDescriptorFactory.fromResource(R.drawable.gross)).title("?????????").snippet("???????????????"));//???????????????
            aMap.addMarker(new MarkerOptions().position(new LatLng(30.255736,120.149187)).icon(BitmapDescriptorFactory.fromResource(R.drawable.gross)).title("?????????").snippet("????????????"));//????????????
            aMap.addMarker(new MarkerOptions().position(new LatLng(30.250409,120.143742)).icon(BitmapDescriptorFactory.fromResource(R.drawable.gross)).title("?????????").snippet("????????????"));//????????????
            aMap.addMarker(new MarkerOptions().position(new LatLng(  30.239343,120.139478)).icon(BitmapDescriptorFactory.fromResource(R.drawable.gross)).title("?????????").snippet("?????????"));//?????????
            aMap.addMarker(new MarkerOptions().position(new LatLng(   30.228674,120.144441)).icon(BitmapDescriptorFactory.fromResource(R.drawable.gross)).title("?????????").snippet("??????"));//??????


            aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(10).color(Color.BLUE).setDottedLine(true));


        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //???activity??????onDestroy?????????mMapView.onDestroy()???????????????
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //???activity??????onResume?????????mMapView.onResume ()???????????????????????????
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //???activity??????onPause?????????mMapView.onPause ()????????????????????????
        mMapView.onPause();

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //???activity??????onSaveInstanceState?????????mMapView.onSaveInstanceState (outState)??????????????????????????????
        mMapView.onSaveInstanceState(outState);

    }
    //?????????
    private void initLocation() {
        //???????????????
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //????????????????????????
        mLocationClient.setLocationListener(this);
        //?????????AMapLocationClientOption??????
        mLocationOption = new AMapLocationClientOption();
        //?????????????????????AMapLocationMode.Hight_Accuracy?????????????????????
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //????????????3s???????????????????????????????????????
        //??????setOnceLocationLatest(boolean b)?????????true??????????????????SDK???????????????3s?????????????????????????????????????????????????????????true???setOnceLocation(boolean b)????????????????????????true???????????????????????????false???
        mLocationOption.setOnceLocationLatest(true);
        //????????????????????????????????????????????????????????????
        mLocationOption.setNeedAddress(true);
        //?????????????????????????????????????????????????????????30000???????????????????????????????????????8000?????????
        mLocationOption.setHttpTimeOut(20000);
        //??????????????????????????????????????????????????????
        mLocationOption.setLocationCacheEnable(false);
        //??????????????????????????????????????????
        mLocationClient.setLocationOption(mLocationOption);
    }
    /**
     * ?????????????????????????????????
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //??????
                String address = aMapLocation.getAddress();
                //????????????
                double latitude=aMapLocation.getLatitude();
                //????????????
                double longitude=aMapLocation.getLongitude();
                //????????????
                //???????????????
                if (currentLatLng == null) {
                    timer.setBase(SystemClock.elapsedRealtime());
                    int hour=(int)((SystemClock.elapsedRealtime()-timer.getBase())/1000/60);
                    timer.setFormat("0"+String.valueOf(hour)+" :%s");
                    timer.start();
                    currentLatLng = new LatLng(latitude, longitude);

                }
                LatLng lastLatLng = currentLatLng;

                currentLatLng = new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
                if(aMapLocation.getSpeed()!=0&&AMapUtils.calculateLineDistance(currentLatLng,lastLatLng)>0){
                    if(app.getA()=="1:1"){
                        movedDisdance= AMapUtils.calculateLineDistance(currentLatLng,lastLatLng);
                    }else if(app.getA()=="1:2"){
                        movedDisdance= (AMapUtils.calculateLineDistance(currentLatLng,lastLatLng))*2;
                    }else if(app.getA()=="1:5"){
                        movedDisdance= AMapUtils.calculateLineDistance(currentLatLng,lastLatLng)*5;
                    }else  if(app.getA()=="1:10"){
                        movedDisdance= AMapUtils.calculateLineDistance(currentLatLng,lastLatLng)*10;
                    }
                    totalDistance +=movedDisdance;

                }
//                float movedDisdance=Math.round(AMap.GeometryUtil.distance(currentLatLng,lastLatLng));

//                aMap.addPolyline(new PolylineOptions().add(lastLatLng,currentLatLng).width(10).color(Color.argb(255,1,1,1)));
//                totalDistance +=movedDisdance;
                //??????????????????????????????
                TextView s=findViewById(R.id.distance);
                TextView sp=findViewById(R.id.speed);
                s.setText(totalDistance+"m");
                sp.setText(aMapLocation.getSpeed()+"m/s");
                sppeed= (String) sp.getText();
               while (true){
                   if(totalDistance<ss1){
                       break;
                   }else if(i==0&&totalDistance>=ss1){
                       aMap.addPolyline(new PolylineOptions().addAll(latLngs2).width(10).color(Color.BLUE));
                       i=i+1;
                       break;
                   }else if(i==1&&totalDistance>=ss1+ss2){
                       aMap.addPolyline(new PolylineOptions().addAll(latLngs3).width(10).color(Color.BLUE));
                       i=i+1;
                       break;
                   }else if(i==2&&totalDistance>=ss1+ss2+ss3){
                       aMap.addPolyline(new PolylineOptions().addAll(latLngs4).width(10).color(Color.BLUE));
                       i=i+1;
                       break;
                   }else if(i==3&&totalDistance>=ss1+ss2+ss3+ss4){
                       aMap.addPolyline(new PolylineOptions().addAll(latLngs5).width(10).color(Color.BLUE));
                       i=i+1;
                       break;
                   }else if(i==4&&totalDistance>=ss1+ss2+ss3+ss4+ss5){
                       aMap.addPolyline(new PolylineOptions().addAll(latLngs6).width(10).color(Color.BLUE));
                       i=i+1;
                       break;
                   }else if(i==5&&totalDistance>=ss1+ss2+ss3+ss4+ss5+ss6){
                       aMap.addPolyline(new PolylineOptions().addAll(latLngs7).width(10).color(Color.BLUE));
                       i=i+1;
                       break;
                   }else if(i==6&&totalDistance>=ss1+ss2+ss3+ss4+ss5+ss6+ss7){
                       aMap.addPolyline(new PolylineOptions().addAll(latLngs8).width(10).color(Color.BLUE));
                       i=i+1;
                       break;
                   }else if(i==7&&totalDistance>=ss1+ss2+ss3+ss4+ss5+ss6+ss7+ss8){
                       aMap.addPolyline(new PolylineOptions().addAll(latLngs9).width(10).color(Color.BLUE));
                       i=i+1;
                       break;
                   }else if(i==8&&totalDistance>=ss1+ss2+ss3+ss4+ss5+ss6+ss7+ss8+ss9){
                       aMap.addPolyline(new PolylineOptions().addAll(latLngs10).width(10).color(Color.BLUE));
                       i=i+1;
                       break;
                   }else {
                       break;
                   }
               }

//                mLocationClient.stopLocation();
                if(mListener !=null){
                    mListener.onLocationChanged(aMapLocation);
                }
            } else {
                //???????????????????????????ErrCode????????????????????????????????????????????????errInfo???????????????????????????????????????
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
    private void showMsg(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }





}