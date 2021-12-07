package com.llw.run.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.llw.run.R;
import com.llw.run.free_runActivity2;
import com.llw.run.jiao_cActivity;
import com.llw.run.pai_hangActivity;
import com.llw.run.roam_runActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //漫跑点击事件
        Button bt_roam=root.findViewById(R.id.manpao);
        bt_roam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), roam_runActivity.class);
                startActivity(intent);
            }
        });
       //自由跑点击事件
        Button bt_free=root.findViewById(R.id.free_run);
        bt_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), free_runActivity2.class);
                startActivity(intent);

            }
        });
        //点击跑步教程
        TextView jiaoc=root.findViewById(R.id.paobu_vido);
        jiaoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), jiao_cActivity.class);
                startActivity(intent);
            }
        });
        //点击跑步排行榜
        TextView bandan=root.findViewById(R.id.bangdan);
        bandan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), pai_hangActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}