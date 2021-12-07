package com.llw.run;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<Order> mOrderList;


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order=mOrderList.get(position);
        holder.orderImage.setImageResource(order.getImageId());
        holder.orderName.setText(order.getUsername());
        holder.orderMile.setText(order.getMile());

    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView orderImage;
        TextView orderName;
        TextView orderMile;
        public ViewHolder(View view){
            super(view);
            orderImage=(ImageView)view.findViewById(R.id.order_image);
            orderName=(TextView)view.findViewById(R.id.order_name);
            orderMile=(TextView)view.findViewById(R.id.order_mile);
        }
    }
    public OrderAdapter(List<Order> orderList){
        mOrderList=orderList;
    }
}
