package com.example.lin.androidkeshe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.entity.Business;
import com.example.lin.androidkeshe.json.CanGuan;
import com.example.lin.androidkeshe.json.QueryCollectionJS;
import com.example.lin.androidkeshe.ui.activity.MyCollectionActivity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by lin on 2018/7/5.
 * 描述:
 */
public class MyCollectionAdapter extends RecyclerView.Adapter<MyCollectionAdapter.ViewHolder> {

    private List<QueryCollectionJS> mBusinessList ;
    private Context mContext ;

    private QueryCollectionJS business ;

    public MyCollectionAdapter(List<QueryCollectionJS> businessList) {
        this.mBusinessList = businessList;
    }

    private BusinessAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener{

        public void onClick(View view, int position);

    }

    public void setOnItemClickListener(BusinessAdapter.OnItemClickListener listener){

        this.mListener = listener;
    }


    @Override
    public MyCollectionAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_item, parent, false);
        mContext = parent.getContext();
        MyCollectionAdapter.ViewHolder holder = new MyCollectionAdapter.ViewHolder(view,mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyCollectionAdapter.ViewHolder holder, int position) {
        business = mBusinessList.get(position);

//        holder.business_imgId.setImageResource(business.get());
        holder.business_name.setText(business.getTitle());
        holder.business_location.setText(business.getAddress());
        holder.business_takeout_cost.setText(business.getDeliverfee() + "");
        holder.business_delivetime.setText(business.getDelivertime() + "");
        holder.business_rating.setText(business.getRating() + "");

    }

    @Override
    public int getItemCount() {
        return mBusinessList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View businessView;

        ImageView business_imgId;
        TextView business_name;
        TextView business_location;
        TextView business_takeout_cost;
        TextView business_delivetime;
        TextView business_rating;

        private BusinessAdapter.OnItemClickListener mListener;

        public ViewHolder(View itemView,BusinessAdapter.OnItemClickListener listener) {
            super(itemView);

            mListener = listener;

            businessView = itemView;
            business_imgId = (ImageView) itemView.findViewById(R.id.business_imgId);
            business_name = (TextView) itemView.findViewById(R.id.business_name);
            business_location = (TextView) itemView.findViewById(R.id.business_location);
            business_takeout_cost = (TextView) itemView.findViewById(R.id.business_takeout_cost);
            business_delivetime = (TextView) itemView.findViewById(R.id.business_deliverTime);
            business_rating = (TextView) itemView.findViewById(R.id.business_rating);

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
