package com.llw.run;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class tian_an_menActivity extends AppCompatActivity implements AMapLocationListener{
    MapView mMapView = null;
    AMap aMap;
    //不显示

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //跑步数据
    //位置更改监听
    private LocationSource.OnLocationChangedListener mListener;
    private LatLng currentLatLng;
    private float totalDistance=0;
    private float movedDisdance;
    int i=0;
    float ss1,ss2,ss3,ss4,ss5,ss6,ss7,ss8,ss9;
    List<LatLng> latLngs2 = new ArrayList<LatLng>();
    List<LatLng> latLngs3 = new ArrayList<LatLng>();
    List<LatLng> latLngs4 = new ArrayList<LatLng>();
    List<LatLng> latLngs5 = new ArrayList<LatLng>();
    List<LatLng> latLngs6 = new ArrayList<LatLng>();
    List<LatLng> latLngs7 = new ArrayList<LatLng>();
    List<LatLng> latLngs8 = new ArrayList<LatLng>();
    List<LatLng> latLngs9 = new ArrayList<LatLng>();
    List<LatLng> latLngs10 = new ArrayList<LatLng>();
    private String sppeed;
    Chronometer timer;
    //
    final Data app = (Data)getApplication();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_tian_an_men);
        final Data app2 = (Data)getApplication();
        totalDistance=app2.getJixu();
        Log.d("哇哈哈哈哈哈哈哈哈哈哈", totalDistance+"");


        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        timer=(Chronometer)findViewById(R.id.timer);

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        //不显示
        initLocation();
        mLocationClient.startLocation();
