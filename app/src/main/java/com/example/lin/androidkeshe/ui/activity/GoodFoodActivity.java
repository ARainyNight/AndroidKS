package com.example.lin.androidkeshe.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.adapter.GoodFoodAdapter;
import com.example.lin.androidkeshe.entity.GoodFood;

import java.util.ArrayList;
import java.util.List;

public class GoodFoodActivity extends BaseAppCompatActivity {

    private List<GoodFood> goodFoodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getToolbarTitle().setText("1");
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_good_food;
    }

    private void initView() {

        initGoodFood();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.goodfood_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        GoodFoodAdapter adapter  = new GoodFoodAdapter(goodFoodList);
        recyclerView.setAdapter(adapter);
    }

    private void initGoodFood() {

        for (int i = 0; i < 4; i++) {
            GoodFood goodFood1 = new GoodFood(R.mipmap.ic_launcher, "蛋仔卤肉饭", "酷卡", 10);
            GoodFood goodFood2 = new GoodFood(R.mipmap.ic_launcher, "鸡公煲", "龙山鸡公煲", 10);
            GoodFood goodFood3 = new GoodFood(R.mipmap.ic_launcher, "黄焖鸡", "二道门黄焖鸡", 10);
            GoodFood goodFood4 = new GoodFood(R.mipmap.ic_launcher, "冰糖雪梨", "七杯茶", 10);

            goodFoodList.add(goodFood1);
            goodFoodList.add(goodFood2);
            goodFoodList.add(goodFood3);
            goodFoodList.add(goodFood4);
        }
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }
}
