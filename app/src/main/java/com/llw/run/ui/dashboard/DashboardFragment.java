package com.llw.run.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.llw.run.FriendDetailActivity;
import com.llw.run.R;
import com.llw.run.entity.ReplayEvent;
import com.llw.run.fa_buActivity;
import com.llw.run.http.res.FriendRes;
import com.llw.run.presenter.DynamicPresenter;
import com.llw.run.presenter.impl.DymicPresenterImp;
import com.llw.run.ui.adapter.FriendAdapter;
import com.llw.run.utils.IntentDateUtils;
import com.llw.run.view.DynamicView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements DynamicView {

    private DashboardViewModel dashboardViewModel;

    RecyclerView rvRun;

    private DynamicPresenter dynamicPresenter;

    private List<FriendRes> friendMoments = new ArrayList<>();
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

                FriendRes friendRes = new FriendRes();
                friendRes.setCommentNumber(friendMoments.get(position).getCommentNumber());
                friendRes.setCommentWithDynamics(friendMoments.get(position).getCommentWithDynamics());
                friendRes.setDid(friendMoments.get(position).getDid());
                friendRes.setIssue(friendMoments.get(position).getIssue());
                friendRes.setLikeNumber(friendMoments.get(position).getLikeNumber());
                friendRes.setUsername(friendMoments.get(position).getUsername());
                friendRes.setIssueTime(friendMoments.get(position).getIssueTime());

                IntentDateUtils intentDateUtils = IntentDateUtils.getInstance();
                intentDateUtils.setImages(friendMoments.get(position).getPics());

                Intent intent = new Intent(getActivity(), FriendDetailActivity.class);
                intent.putExtra("friendMoment", friendRes);
                startActivity(intent);

            }

            @Override
            public void onZanClicked(int position) {

//                Replay replay = new Replay();
//                replay.setContent("你好");
//                replay.setName("大师兄");
//                List<Replay> replays = new ArrayList<>();
//                replays.add(replay);
//
//
//                friendMoments.get(0).setReplays(replays);
//
//                LitePal.saveAll(friendMoments.get(0).getReplays());
//
//                friendMoments.get(0).save();


            }
        });

        rvRun.setAdapter(friendAdapter);

        dynamicPresenter = new DymicPresenterImp(this);

        EventBus.getDefault().register(this);

        return root;
    }


    @Override
    public void onResume() {
        super.onResume();

        dynamicPresenter.getDynamicList();

    }


    /**
     * 收到回复数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataReceived(ReplayEvent testDataEvent) {

//        List<Replay> replays = new ArrayList<>();
//        replays.add(testDataEvent.getReplay());
//
//        friendMoments.get(currentPos).setReplays(replays);
//
//        LitePal.saveAll(friendMoments.get(currentPos).getReplays());
//
//        friendMoments.get(currentPos).save();
    }


    @Override
    public void onDataResponse(List<FriendRes> friendResList) {

        friendMoments.clear();

        friendMoments.addAll(friendResList);

        friendAdapter.notifyDataSetChanged();

    }

    @Override
    public void onShow(boolean show) {

    }
}