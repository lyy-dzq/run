package com.llw.run.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.llw.run.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectImageAdapter extends RecyclerView.Adapter<SelectImageAdapter.ViewHolder> {

    private Context context;
    private List<String> paths;
    private OnImageClickListener onClickListener;

    public SelectImageAdapter(Context context, List<String> paths, OnImageClickListener onClickListener) {
        this.context = context;
        this.paths = paths;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (position == paths.size()) {

            holder.ivDeleteImage.setVisibility(View.GONE);
            Glide.with(context).load(R.mipmap.addiamge).into(holder.ivImage);
        } else {
            holder.ivDeleteImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(paths.get(position)).into(holder.ivImage);
        }

        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == paths.size()) {
                    onClickListener.onSelectClicked(position);
                }

            }
        });
        holder.ivDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onDelete(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (paths.size() == 9) {
            return 9;
        } else {
            return paths.size() + 1;
        }


    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.iv_delete)
        ImageView ivDeleteImage;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public interface OnImageClickListener {
        void onSelectClicked(int position);

        void onDelete(int position);
    }
}