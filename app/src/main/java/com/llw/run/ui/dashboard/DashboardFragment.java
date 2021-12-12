package com.llw.run.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.llw.run.FriendDetailActivity;
import com.llw.run.R;
import com.llw.run.entity.FriendMoment;
import com.llw.run.entity.Replay;
import com.llw.run.entity.ReplayEvent;
import com.llw.run.fa_buActivity;
import com.llw.run.ui.adapter.FriendAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    RecyclerView rvRun;

    private List<FriendMoment> friendMoments = new ArrayList<>();
    private FriendAdapter friendAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //点击发布
        Button tent = root.findViewById(R.id.tent);

        rvRun = root.findViewById(R.id.rv_run);
        tent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), fa_buActivity.class);
                startActivity(intent);
            }
        });

        friendAdapter = new FriendAdapter(getActivity(), friendMoments, new FriendAdapter.OnClickListener() {
            @Override
            public void onReplayClicked(int position) {
                currentPos = position;

                Intent intent = new Intent(getActivity(), FriendDetailActivity.class);
                intent.putExtra("friendMoment", friendMoments.get(position));
                startActivity(intent);

            }

            @Override
            public void onZanClicked(int position) {
                currentPos=position;



            }
        });

        rvRun.setAdapter(friendAdapter);

        EventBus.getDefault().register(this);
        return root;
    }


    @Override
    public void onResume() {
        super.onResume();


        friendMoments.clear();
        friendMoments.addAll(LitePal.order("id desc").find(FriendMoment.class, true));

        friendAdapter.notifyDataSetChanged();
    }


    private int currentPos;

    /**
     * 收到回复数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataReceived(ReplayEvent testDataEvent) {

        List<Replay> replays = new ArrayList<>();
        replays.add(testDataEvent.getReplay());

        friendMoments.get(currentPos).setReplays(replays);

        LitePal.saveAll(friendMoments.get(currentPos).getReplays());

        friendMoments.get(currentPos).save();
    }
}