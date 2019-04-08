package com.example.lin.androidkeshe.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.adapter.BusinessAdapter;
import com.example.lin.androidkeshe.entity.Business;
import com.example.lin.androidkeshe.json.CanGuan;
import com.example.lin.androidkeshe.ui.activity.BusinessShowActivity;
import com.example.lin.androidkeshe.ui.activity.GoodFoodActivity;
import com.example.lin.androidkeshe.utils.L;
import com.example.lin.androidkeshe.utils.ShareUtils;
import com.example.lin.androidkeshe.utils.StaticClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;


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
 * 描述:   外卖Fragment
 */
public class TakeOutFoodFragment extends Fragment implements View.OnClickListener {


    private static final int REQUEST_CODE_PICK_CITY = 233;
    private static final String TAG = "TakeOutFoodFragment";


    //商家展示RecyclerView
    private List<CanGuan.DataBean> businessList = new ArrayList<>();

    private TextView tof_city_text;

    private static final String KEY = "current_theme";

    private List<HotCity> hotCities;
    private int anim;
    private int theme;
    private boolean enable;

    private BusinessAdapter adapter;
    private RecyclerView recyclerView;

    private int userid;
    private String username ;

    private LinearLayout goodfood_layout;

    //轮播图
    private SliderLayout mSlider;
    private String[] imgName = {"新店特惠", "美食城", "热门餐馆", "好再来餐厅"};
    private int[] imgId = {R.drawable.business1, R.drawable.business2, R.drawable.business3, R.drawable.business4};

    private LoadingDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_takeoutfood, container, false);


        if (savedInstanceState != null) {
            theme = savedInstanceState.getInt(KEY);
            getActivity().setTheme(theme > 0 ? theme : R.style.DefaultCityPickerTheme);
        }

        initView(view);
        return view;
    }

    private void initView(View view) {

        goodfood_layout = (LinearLayout) view.findViewById(R.id.goodfood_layout);
        goodfood_layout.setOnClickListener(this);


        //初始化轮播图
        initBanner(view);

        //初始化商家
        initBusiness(view);

        //初始化地图选择器
        initCityPicker(view);
    }

    private void initCityPicker(View view) {
        tof_city_text = (TextView) view.findViewById(R.id.tof_city_text);
        tof_city_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPicker.getInstance()
                        .setFragmentManager(getActivity().getSupportFragmentManager())
                        .enableAnimation(enable)
                        .setAnimationStyle(anim)
                        .setLocatedCity(null)
                        .setHotCities(hotCities)
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                tof_city_text.setText(data.getName());
                                // tof_city_text.setText(data == null ? "杭州" : String.format("当前城市：%s，%s", data.getName(), data.getCode()));
                            }

                            @Override
                            public void onLocate() {
                                //开始定位，这里模拟一下定位
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        CityPicker.getInstance().locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                                    }
                                }, 3000);
                            }
                        })
                        .show();
            }
        });
    }


    private void initBusiness(View view) {

        mDialog = new LoadingDialog(getContext());
        mDialog.setLoadingText("加载中...")
                .setSuccessText("加载成功")
                .setFailedText("加载失败")
                .setInterceptBack(true)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO)
                .setRepeatCount(0)
                .setDrawColor(Color.WHITE)
                .show();

        //调用饿了么API接口
        initElmAPI(view);

        recyclerView = (RecyclerView) view.findViewById(R.id.tof_fragment_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

    }

    private void initElmAPI(View view) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://120.76.205.241:8000/restaurant/ele?geo=112.442990%2C38.011350&pageToken=1&apikey="
                        + StaticClass.APIKEY)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
                L.d("............................请求调用失败");
                mDialog.loadFailed();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (response.isSuccessful()) {

                    final String json = response.body().string();

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
        CanGuan canGuan = gson.fromJson(json, new TypeToken<CanGuan>() {}.getType());

        businessList = canGuan.getData();


        adapter = new BusinessAdapter(businessList);

        adapter.setOnItemClickListener(new BusinessAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                int userid = ShareUtils.getInt(getContext(),"userid",0);
                L.d("55555555555555555555555.."+userid);
                String username =ShareUtils.getString(getContext(),"username","defvalude");
                CanGuan.DataBean bean = businessList.get(position);
                Intent intent = new Intent(getContext(), BusinessShowActivity.class);
                intent.putExtra("userid",userid);
                intent.putExtra("id",bean.getId());
                intent.putExtra("title",bean.getTitle());
                intent.putExtra("rating",bean.getRating());
                L.d("4444444444444444443....."+bean.getRating());
                intent.putExtra("location",bean.getAddress());
                intent.putExtra("takeout_cost",bean.getDeliverFee()+"");
                intent.putExtra("deliverTime",bean.getDeliverTime()+"");

                startActivity(intent);

            }
        });
        recyclerView.setAdapter(adapter);
    }


    private void initBanner(View view) {
        mSlider = (SliderLayout) view.findViewById(R.id.slider);

        for (int i = 0; i < 4; i++) {
            TextSliderView textSliderView = new TextSliderView(this.getActivity());
            textSliderView.image(imgId[i]);
            textSliderView.description(imgName[i]);
            textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
            mSlider.addSlider(textSliderView);
        }

        //默认的indicator
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //自定义的indicator
        //mSliderLayout.setCustomIndicator(indicator);
        mSlider.setCustomAnimation(new DescriptionAnimation());//动画效果
        mSlider.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSlider.setDuration(3000);

        mSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSlider.stopAutoCycle();
        mDialog.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goodfood_layout:
                startActivity(new Intent(getContext(), GoodFoodActivity.class));
                break;
        }
    }
}
