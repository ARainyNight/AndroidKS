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
import com.example.lin.androidkeshe.adapter.BusinessAdapter;
import com.example.lin.androidkeshe.adapter.CommentAdapter;
import com.example.lin.androidkeshe.entity.Comment;
import com.example.lin.androidkeshe.json.CanGuan;
import com.example.lin.androidkeshe.json.PingLun;
import com.example.lin.androidkeshe.utils.L;
import com.example.lin.androidkeshe.utils.StaticClass;
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
 * Created by lin on 2018/6/26.
 * 描述:  商家展示界面评论页
 */
public class BusinessShowCommentFragment extends Fragment{

    private List<PingLun.DataBean> commentList = new ArrayList<>();

    private CommentAdapter adapter ;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.business_show_comment_fragment,container,false);


        initrecyclerview(view);

        return view;
    }

    private void initElmAPI(View view) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://120.76.205.241:8000/comment/ele?" +
                        "id=1334126&pageToken=1&apikey="+ StaticClass.APIKEY)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.d(".............调用评论API失败");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String json = response.body().string();

                                jsonToEntity(json);
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    });
            }
        });
    }

    private void jsonToEntity(String json) {

        Gson gson = new Gson();
        PingLun pingLun = gson.fromJson(json, new TypeToken<PingLun>(){}.getType());

        commentList = pingLun.getData();

        adapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(adapter);
    }


    private void initrecyclerview(View view) {
       // initElmAPI(view);

         recyclerView  = (RecyclerView)view.findViewById(R.id.comment_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

    }


}
