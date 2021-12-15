package com.llw.run;

import android.Manifest;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.llw.run.presenter.FabuPresenter;
import com.llw.run.presenter.impl.FabuPresenterImp;
import com.llw.run.ui.adapter.SelectImageAdapter;
import com.llw.run.utils.Base64Utils;
import com.llw.run.view.FabuView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class fa_buActivity extends TakePhotoActivity implements FabuView {

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

    private FabuPresenter fabuPresenter;

    private List<String> paths = new ArrayList<>();
    private SelectImageAdapter selectImageAdapter;
    private int MaxLenth=150;
    private int ReLenth=MaxLenth;

    private TakePhoto takePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fa_bu);
        ButterKnife.bind(this);

        fabuPresenter = new FabuPresenterImp(this);

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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvTotal.setText(""+ReLenth);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ReLenth=MaxLenth-etContent.length();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvTotal.setText(""+ReLenth);
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

                List<String> images = new ArrayList<>();

                for (String pathStr : paths) {

                    images.add(Base64Utils.bitmapToBase64(BitmapFactory.decodeFile(pathStr)));

                }

                String content = etContent.getText().toString();

                if (TextUtils.isEmpty(content) && paths.size() == 0) {
                    Toast.makeText(this, "请发布文字或者图片", Toast.LENGTH_LONG).show();
                    return;
                }

                fabuPresenter.fabu("4eb396dd-c6a3-45c3-a7d0-d96f3399fc78", images, content);
//
//
//                List<Replay> replays = new ArrayList<>();
//
//                FriendMoment friendMoment = new FriendMoment();
//                friendMoment.setContent(content);
//                friendMoment.setImagePath(paths);
//                friendMoment.setTime(new Date().getTime());
//                friendMoment.setReplays(replays);
//
//                LitePal.saveAll(replays);
//
//                if (friendMoment.save()) {
//                    Toast.makeText(this, "发布成功", Toast.LENGTH_LONG).show();
//                    finish();
//                } else {
//                    Toast.makeText(this, "发布失败", Toast.LENGTH_LONG).show();
//                }


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

    @Override
    public void onFabuResponse(boolean success) {

        if (success) {
            Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
            finish();
        } else
            Toast.makeText(this, "发布失败", Toast.LENGTH_SHORT).show();

    }
}

