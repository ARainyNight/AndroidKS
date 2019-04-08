package com.example.lin.androidkeshe.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.adapter.BusinessAdapter;
import com.example.lin.androidkeshe.adapter.MyCollectionAdapter;
import com.example.lin.androidkeshe.json.CanGuan;
import com.example.lin.androidkeshe.json.QueryCollectionJS;
import com.example.lin.androidkeshe.utils.L;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyCollectionActivity extends BaseAppCompatActivity {

    private RecyclerView myCollectionrecycler_view;

    private LoadingDialog mDialog ;
    private int userid;

    private List<QueryCollectionJS> businessList = new ArrayList<>();

    private MyCollectionAdapter adapter ;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getToolbarTitle().setText("我的收藏");

        userid = getIntent().getIntExtra("userid",0);

        //初始化收藏
        initCollection();

        recyclerView= (RecyclerView)findViewById(R.id.mycollection_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initCollection() {
        mDialog = new LoadingDialog(this);
        mDialog.setLoadingText("加载中...")
                .setSuccessText("加载成功")
                .setFailedText("加载失败")
                .setInterceptBack(true)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO)
                .setRepeatCount(0)
                .setDrawColor(Color.WHITE)
                .show();

        //查询本地数据库我的收藏表
        initMyCollection();

    }

    private void initMyCollection() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.130.187.164:8080/queryCollection?userid="+userid)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.loadFailed();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    final String json = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            jsonToEntity(json);
                            mDialog.loadSuccess();
                        }
                    });
                }
            }
        });
    }

    private void jsonToEntity(String json) {

        Gson gson = new Gson();
        businessList =gson.fromJson(json,new TypeToken<List<QueryCollectionJS>>(){}.getType());

        adapter = new MyCollectionAdapter(businessList);

        adapter.setOnItemClickListener(new BusinessAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                QueryCollectionJS queryCollectionJS = businessList.get(position);
                Intent intent =new Intent(MyCollectionActivity.this,BusinessShowActivity.class);
                intent.putExtra("userid",queryCollectionJS.getUserid());
                intent.putExtra("id",queryCollectionJS.getBusinessid());
                intent.putExtra("title",queryCollectionJS.getTitle());
                intent.putExtra("rating",queryCollectionJS.getRating());
                intent.putExtra("location",queryCollectionJS.getAddress());
                intent.putExtra("takeout_cost",queryCollectionJS.getDeliverfee());
                intent.putExtra("deliverTime",queryCollectionJS.getDelivertime());

                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected boolean isShowBacking() {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDialog.close();
    }
}
