package com.llw.run;

import android.icu.text.AlphabeticIndex;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private List<Records> mRecordList;


    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item,parent,false);
        RecordAdapter.ViewHolder holder=new RecordAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.ViewHolder holder, int position) {
        Records records=mRecordList.get(position);
        holder.recordTime.setText(records.getTime());
        holder.recordType.setText(records.getType());
        holder.recordGlis.setText(records.getGlis());
        holder.recordSchangs.setText(records.getSchangs());

    }

    @Override
    public int getItemCount() {
        return mRecordList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView recordTime;
        TextView recordType;
        TextView recordGlis;
        TextView recordSchangs;

        public ViewHolder(View view){
            super(view);
            recordTime=(TextView)view.findViewById(R.id.time);
            recordType=(TextView)view.findViewById(R.id.type);
            recordGlis=(TextView)view.findViewById(R.id.glis);
            recordSchangs=(TextView)view.findViewById(R.id.schangs);
        }
    }
    public RecordAdapter(List<Records> recordsList){
        mRecordList=recordsList;
    }
}