//跑步结束
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
                tanchu.setVisibility(View.VISIBLE);
                mLocationClient.stopLocation();
                mLocationClient.onDestroy();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
                //获取当前时间
                Date date = new Date(System.currentTimeMillis());
                xxi.setText(simpleDateFormat.format(date));
                //公里数、配速、时长
                distance2.setText(totalDistance/1000+"km");
                speed2.setText(sppeed);
                timer2.setText(timer.getText());
                stoprun.setVisibility(View.INVISIBLE);
            }
        });

        //退出、分享
        LinearLayout contentLayout=findViewById(R.id.tanchu);
        //分享退出
        TextView tchu=findViewById(R.id.tchu);
        tchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tian_an_menActivity.this, BottomNavigationActivity.class);
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

                // 分享本地图片
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM,uri);
                intent.setType("image/*");
                startActivity(Intent.createChooser(intent,"选择分享应用"));

            }
        });


        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.getUiSettings().setZoomControlsEnabled(false);
            CameraPosition cameraPosition = new CameraPosition(new LatLng(39.915576, 116.397135), 15, 0, 10);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            aMap.moveCamera(cameraUpdate);
            List<LatLng> latLngs = new ArrayList<LatLng>();

            latLngs.add(new LatLng(39.907832,116.401268));//天安门东站
            latLngs.add(new LatLng(39.907898,116.403199));//v1
            latLngs2.add(new LatLng(39.907832,116.401268));
            latLngs2.add(new LatLng(39.907898,116.403199));//v1
            float dis1=AMapUtils.calculateLineDistance(new LatLng(39.907832,116.401268),new LatLng(39.907898,116.403199));
            latLngs.add(new LatLng(39.908026,116.403261));//2
            latLngs2.add(new LatLng(39.908026,116.403261));//2
            float dis2=AMapUtils.calculateLineDistance(new LatLng(39.908026,116.403261),new LatLng(39.907898,116.403199));
            latLngs.add(new LatLng(39.908223,116.403314));//3
            latLngs2.add(new LatLng(39.908223,116.403314));//3
            float dis3=AMapUtils.calculateLineDistance(new LatLng(39.908223,116.403314),new LatLng(39.908026,116.403261));
            latLngs.add(new LatLng(39.909725,116.403239));//4
            latLngs2.add(new LatLng(39.909725,116.403239));
            float dis4=AMapUtils.calculateLineDistance(new LatLng(39.909725,116.403239),new LatLng(39.908223,116.403314));
            ss1=dis1+dis2+dis3+dis4;
            latLngs.add(new LatLng(39.909766,116.403234));//5
            latLngs3.add(new LatLng(39.909725,116.403239));
            latLngs3.add(new LatLng(39.909766,116.403234));//5
            float dis5=AMapUtils.calculateLineDistance(new LatLng(39.909766,116.403234),new LatLng(39.909725,116.403239));
            latLngs.add(new LatLng(39.910931,116.403175));//6
            latLngs3.add(new LatLng(39.910931,116.403175));//6
            float dis6=AMapUtils.calculateLineDistance(new LatLng(39.910931,116.403175),new LatLng(39.909766,116.403234));
            latLngs.add(new LatLng(39.911671,116.403121));//7
            latLngs3.add(new LatLng(39.911671,116.403121));//7
            float dis7=AMapUtils.calculateLineDistance(new LatLng(39.911671,116.403121),new LatLng(39.910931,116.403175));
            latLngs.add(new LatLng(39.912803,116.403078));//8
            latLngs3.add(new LatLng(39.912803,116.403078));//8
            float dis8=AMapUtils.calculateLineDistance(new LatLng(39.912803,116.403078),new LatLng(39.911671,116.403121));

     //南池子美术馆
            latLngs.add(new LatLng(39.91384,116.403025));//9
            latLngs3.add(new LatLng(39.91384,116.403025));//9
            float dis9=AMapUtils.calculateLineDistance(new LatLng(39.91384,116.403025),new LatLng(39.912803,116.403078));
            ss2=dis5+dis6+dis7+dis8+dis9;
            latLngs.add(new LatLng(39.915161,116.402971));//10
            latLngs4.add(new LatLng(39.91384,116.403025));//9
            latLngs4.add(new LatLng(39.915161,116.402971));//10
            float dis10=AMapUtils.calculateLineDistance(new LatLng(39.915161,116.402971),new LatLng(39.91384,116.403025));
            latLngs.add(new LatLng(39.917152,116.402901));
            latLngs4.add(new LatLng(39.917152,116.402901));
            float dis11=AMapUtils.calculateLineDistance(new LatLng(39.917152,116.402901),new LatLng(39.915161,116.402971));
            latLngs.add(new LatLng(39.917967,116.402853));
            latLngs4.add(new LatLng(39.917967,116.402853));
            float dis12=AMapUtils.calculateLineDistance(new LatLng(39.917967,116.402853),new LatLng(39.917152,116.402901));
            latLngs.add(new LatLng(39.91837,116.402837));
            latLngs4.add(new LatLng(39.91837,116.402837));
            float dis13=AMapUtils.calculateLineDistance(new LatLng(39.91837,116.402837),new LatLng(39.917967,116.402853));
            latLngs.add(new LatLng(39.919279,116.402805));
            latLngs4.add(new LatLng(39.919279,116.402805));
            float dis14=AMapUtils.calculateLineDistance(new LatLng(39.919279,116.402805),new LatLng(39.91837,116.402837));
            latLngs.add(new LatLng(39.920937,116.402687));
            latLngs4.add(new LatLng(39.920937,116.402687));
            float dis15=AMapUtils.calculateLineDistance(new LatLng(39.920937,116.402687),new LatLng(39.919279,116.402805));
            ss3=dis10+dis11+dis12+dis13+dis14+dis15;
     //宣仁庙
            latLngs.add(new LatLng(39.922476,116.402612));
            latLngs5.add(new LatLng(39.920937,116.402687));
            latLngs5.add(new LatLng(39.922476,116.402612));
            float dis16=AMapUtils.calculateLineDistance(new LatLng(39.922476,116.402612),new LatLng(39.920937,116.402687));
            latLngs.add(new LatLng(39.9235,116.402531));
            latLngs5.add(new LatLng(39.9235,116.402531));
            float dis17=AMapUtils.calculateLineDistance(new LatLng(39.9235,116.402531),new LatLng(39.922476,116.402612));
            latLngs.add(new LatLng(39.923389,116.399849));
            latLngs5.add(new LatLng(30.230585,120.155469));
            float dis18=AMapUtils.calculateLineDistance(new LatLng(30.230585,120.155469),new LatLng(39.9235,116.402531));
            latLngs.add(new LatLng(39.923426,116.399645));
            latLngs5.add(new LatLng(39.923426,116.399645));
            float dis19=AMapUtils.calculateLineDistance(new LatLng(39.923426,116.399645),new LatLng(30.230585,120.155469));

     //故宫博物院神武门
            latLngs.add(new LatLng(39.923327,116.39685));
            latLngs5.add(new LatLng(39.923327,116.39685));
            ss4=dis16+dis17+dis18+dis19;
            float dis20=AMapUtils.calculateLineDistance(new LatLng(39.923327,116.39685),new LatLng(39.923426,116.399645));
            latLngs.add(new LatLng(39.92322,116.393803));
            latLngs6.add(new LatLng(39.923327,116.39685));
            latLngs6.add(new LatLng(39.92322,116.393803));
            float dis21=AMapUtils.calculateLineDistance(new LatLng(39.92322,116.393803),new LatLng(39.923327,116.39685));

            latLngs.add(new LatLng(39.923149,116.391882));
            latLngs6.add(new LatLng(39.923149,116.391882));
            float dis22=AMapUtils.calculateLineDistance(new LatLng(39.923149,116.391882),new LatLng(39.92322,116.393803));

            latLngs.add(new LatLng(39.92298,116.39148));
            latLngs6.add(new LatLng(39.92298,116.39148));
            float dis23=AMapUtils.calculateLineDistance(new LatLng(39.92298,116.39148),new LatLng(39.923149,116.391882));

            latLngs.add(new LatLng(39.922766,116.391228));
            latLngs6.add(new LatLng(39.922766,116.391228));
            float dis24=AMapUtils.calculateLineDistance(new LatLng(39.922766,116.391228),new LatLng(39.92298,116.39148));

            latLngs.add(new LatLng(39.922527,116.39104));
            latLngs6.add(new LatLng(39.922527,116.39104));
            float dis25=AMapUtils.calculateLineDistance(new LatLng(39.922527,116.39104),new LatLng(39.922766,116.391228));
            ss5=dis20+dis21+dis22+dis23+dis24+dis25;
            latLngs.add(new LatLng(39.922466,116.391035));
            latLngs7.add(new LatLng(39.922527,116.39104));
            latLngs7.add(new LatLng(39.922466,116.391035));
            float dis26=AMapUtils.calculateLineDistance(new LatLng(39.922466,116.391035),new LatLng(39.922527,116.39104));

            latLngs.add(new LatLng(39.922075,116.390976));
            latLngs7.add(new LatLng(39.922075,116.390976));
            float dis27=AMapUtils.calculateLineDistance(new LatLng(39.922075,116.390976),new LatLng(39.922466,116.391035));

            latLngs.add(new LatLng(39.921935,116.391002));
            latLngs7.add(new LatLng(39.921935,116.391002));
            float dis28=AMapUtils.calculateLineDistance(new LatLng(39.921935,116.391002),new LatLng(39.922075,116.390976));

            latLngs.add(new LatLng(39.920335,116.391088));
            latLngs7.add(new LatLng(39.920335,116.391088));
            float dis29=AMapUtils.calculateLineDistance(new LatLng(39.920335,116.391088),new LatLng(39.921935,116.391002));
            ss6=dis21+dis22+dis23+dis24+dis25+dis26+dis27+dis28+dis29;
            latLngs.add(new LatLng(39.918664,116.391179));
            latLngs8.add(new LatLng(39.920335,116.391088));
            latLngs8.add(new LatLng(39.918664,116.391179));
            float dis30=AMapUtils.calculateLineDistance(new LatLng(39.918664,116.391179),new LatLng(30.234984,120.156541));
