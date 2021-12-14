package com.llw.run.ui.notifications;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.llw.run.Data;
import com.llw.run.R;
import com.llw.run.RecordActivity;
import com.llw.run.enter_roam1Activity;
import com.llw.run.ge_renActivity;
import com.llw.run.leiji_Activity;
import com.llw.run.she_zhiActivity;
import com.llw.run.xihuActivity;
import com.llw.run.xzActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.mob.tools.utils.DeviceHelper.getApplication;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private float a,b,c;
    String nic2,medals;
    Bitmap bitmapALL2=null; //后端数据
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        ImageView hBack=root.findViewById(R.id.h_back);
        ImageView hHead=root.findViewById(R.id.h_head);
        final Data app = (Data)getApplication();
        new Thread(){
            @Override
            public void run() {
                try {
//                    Thread.sleep(5000);
//                            String path="http://47.113.226.119:8080/register";
                    String path="http://10.21.234.20:8080/"+app.getUid()+"/queryTotalUserMsgByUid";
                    URL url = new URL(path);
                    //打开httpurlconnection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");              //设置POST方式获取数据
                    conn.setConnectTimeout(5000);              //设置连接超时时间5秒
                    conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  //如果设置方式为post，则必须制定该属性
                    //将数据进行编码,然后会自动的将该数据放到post中传到后台
//                        String data="password="+ URLEncoder.encode(new_mima.getText().toString().trim(),"utf-8");
                    //指定长度
//                        conn.setRequestProperty("Content-length",String.valueOf(data.length()));
                    conn.setDoOutput(true); //指定输出模式
//                        conn.getOutputStream().write(data.getBytes());  //将要传递的数据写入输出流
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
                        Log.d("所有信息", result);
                        JSONObject result_json=new JSONObject(result);
                        a=Float.parseFloat(result_json.getString("totalFreeRunMiles"));
                        b=Float.parseFloat(result_json.getString("totalRomanticRunMiles"));
                        medals=result_json.getString("medals");

                        nic2=result_json.getString("username");
                        app.setNic2(nic2);
                        if(medals.equals("null")){
                            medals="0";
                        }
                        app.setMedials(medals);
                        c=a+b;
                        app.setC(c);
                        String toux=result_json.getString("avatar");

                        try {
                            byte[]bitmapArray;
                            bitmapArray= Base64.decode(toux, Base64.DEFAULT);
                            bitmapALL2= BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                            if(bitmapALL2==null){
                                Resources res = NotificationsFragment.this.getResources();
                                bitmapALL2= BitmapFactory.decodeResource(res, R.drawable.touxiang);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        app.setToux(bitmapALL2);


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



        //设置背景磨砂效果
        Glide.with(getActivity()).load(R.id.toux)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(hBack);
//        //设置圆形图像
//        Glide.with(getActivity()).load(R.id.toux)
//                .bitmapTransform(new CropCircleTransformation(getActivity()))
//                .into(hHead);
        hHead.setImageBitmap(app.getToux());


        //显示用户名
        TextView mz=root.findViewById(R.id.mz);
        mz.setText(app.getNic2());
        //累计公里数
        LinearLayout leiji=root.findViewById(R.id.leiji);
        //
        TextView lanse=root.findViewById(R.id.lanse);
        lanse.setText(app.getC()+"");

        leiji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), leiji_Activity.class);
                startActivity(intent);
            }
        });
        //勋章数
        TextView hse=root.findViewById(R.id.hongse);
        hse.setText(app.getMedials()+"");
        LinearLayout xunz=root.findViewById(R.id.xunz);
        xunz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), xzActivity.class);
                startActivity(intent);
            }
        });
        //个人信息
        LinearLayout geren=root.findViewById(R.id.geren);
        geren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ge_renActivity.class);
                startActivity(intent);
            }
        });
        //跑步
        LinearLayout paobu=root.findViewById(R.id.paobu);
        paobu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecordActivity.class);
                startActivity(intent);
            }
        });

        //设置
        LinearLayout shezhi=root.findViewById(R.id.shezhi);
        shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), she_zhiActivity.class);
                startActivity(intent);
            }
        });



        return root;
    }

}