package com.llw.run;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.UnicodeSetSpanner;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.provider.MediaStore;

import org.json.JSONObject;

import static com.mob.tools.utils.DeviceHelper.getApplication;


public class ge_renActivity extends AppCompatActivity {
    protected static final int CHOOSE_PHOTO = 2;
    final Data appp = (Data)getApplication();

    TextView sri2;
    private ImageView picture;
    Uri tempUri;
    Parcelable mBitmap;
    String[] sexArry;//性别点击
    TextView x_b;//性别
    Bitmap bitmapALL2=null; //后端数据

    String nic2;
    String sex2;
    String birth2;
    String qian_m2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        final Data app = (Data)getApplication();
        setContentView(R.layout.activity_ge_ren);
//


        inihouduan();

        //昵称
        TextView mz=findViewById(R.id.nicheng);
        mz.setText(app.getNic2());
        LinearLayout nicall=findViewById(R.id.nc_all);
        nicall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ge_renActivity.this, ni_chengActivity.class);
                startActivity(intent);
            }
        });
        //个性签名
             //调用后端
        TextView qm2=findViewById(R.id.qm2);
        qm2.setText(app.getQian_m2());
        LinearLayout qm=findViewById(R.id.qianm);
        qm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ge_renActivity.this, qian_mActivity.class);
                startActivity(intent);
            }
        });
        //性别选择
       x_b=findViewById(R.id.x_b);
       x_b.setText(app.getSex2());
       sexArry = new String[]{ "boy", "girl"};// 性别选择
       LinearLayout xb=findViewById(R.id.xb);
       xb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showSexChooseDialog();

           }
       });


        //头像上传
        picture = (ImageView) findViewById(R.id.picture);
        picture.setImageBitmap(app.getToux());
        LinearLayout toux=findViewById(R.id.toux);
        toux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(ge_renActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ge_renActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
            }
        });


        //生日
        sri2=findViewById(R.id.sri2);
        sri2.setText(app.getBirth2());
        LinearLayout sri=findViewById(R.id.sri);
        sri.setFocusable(false);
        sri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTimePicker1();
            }
        });
    }


    //头像
    private void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this,"you denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){

                    if(Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data);
                    }else {

                        handleImageBeforeKitKat(data);
                    }
                }

                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads//public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath=uri.getPath();
        }
        displayImage(imagePath);
    }
    private void handleImageBeforeKitKat(Intent data){
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }
    private String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return  path;
    }
    private void displayImage(String imagePath){
        final Data app2 = (Data)getApplication();

        if(imagePath!=null){
            Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
            //
            String base64=bitmapToBase64(bitmap);

            new Thread(){
                @Override
                public void run() {
                    try {
                        String JSON_URL="http://10.21.234.20:8080/";
                        JSON_URL=JSON_URL+app2.getUid()+"/insertAvatorByUid?"+"avator="+base64;
                        Log.d("头像", JSON_URL);
                        URL url = new URL(JSON_URL);
                        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                        urlConn.setRequestMethod("GET");
//                        urlConn.setConnectTimeout(80000);
//                        urlConn.setReadTimeout(80000);
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
                        Log.d("头像",result);
                    } catch (Exception e) {
                        Log.d("跑", "....");
                        e.printStackTrace();

                    }
                }
            }.start();

            picture.setImageBitmap(bitmap);


        }else {
            Toast.makeText(this,"failed to get imaage",Toast.LENGTH_SHORT).show();
        }
    }

    //选择出生年月日
    private void initTimePicker1() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)

        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11

        Date curDate = new Date(System.currentTimeMillis());//获取当前时间

        SimpleDateFormat formatter_year = new SimpleDateFormat("yyyy ");

        String year_str = formatter_year.format(curDate);

        int year_int = (int) Double.parseDouble(year_str);
        SimpleDateFormat formatter_mouth = new SimpleDateFormat("MM ");
        String mouth_str = formatter_mouth.format(curDate);
        int mouth_int = (int) Double.parseDouble(mouth_str);
        SimpleDateFormat formatter_day = new SimpleDateFormat("dd ");

        String day_str = formatter_day.format(curDate);

        int day_int = (int) Double.parseDouble(day_str);



        Calendar selectedDate = Calendar.getInstance();//系统当前时间

        Calendar startDate = Calendar.getInstance();

        startDate.set(1900, 0, 1);

        Calendar endDate = Calendar.getInstance();

        endDate.set(year_int, mouth_int - 1, day_int);

        //时间选择器

        TimePickerView pvTime1 = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {

            @Override

            public void onTimeSelect(Date date, View v) {//选中事件回调

                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null

                /*btn_Time.setText(getTime(date));*/

                sri2.setText(getTime(date));
                final Data app3 = (Data)getApplication();

                //插入生日
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            String JSON_URL="http://10.21.234.20:8080/";
                            JSON_URL=JSON_URL+app3.getUid()+"/insertBirthByUid?"+"birth="+getTime(date);
                            Log.d("生日", JSON_URL);
                            URL url = new URL(JSON_URL);
                            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                            urlConn.setRequestMethod("GET");
                            urlConn.setConnectTimeout(80000);
                            urlConn.setReadTimeout(80000);
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
                            Log.d("生日",result);
                        } catch (Exception e) {
                            Log.d("跑", "....");
                            e.printStackTrace();

                        }
                    }
                }.start();

            }

        })
                .setType(new boolean[]{true, true, true, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示

                .setLabel("年", "月", "日", "", "", "")//默认设置为年月日时分秒

                .isCenterLabel(false)

                .setDividerColor(Color.RED)

                .setTextColorCenter(Color.BLUE)//设置选中项的颜色

                .setTextColorOut(Color.BLACK)//设置没有被选中项的颜色

                .setContentSize(21)

                .setDate(selectedDate)

                .setLineSpacingMultiplier(1.2f)

                .setTextXOffset(-10, 0, 10, 0, 0, 0)//设置X轴倾斜角度[ -90 , 90°]

                .setRangDate(startDate, endDate)
                .setDecorView(null)//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .build();
        pvTime1.setDate(Calendar.getInstance());
        pvTime1.show();
    }
    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    //性别
    private void showSexChooseDialog() {
        final Data app4 = (Data)getApplication();

        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中
            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                x_b.setText(sexArry[which]);
                //插入性别
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            String JSON_URL="http://10.21.234.20:8080/";
                            JSON_URL=JSON_URL+app4.getUid()+"/insertSexByUid?"+"sex="+sexArry[which];
                            Log.d("性别", JSON_URL);
                            URL url = new URL(JSON_URL);
                            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                            urlConn.setRequestMethod("GET");
                            urlConn.setConnectTimeout(80000);
                            urlConn.setReadTimeout(80000);
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
                            Log.d("性别",result);
                        } catch (Exception e) {
                            Log.d("跑", "....");
                            e.printStackTrace();

                        }
                    }
                }.start();

                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }
    //转64码
    /*
     * bitmap转base64
     * */
    private static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /*end*/
 private void inihouduan(){
     final Data appo = (Data)getApplication();

     //个人所有信息调用
     new Thread(){
         @Override
         public void run() {
             try {
//                    Thread.sleep(5000);
//                            String path="http://47.113.226.119:8080/register";
                 String path="http://10.21.234.20:8080/"+appo.getUid()+"/queryTotalUserMsgByUid";
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
                     String toux=result_json.getString("avatar");

                     try {
                         byte[]bitmapArray;
                         bitmapArray=Base64.decode(toux, Base64.DEFAULT);
                         bitmapALL2= BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                         if(bitmapALL2==null){
                             Resources res = ge_renActivity.this.getResources();
                             bitmapALL2= BitmapFactory.decodeResource(res, R.drawable.touxiang);

                         }
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                     nic2=result_json.getString("username");
                     sex2=result_json.getString("sex");
                     birth2=result_json.getString("birth");
                     qian_m2=result_json.getString("sigNature");
                     Log.d("生日日期", birth2);
                     appo.setToux(bitmapALL2);
                     appo.setNic2(nic2);
                     appo.setSex2(sex2);
                     appo.setBirth2(birth2);
                     appo.setQian_m2(qian_m2);
//                        a=Float.parseFloat(result_json.getString("totalFreeRunMiles"));
//                        b=Float.parseFloat(result_json.getString("totalRomanticRunMiles"));
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