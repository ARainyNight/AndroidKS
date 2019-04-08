package com.example.lin.androidkeshe.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.adapter.OrderAdapter;
import com.example.lin.androidkeshe.entity.Order;
import com.example.lin.androidkeshe.json.QueryCollectionJS;
import com.example.lin.androidkeshe.json.QueryOrderJS;
import com.example.lin.androidkeshe.utils.ShareUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lin on 2018/6/21.
 * 描述:
 */
public class OrderFragment extends Fragment {

    private List<QueryOrderJS> orderList = new ArrayList<>();

    private int userid ;

    private OrderAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        userid  = ShareUtils.getInt(getContext(),"userid",0);

        recyclerView =(RecyclerView)view.findViewById(R.id.order_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        initMyOrder(view);
        return view;
    }

    private void initMyOrder(View view) {
        OkHttpClient client =new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.130.187.164:8080/queryOrder?userid="+userid)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    final String json = response.body().string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            jsonToEntity(json);
                        }
                    });
                }
            }
        });
    }

    private void jsonToEntity(String json) {
        Gson gson = new Gson();
        orderList =gson.fromJson(json,new TypeToken<List<QueryOrderJS>>(){}.getType());

        adapter = new OrderAdapter(orderList);
        recyclerView.setAdapter(adapter);

    }

}
