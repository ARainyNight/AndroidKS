package com.example.lin.androidkeshe.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.entity.Business;
import com.example.lin.androidkeshe.utils.L;

/**
 * Created by lin on 2018/6/26.
 * 描述:  商家展示界面详情页
 */
public class BusinessShowDetailsFragment extends Fragment{


    private Business mBusiness;


    private TextView details_business_name;
    private TextView details_business_deliverTime;
    private TextView details_business_rating;
    private TextView details_business_takeout_cost;
    private TextView details_business_location;

    private int userid;
    private String id;
    private String title;
    private double rating;
    private String rating2;
    private String location;
    private String takeout_cost;
    private String deliverTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.business_show_details_fragment,container,false);


        initView(view);

        return view;
    }

    private void initView(View view) {
        details_business_name =(TextView)view.findViewById(R.id.details_business_name);
        details_business_deliverTime =(TextView)view.findViewById(R.id.details_business_deliverTime);
        details_business_rating =(TextView)view.findViewById(R.id.details_business_rating);
        details_business_takeout_cost =(TextView)view.findViewById(R.id.details_business_takeout_cost);
        details_business_location =(TextView)view.findViewById(R.id.details_business_location);

        title = getActivity().getIntent().getStringExtra("title");
        rating = getActivity().getIntent().getDoubleExtra("rating", 0);
        rating2 = rating + "";
        L.d("444444444444444444....." + rating);
        location = getActivity().getIntent().getStringExtra("location");
        takeout_cost = getActivity().getIntent().getStringExtra("takeout_cost");
        deliverTime = getActivity().getIntent().getStringExtra("deliverTime");

        details_business_name.setText(title);
        details_business_rating.setText(rating2);
        details_business_location.setText(location);
        details_business_takeout_cost.setText(takeout_cost);
        details_business_deliverTime.setText(deliverTime);
    }


}
