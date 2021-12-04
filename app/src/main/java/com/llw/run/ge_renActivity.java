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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.provider.MediaStore;

import static com.mob.tools.utils.DeviceHelper.getApplication;


public class ge_renActivity extends AppCompatActivity {
    protected static final int CHOOSE_PHOTO = 2;

    TextView sri2;
    private ImageView picture;
    Uri tempUri;
    Parcelable mBitmap;
    String[] sexArry;//性别点击
    TextView x_b;//性别
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        final Data app = (Data)getApplication();
        setContentView(R.layout.activity_ge_ren);
        //昵称
        TextView mz=findViewById(R.id.nicheng);
        mz.setText(app.getinto());
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
       sexArry = new String[]{"不告诉你", "女", "男"};// 性别选择
       LinearLayout xb=findViewById(R.id.xb);
       xb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showSexChooseDialog();
           }
       });


        //头像上传
        picture = (ImageView) findViewById(R.id.picture);
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
        if(imagePath!=null){
            Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
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
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中
            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                x_b.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }




}