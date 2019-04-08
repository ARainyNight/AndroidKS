package com.example.lin.androidkeshe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.entity.Commodity;
import com.example.lin.androidkeshe.entity.OrderData;
import com.example.lin.androidkeshe.json.MenuJS;
import com.example.lin.androidkeshe.utils.L;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lin on 2018/7/3.
 * 描述:  商家展示 - ---- 菜单页
 */
public class CommodityAdapter extends RecyclerView.Adapter<CommodityAdapter.ViewHolder> {

    private List<MenuJS.DataBean> commodityList;
    private List<OrderData> orderDataList = new ArrayList<>();


    private MenuJS.DataBean commodity;
    private Context context;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{

        public void onClick(View view, int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public CommodityAdapter(List<MenuJS.DataBean> commodities) {

        this.commodityList = commodities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        context = parent.getContext();
        ViewHolder holder = new ViewHolder(view, mListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemView.setTag(position);
        commodity = commodityList.get(position);
        if (commodity.getImageUrls().size() != 0) {
            Glide.with(context).load(commodity.getImageUrls().get(0)).into(holder.comm_imgid);
        }else {
            holder.comm_imgid.setImageResource(R.drawable.businessshow);
        }
        holder.comm_name.setText(commodity.getTitle());
        holder.comm_price.setText(String.valueOf(commodity.getSkuOptions().get(0).getPrice()));
        holder.comm_desc.setText(commodity.getDescription());
    }

    @Override
    public int getItemCount() {
        return commodityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView comm_imgid;
        TextView comm_name;
        TextView comm_price;
        TextView comm_desc;

        private OnItemClickListener mListener;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);

            mListener = listener;


            comm_imgid = (ImageView) itemView.findViewById(R.id.comm_imgId);
            comm_name = (TextView) itemView.findViewById(R.id.comm_name);
            comm_price = (TextView) itemView.findViewById(R.id.comm_price);
            comm_desc = (TextView) itemView.findViewById(R.id.comm_desc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mListener!=null){
                        mListener.onClick(view,getAdapterPosition());
                    }

                }
            });

        }

    }
}
