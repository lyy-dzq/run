package com.llw.run.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.llw.run.R;
import com.llw.run.http.res.FriendRes;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private Context context;
    private List<FriendRes> friendMoments;
    private OnClickListener onClickListener;

    private SimpleDateFormat df = new SimpleDateFormat("MM-dd");

    public FriendAdapter(Context context, List<FriendRes> friendMoments, OnClickListener onClickListener) {
        this.context = context;
        this.friendMoments = friendMoments;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tvContent.setText(friendMoments.get(position).getIssue());

        holder.rvImages.setLayoutManager(new GridLayoutManager(context, 3));
        if (friendMoments.get(position).getPics() != null && friendMoments.get(position).getPics().size() > 0) {
            if (!TextUtils.isEmpty(friendMoments.get(position).getPics().get(0))) {
                holder.rvImages.setAdapter(new ImageAdapter(context, friendMoments.get(position).getPics()));

                holder.rvImages.setVisibility(View.VISIBLE);
            }else {
                holder.rvImages.setVisibility(View.GONE);
            }
        }else {
            holder.rvImages.setVisibility(View.GONE);
        }
        holder.tvReplays.setText(friendMoments.get(position).getCommentNumber() + "");
        holder.tv_zan.setText(friendMoments.get(position).getLikeNumber() + "");

        holder.tvDate.setText(friendMoments.get(position).getIssueTime());

        Glide.with(context).load("https://img2.baidu.com/it/u=1960058469,2576593478&fm=26&fmt=auto").into(holder.ivImage);

        holder.llReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onReplayClicked(position);
            }
        });
        holder.llZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onZanClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendMoments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_replays)
        TextView tvReplays;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_zan)
        TextView tv_zan;
        @BindView(R.id.rv_images)
        RecyclerView rvImages;
        @BindView(R.id.ll_replay)
        LinearLayout llReplay;
        @BindView(R.id.ll_zan)
        LinearLayout llZan;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public interface OnClickListener {
        void onReplayClicked(int position);

        void onZanClicked(int position);
    }
}