//北京市第一六一中学附属小学
            latLngs.add(new LatLng(39.918117,116.391206));
            latLngs8.add(new LatLng(39.918117,116.391206));
            float dis31=AMapUtils.calculateLineDistance(new LatLng(39.918117,116.391206),new LatLng(39.918664,116.391179));

            latLngs.add(new LatLng(39.9165,116.391303));
            latLngs8.add(new LatLng(39.9165,116.391303));
            float dis32=AMapUtils.calculateLineDistance(new LatLng(39.9165,116.391303),new LatLng(39.918117,116.391206));

            latLngs.add(new LatLng(39.915163,116.391372));
            latLngs8.add(new LatLng(39.915163,116.391372));
            float dis33=AMapUtils.calculateLineDistance(new LatLng(39.915163,116.391372),new LatLng(39.9165,116.391303));

            latLngs.add(new LatLng(39.913887,116.391464));
            latLngs8.add(new LatLng(39.913887,116.391464));
            float dis34=AMapUtils.calculateLineDistance(new LatLng(39.913887,116.391464),new LatLng(39.915163,116.391372));
            ss7=dis30+dis31+dis32+dis33+dis34;


            latLngs.add(new LatLng(39.912472,116.391582));
            latLngs9.add(new LatLng(39.913887,116.391464));
            latLngs9.add(new LatLng(39.912472,116.391582));
            float dis35=AMapUtils.calculateLineDistance(new LatLng(39.912472,116.391582),new LatLng(39.913887,116.391464));
            latLngs.add(new LatLng(39.911168,116.391694));
            latLngs9.add(new LatLng(39.911168,116.391694));
            float dis36=AMapUtils.calculateLineDistance(new LatLng(39.911168,116.391694),new LatLng(39.912472,116.391582));

            latLngs.add(new LatLng(39.910102,116.391782));
            latLngs9.add(new LatLng(39.910102,116.391782));
            float dis37=AMapUtils.calculateLineDistance(new LatLng(39.910102,116.391782),new LatLng(39.911168,116.391694));

            latLngs.add(new LatLng(39.908625,116.391868));
            latLngs9.add(new LatLng(39.908625,116.391868));
            float dis38=AMapUtils.calculateLineDistance(new LatLng(39.908625,116.391868),new LatLng(39.910102,116.391782));
            ss8=dis35+dis36+dis37+dis38;

            latLngs.add(new LatLng(39.908432,116.391873));
            latLngs10.add(new LatLng(39.908625,116.391868));
            latLngs10.add(new LatLng(39.908432,116.391873));
            float dis39=AMapUtils.calculateLineDistance(new LatLng(39.908432,116.391873),new LatLng(39.908625,116.391868));

            latLngs.add(new LatLng(39.907905,116.391879));
            latLngs10.add(new LatLng(39.907905,116.391879));
            float dis40=AMapUtils.calculateLineDistance(new LatLng(39.907905,116.391879),new LatLng(39.908432,116.391873));

            latLngs.add(new LatLng(39.907517,116.391766));  //终点
            latLngs10.add(new LatLng(39.907517,116.391766));
            float dis41=AMapUtils.calculateLineDistance(new LatLng(39.907517,116.391766),new LatLng(39.907905,116.391879));
            ss9=dis39+dis40+dis41;
            //添加标点
            aMap.addMarker(new MarkerOptions().position(new LatLng(39.907832,116.401268)).icon(BitmapDescriptorFactory.fromResource(R.drawable.begin)).title("起点").snippet("天安门东站"));//起点
            aMap.addMarker(new MarkerOptions().position(new LatLng(39.91384,116.403025)).icon(BitmapDescriptorFactory.fromResource(R.drawable.gross)).title("经过点").snippet("南池子美术馆"));//
            aMap.addMarker(new MarkerOptions().position(new LatLng(39.922476,116.402612)).icon(BitmapDescriptorFactory.fromResource(R.drawable.gross)).title("经过点").snippet("宣仁庙"));//
            aMap.addMarker(new MarkerOptions().position(new LatLng(39.923327,116.39685)).icon(BitmapDescriptorFactory.fromResource(R.drawable.gross)).title("经过点").snippet("故宫博物馆神武门"));//
            aMap.addMarker(new MarkerOptions().position(new LatLng(39.918117,116.391206)).icon(BitmapDescriptorFactory.fromResource(R.drawable.gross)).title("经过点").snippet("北京市第一六一中学附属小学"));
            aMap.addMarker(new MarkerOptions().position(new LatLng(39.907517,116.391766)).icon(BitmapDescriptorFactory.fromResource(R.drawable.end)).title("终点").snippet("天安门西站附近"));//断桥残雪

            aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(10).color(Color.BLUE).setDottedLine(true));


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
    //不显示
    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置定位请求超时时间，单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制，高精度定位会产生缓存。
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }
    /**
     * 接收异步返回的定位结果
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //地址
                String address = aMapLocation.getAddress();
                //获取维度
                double latitude=aMapLocation.getLatitude();
                //获取经度
                double longitude=aMapLocation.getLongitude();
                //跑步数据
                //首次定位时
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
                //界面显示总里程和速度
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
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
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