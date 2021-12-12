package com.llw.run;

import android.Manifest;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.llw.run.entity.FriendMoment;
import com.llw.run.entity.Replay;
import com.llw.run.ui.adapter.SelectImageAdapter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class fa_buActivity extends TakePhotoActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.text_publish)
    TextView textPublish;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.rv_images)
    RecyclerView rvImages;

    private List<String> paths = new ArrayList<>();
    private SelectImageAdapter selectImageAdapter;
    private int MaxText=150;
    private int ReText=MaxText;

    private TakePhoto takePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fa_bu);
        ButterKnife.bind(this);

        takePhoto = getTakePhoto();

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                100);

        selectImageAdapter = new SelectImageAdapter(this, paths, new SelectImageAdapter.OnImageClickListener() {
            @Override
            public void onSelectClicked(int position) {

                takePhoto.onPickMultiple(9);
            }

            @Override
            public void onDelete(int position) {
                paths.remove(position);
                selectImageAdapter.notifyDataSetChanged();

            }
        });

        rvImages.setLayoutManager(new GridLayoutManager(this, 3));
        rvImages.setAdapter(selectImageAdapter);

        tvTotal.setText("150");
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tvTotal.setText(""+ReText);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ReText=(MaxText-etContent.length());

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvTotal.setText(""+ReText);

            }
        });


    }


    @OnClick({R.id.iv_back, R.id.text_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.text_publish:

                String content = etContent.getText().toString();

                if (TextUtils.isEmpty(content) && paths.size() == 0) {
                    Toast.makeText(this, "请发布文字或者图片", Toast.LENGTH_LONG).show();
                    return;
                }

                List<Replay> replays = new ArrayList<>();

                FriendMoment friendMoment = new FriendMoment();
                friendMoment.setContent(content);
                friendMoment.setImagePath(paths);
                friendMoment.setTime(new Date().getTime());
                friendMoment.setReplays(replays);

                LitePal.saveAll(replays);

                if (friendMoment.save()) {
                    Toast.makeText(this, "发布成功", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(this, "发布失败", Toast.LENGTH_LONG).show();
                }


                break;
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);

        ArrayList<TImage> images = result.getImages();
        if (images.size() > 0) {
            for (TImage image : images) {
                if (paths.size() < 9) {
                    paths.add(image.getOriginalPath());
                }
            }
        }

        selectImageAdapter.notifyDataSetChanged();

    }
}

