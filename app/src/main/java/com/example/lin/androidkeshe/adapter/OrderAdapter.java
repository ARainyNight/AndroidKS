package com.example.lin.androidkeshe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.entity.Order;
import com.example.lin.androidkeshe.json.QueryOrderJS;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by lin on 2018/7/3.
 * 描述:
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private List<QueryOrderJS> orderList  ;

    private QueryOrderJS order ;

    private Context mContext;

    public OrderAdapter(List<QueryOrderJS> orders){
        this.orderList = orders;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item,parent,false);
        mContext =parent.getContext();
        ViewHolder  holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        order = orderList.get(position);
        //holder.orderimg_Id .setImageResource(order.getOrderimg_id());
        Glide.with(mContext).load(order.getCommimgid()).into(holder.orderimg_Id);
        holder.order_business_name.setText(order.getCommbusinessname());
        holder.order_content.setText(order.getCommname());
        holder.order_price.setText(order.getCommprice());

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView orderimg_Id;
        TextView order_business_name;
        TextView order_content;
        TextView order_price;

        public ViewHolder(View itemView) {
            super(itemView);
            orderimg_Id= (ImageView)itemView.findViewById(R.id.order_commimg);
            order_business_name =(TextView)itemView.findViewById(R.id.order_commbusinessname);
            order_content =(TextView)itemView.findViewById(R.id.order_commname);
            order_price=(TextView)itemView.findViewById(R.id.order_price);
        }
    }
}
