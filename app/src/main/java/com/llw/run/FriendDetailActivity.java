package com.llw.run;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.llw.run.http.res.FriendRes;
import com.llw.run.presenter.ReplayPresenter;
import com.llw.run.presenter.impl.ReplayPresenterImp;
import com.llw.run.ui.adapter.ImageAdapter;
import com.llw.run.ui.adapter.ReplayAdapter;
import com.llw.run.utils.CommUtils;
import com.llw.run.utils.IntentDateUtils;
import com.llw.run.view.ReplayView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendDetailActivity extends AppCompatActivity implements ReplayView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.text_del)
    TextView textDel;
    @BindView(R.id.tv_zan)
    TextView tv_zan;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rv_images)
    RecyclerView rvImages;
    @BindView(R.id.ll_zan)
    LinearLayout llZan;
    @BindView(R.id.rv_replay)
    RecyclerView rvReplay;
    @BindView(R.id.et_replay)
    EditText etReplay;
    @BindView(R.id.btn_replay)
    Button btnReplay;

    private FriendRes friendMoment;

    private ReplayPresenter replayPresenter;

    private ReplayAdapter replayAdapter;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_friend_detail);
        ButterKnife.bind(this);

        friendMoment = (FriendRes) getIntent().getSerializableExtra("friendMoment");

        Glide.with(this).load("https://img2.baidu.com/it/u=1960058469,2576593478&fm=26&fmt=auto").into(ivImage);
        tvName.setText("Youmu");

        tvDate.setText(friendMoment.getIssueTime());
        tv_zan.setText(friendMoment.getLikeNumber() + "");
        tvContent.setText(friendMoment.getIssue() + "");


        rvImages.setLayoutManager(new GridLayoutManager(this, 3));
        if (IntentDateUtils.getInstance().getImages() != null && IntentDateUtils.getInstance().getImages().size() > 0 && !TextUtils.isEmpty(IntentDateUtils.getInstance().getImages().get(0))) {
            rvImages.setAdapter(new ImageAdapter(this, IntentDateUtils.getInstance().getImages()));

        }

        replayAdapter = new ReplayAdapter(this, friendMoment.getCommentWithDynamics());
        rvReplay.setAdapter(replayAdapter);


        replayPresenter = new ReplayPresenterImp(this);

    }

    @OnClick({R.id.iv_back, R.id.text_del, R.id.btn_replay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.text_del:
                break;
            case R.id.btn_replay:
                String content = etReplay.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(this, "请输入回复内容", Toast.LENGTH_SHORT).show();
                    return;
                }

                FriendRes.CommentWithDynamicsEntity commentWithDynamicsEntity = new FriendRes.CommentWithDynamicsEntity();

                commentWithDynamicsEntity.setContent(content);
                commentWithDynamicsEntity.setUsername("Yummu");
                commentWithDynamicsEntity.setCommentTime(df.format(new Date().getTime()));


                friendMoment.getCommentWithDynamics().add(commentWithDynamicsEntity);


                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("uid", "4eb396dd-c6a3-45c3-a7d0-d96f3399fc78");
                jsonObject.addProperty("did", friendMoment.getDid());
                jsonObject.addProperty("fuid", "6fefc9e1-9de5-4fab-abb1-f3aeba18fbb6");
                jsonObject.addProperty("content", content);

                replayPresenter.replay(jsonObject);


//                Replay replay = new Replay();
//                replay.setContent(content);
//                replay.setName("大师兄");
//
//                friendMoment.getReplays().add(replay);
//
//                replayAdapter.notifyDataSetChanged();
//
//                ReplayEvent replayEvent = new ReplayEvent();
//                replayEvent.setReplay(replay);
//
//                EventBus.getDefault().post(replayEvent);

                CommUtils.hide(this);
                etReplay.setText("");

                break;
        }
    }

    @Override
    public void onReplayResponse(boolean success) {
        if (success) {
            Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
            replayAdapter.notifyDataSetChanged();
        } else
            Toast.makeText(this, "发布失败", Toast.LENGTH_SHORT).show();

    }
}
