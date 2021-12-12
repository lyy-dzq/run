package com.llw.run.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.llw.run.R;
import com.llw.run.entity.Replay;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReplayAdapter extends RecyclerView.Adapter<ReplayAdapter.ViewHolder> {

    private Context context;
    private List<Replay> replays;

    public ReplayAdapter(Context context, List<Replay> replays) {
        this.context = context;
        this.replays = replays;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_replay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tvContent.setText(replays.get(position).getContent());
        holder.tvName.setText(replays.get(position).getName());

        Glide.with(context).load("https://img1.baidu.com/it/u=3303981320,1355171730&fm=26&fmt=auto").into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        return replays.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}