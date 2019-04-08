package com.example.lin.androidkeshe.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.adapter.CommodityAdapter;
import com.example.lin.androidkeshe.adapter.LeftAdapter;
import com.example.lin.androidkeshe.adapter.RightAdapter;
import com.example.lin.androidkeshe.entity.Commodity;
import com.example.lin.androidkeshe.json.CanGuan;
import com.example.lin.androidkeshe.json.MenuJS;
import com.example.lin.androidkeshe.ui.activity.PayActivity;
import com.example.lin.androidkeshe.utils.L;
import com.example.lin.androidkeshe.utils.StaticClass;
import com.example.lin.androidkeshe.view.HaveHeaderListView;
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

/**
 * Created by lin on 2018/6/26.
 * 描述:商家展示界面菜单页
 */
public class BusinessShowMenuFragment extends Fragment {

    private List<MenuJS.DataBean> menuList ;

    //添加图片
    private ImageView comm_increase;
    //删除图片
    private ImageView comm_reduce;
    //菜品数量
    private TextView comm_number;

    private String business_id;

    private LoadingDialog mDialog;

    private RecyclerView recyclerView;

    private CommodityAdapter adapter ;

    private String businessName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.business_show_menu_fragment, container, false);

        business_id = getActivity().getIntent().getStringExtra("id");
        L.d("BusinessShowMenuFragment" + "...." + business_id);
        initView(view);

        businessName = getActivity().getIntent().getStringExtra("title");

        return view;
    }

    private void initView(View view) {

        initMenu(view);


    }

    private void initMenu(View view) {

        mDialog = new LoadingDialog(getContext());
        mDialog.setLoadingText("加载中...")
                .setSuccessText("加载成功")
                .setFailedText("加载失败")
                .setInterceptBack(true)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO)
                .setRepeatCount(0)
                .setDrawColor(Color.WHITE)
                .show();

        initElmAPI(view);

        recyclerView = (RecyclerView) view.findViewById(R.id.menu_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initElmAPI(View view) {

        if (menuList==null){
            menuList = new ArrayList<>();
        }

        Log.d("test", "initElmAPI: " +business_id);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://120.76.205.241:8000/product/ele?id="+business_id+"&apikey="+StaticClass.APIKEY)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mDialog.loadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String json = response.body().string();
                    Log.d("test", "jsonToEntity: "+json);
                    getActivity().runOnUiThread(new Runnable() {
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
            MenuJS menuJS = gson.fromJson(json,new TypeToken<MenuJS>(){}.getType());
            menuList = menuJS.getData();
            adapter=new CommodityAdapter(menuList);

            adapter.setOnItemClickListener(new CommodityAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int position) {

                    MenuJS.DataBean bean = menuList.get(position);

                    Intent intent = new Intent(getContext(), PayActivity.class);
                    intent.putExtra("title",bean.getTitle());
                    intent.putExtra("imageId",bean.getImageUrls().get(0));
                    intent.putExtra("price",String.valueOf(bean.getSkuOptions().get(0).getPrice()));
                    intent.putExtra("businessName",businessName);
                    startActivity(intent);

                }
            });

            recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDialog.close();
    }
}
