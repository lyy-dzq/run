 package com.llw.run;
import java.util.Date;

import android.os.SystemClock;
import android.text.format.DateFormat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.col.sl3.hj;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;



public class free_beginningActivity extends AppCompatActivity implements AMapLocationListener,LocationSource {


    //请求权限码
    private static final int REQUEST_PERMISSIONS = 9527;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private MapView mapView;
    //地图控制器
    private AMap aMap = null;
    //位置更改监听
    private LocationSource.OnLocationChangedListener mListener;

    private LatLng currentLatLng;
    private long totalDistance=0;
    private String sppeed;
    Chronometer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_free_beginning);
        timer=(Chronometer)findViewById(R.id.timer);
//        mapView=findViewById(R.id.map_view);
//        mapView.onCreate(savedInstanceState);
        initLocation();
        initMap(savedInstanceState);

        mLocationClient.startLocation();
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
            }
        });
    }


    /**
     * Toast提示
     * @param msg 提示内容
     */
    private void showMsg(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * 初始化定位
     */
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
        mLocationOption.setHttpTimeOut(30000);
        mLocationOption.setInterval(3000);
        //关闭缓存机制，高精度定位会产生缓存。
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//        new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    mLocationClient.startLocation();//启动定位
//                }
//            }).start();

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
//                showMsg(address);
                //获取维度
                double latitude=aMapLocation.getLatitude();
                //获取经度
                double longitude=aMapLocation.getLongitude();
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
                float movedDisdance=AMapUtils.calculateLineDistance(currentLatLng,lastLatLng);
//                float movedDisdance=Math.round(AMap.GeometryUtil.distance(currentLatLng,lastLatLng));
                //绘制移动路线
                aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(final CameraPosition cameraPosition) {
                        //官方文档有更详细的说明
                        aMap.addPolyline(new PolylineOptions().add(lastLatLng,currentLatLng).width(10).color(Color.argb(255,1,1,1)));

                        totalDistance +=movedDisdance;
                    }
                    @Override
                    public void onCameraChangeFinish(final CameraPosition cameraPosition) {

                    }
                });
//                aMap.addPolyline(new PolylineOptions().add(lastLatLng,currentLatLng).width(10).color(Color.argb(255,1,1,1)));
//                totalDistance +=movedDisdance;

                //界面显示总里程和速度
                TextView s=findViewById(R.id.distance);
                TextView sp=findViewById(R.id.speed);
                s.setText(totalDistance+"m");
                sp.setText(aMapLocation.getSpeed()+"m/s");
                sppeed= (String) sp.getText();
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁定位客户端，同时销毁本地定位服务。
        mLocationClient.onDestroy();
        if(null != mLocationClient){
            mLocationClient.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }
    /**
     * 初始化地图
     * @param savedInstanceState
     */
    private void initMap(Bundle savedInstanceState) {
        mapView = findViewById(R.id.map_view2);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        aMap = mapView.getMap();
        //设置最小缩放等级为16 ，缩放级别范围为[3, 20]
//        aMap.setMinZoomLevel(19);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(20));
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        ////隐藏缩放按钮
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setMyLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        aMap.getUiSettings().setAllGesturesEnabled(false);//禁止地图可拖动


    }
    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
//            mLocationClient.startLocation();//启动定位
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
//            mLocationClient.stopLocation();
//            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

}