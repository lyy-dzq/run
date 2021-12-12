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
import com.llw.run.entity.FriendMoment;
import com.llw.run.entity.Replay;
import com.llw.run.entity.ReplayEvent;
import com.llw.run.ui.adapter.ImageAdapter;
import com.llw.run.ui.adapter.ReplayAdapter;
import com.llw.run.utils.CommUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendDetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.text_del)
    TextView textDel;
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

    private FriendMoment friendMoment;

    private ReplayAdapter replayAdapter;
    private SimpleDateFormat df = new SimpleDateFormat("MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_friend_detail);
        ButterKnife.bind(this);

        friendMoment = (FriendMoment) getIntent().getSerializableExtra("friendMoment");
        tvName.setText("Y");

        Glide.with(this).load("https://img2.baidu.com/it/u=1960058469,2576593478&fm=26&fmt=auto").into(ivImage);
        tvName.setText(friendMoment.getName());

        tvDate.setText(df.format(friendMoment.getTime()));
        tvContent.setText(friendMoment.getContent());


        rvImages.setLayoutManager(new GridLayoutManager(this, 3));
        if (friendMoment.getImagePath() != null) {
            rvImages.setAdapter(new ImageAdapter(this, friendMoment.getImagePath()));
        }


        replayAdapter = new ReplayAdapter(this, friendMoment.getReplays());
        rvReplay.setAdapter(replayAdapter);

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


                Replay replay = new Replay();
                replay.setContent(content);
                replay.setName("大师兄");

                friendMoment.getReplays().add(replay);

                replayAdapter.notifyDataSetChanged();

                ReplayEvent replayEvent = new ReplayEvent();
                replayEvent.setReplay(replay);

                EventBus.getDefault().post(replayEvent);

                CommUtils.hide(this);
                etReplay.setText("");

                break;
        }
    }
}
