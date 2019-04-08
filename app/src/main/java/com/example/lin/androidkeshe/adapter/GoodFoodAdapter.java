package com.example.lin.androidkeshe.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.entity.GoodFood;

import java.util.List;

/**
 * Created by lin on 2018/7/3.
 * 描述:
 */
public class GoodFoodAdapter extends RecyclerView.Adapter<GoodFoodAdapter.ViewHolder>{

    private List<GoodFood>  goodFoodList;

    public GoodFoodAdapter(List<GoodFood> goodFoods){
        this.goodFoodList = goodFoods;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goodfood_item,parent,false);
        ViewHolder holder =new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GoodFood goodFood = goodFoodList.get(position);
        holder.food_imgId.setImageResource(goodFood.getFood_imgId());
        holder.food_name.setText(goodFood.getFood_name());
        holder.food_business.setText(goodFood.getFood_business());
        holder.food_price.setText(goodFood.getFood_price()+"");
    }

    @Override
    public int getItemCount() {
        return goodFoodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView food_imgId ;
        TextView food_name ;
        TextView food_business;
        TextView food_price ;

        public ViewHolder(View itemView) {
            super(itemView);

            food_imgId =(ImageView)itemView.findViewById(R.id.goodfood_imgId);
            food_name=(TextView)itemView.findViewById(R.id.food_name);
            food_business=(TextView)itemView.findViewById(R.id.food_business);
            food_price=(TextView)itemView.findViewById(R.id.food_price);
        }
    }
}
