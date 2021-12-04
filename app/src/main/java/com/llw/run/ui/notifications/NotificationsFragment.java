package com.llw.run.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
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
import com.llw.run.enter_roam1Activity;
import com.llw.run.ge_renActivity;
import com.llw.run.leiji_Activity;
import com.llw.run.she_zhiActivity;
import com.llw.run.xihuActivity;
import com.llw.run.xzActivity;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.mob.tools.utils.DeviceHelper.getApplication;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        ImageView hBack=root.findViewById(R.id.h_back);
        ImageView hHead=root.findViewById(R.id.h_head);
        final Data app = (Data)getApplication();

        //设置背景磨砂效果
        Glide.with(getActivity()).load(R.drawable.touxiang)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(hBack);
        //设置圆形图像
        Glide.with(getActivity()).load(R.drawable.touxiang)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(hHead);
        //显示用户名
        TextView mz=root.findViewById(R.id.mz);
        mz.setText(app.getinto());
        //累计公里数
        LinearLayout leiji=root.findViewById(R.id.leiji);
        leiji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), leiji_Activity.class);
                startActivity(intent);
            }
        });
        //勋章数
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