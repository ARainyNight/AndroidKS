package com.example.lin.androidkeshe.ui.activity;


import android.content.Intent;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.adapter.SectionsPagerAdapter;
import com.example.lin.androidkeshe.entity.Business;
import com.example.lin.androidkeshe.json.AddCollectionJS;
import com.example.lin.androidkeshe.json.DeleteCollectionJS;
import com.example.lin.androidkeshe.json.QueryCollectionJS;
import com.example.lin.androidkeshe.json.QueryOneCollection;
import com.example.lin.androidkeshe.ui.fragment.BusinessShowCommentFragment;
import com.example.lin.androidkeshe.ui.fragment.BusinessShowDetailsFragment;
import com.example.lin.androidkeshe.ui.fragment.BusinessShowMenuFragment;
import com.example.lin.androidkeshe.utils.L;
import com.example.lin.androidkeshe.utils.ShareUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 餐馆信息展示界面
 */

public class BusinessShowActivity extends BaseAppCompatActivity {

    private ImageView business_imgId;
    private TextView business_name;
    private TabItem details_tabitem;
    private TabItem comment_tabitem;
    private TabItem menu_tabitem;
    private ViewPager business_show_viewpager;
    private TabLayout business_show_tab_layout;

    private Business business;


    private LikeButton likeButton;

    private int userid;
    private String id;
    private String title;
    private double rating;
    private String rating2;
    private String location;
    private String takeout_cost;
    private String deliverTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initView();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_business_show;
    }

    private void initView() {
        business_imgId = (ImageView) findViewById(R.id.business_show_business_imgId);
        details_tabitem = (TabItem) findViewById(R.id.business_show_details_tabitem);
//        comment_tabitem =(TabItem)findViewById(R.id.business_show_comment_tabitem);
        menu_tabitem = (TabItem) findViewById(R.id.business_show_menu_tabitem);
        business_show_viewpager = (ViewPager) findViewById(R.id.business_show_viewpager);
        business_show_tab_layout = (TabLayout) findViewById(R.id.business_show_tab_layout);
        likeButton = (LikeButton) findViewById(R.id.likeButton);

        getToolbarTitle().setText("餐馆信息");


        userid = getIntent().getIntExtra("userid", 0);
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        rating = getIntent().getDoubleExtra("rating", 0);
        rating2 = rating + "";
        L.d("444444444444444444....." + rating);
        location = getIntent().getStringExtra("location");
        takeout_cost = getIntent().getStringExtra("takeout_cost");
        deliverTime = getIntent().getStringExtra("deliverTime");

        //初始化likeButton
        initLike();
        //初始化Viewpager
        initViewPager();
    }

    private void initLike() {

        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        final Request request = builder.get().url("http://10.130.187.164:8080/queryOneCollection?userid=" + userid + "&businessid=" + id).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                L.d("json = " + res);
                Gson gson = new Gson();
                final QueryOneCollection queryOneCollection = gson.fromJson(res,
                        new TypeToken<QueryOneCollection>() {
                        }.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (queryOneCollection.getMsg().equals("不存在")) {
                            likeButton.setLiked(false);
                        } else {
                            likeButton.setLiked(true);
                        }

                    }
                });
            }
        });


        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.get().url("http://10.130.187.164:8080/addCollection?" +
                        "userid=" + userid + "&businessid=" + id + "&title=" + title + "&rating=" + rating2 + "&address=" + location +
                        "&deliverfee=" + takeout_cost + "&delivertime=" + deliverTime).build();

                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BusinessShowActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String res = response.body().string();
                        Gson gson = new Gson();
                        AddCollectionJS collectionJS = gson.fromJson(res, new TypeToken<AddCollectionJS>() {
                        }.getType());
                        if (collectionJS.getCode() == 504) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(BusinessShowActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(BusinessShowActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                OkHttpClient client1 = new OkHttpClient();
                Request.Builder builder1 = new Request.Builder();
                Request request1 = builder1.get().url("http://10.130.187.164:8080/deleteCollection?userid=" + userid + "&businessid=" + id).build();
                Call call1 = client1.newCall(request1);

                call1.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String res = response.body().string();
                        Gson gson = new Gson();
                        DeleteCollectionJS deleteCollectionJS = gson.fromJson(res, new TypeToken<DeleteCollectionJS>() {
                        }.getType());
                        if (deleteCollectionJS.getMsg().equals("删除收藏成功")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(BusinessShowActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(BusinessShowActivity.this, "取消收藏失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void initViewPager() {

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new BusinessShowDetailsFragment());
        fragmentList.add(new BusinessShowMenuFragment());
        fragmentList.add(new BusinessShowCommentFragment());

        business_show_viewpager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), fragmentList));
        business_show_viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(business_show_tab_layout));
        business_show_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                business_show_viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    //设置是否有返回功能
    @Override
    protected boolean isShowBacking() {
        return true;
    }
}
