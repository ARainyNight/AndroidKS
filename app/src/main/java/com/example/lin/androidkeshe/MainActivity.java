package com.example.lin.androidkeshe;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lin.androidkeshe.adapter.ViewPagerAdapter;
import com.example.lin.androidkeshe.ui.fragment.MyFragment;
import com.example.lin.androidkeshe.ui.fragment.OrderFragment;
import com.example.lin.androidkeshe.ui.fragment.TakeOutFoodFragment;
import com.example.lin.androidkeshe.service.BottomNavigationViewHelper;
import com.example.lin.androidkeshe.utils.ShareUtils;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String KEY = "current_theme";

    private List<HotCity> hotCities;
    private int anim;
    private int theme;
    private boolean enable;

    private String username;

    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initView() {

        viewPager =(ViewPager)findViewById(R.id.viewpager);
        bottomNavigationView =(BottomNavigationView)findViewById(R.id.bottom_navigation);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_takeoutfood:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.item_order:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.item_my:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem !=null){
                    menuItem.setChecked(false);
                }else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem =bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new TakeOutFoodFragment());
        adapter.addFragment(new OrderFragment());
        adapter.addFragment(new MyFragment());
        viewPager.setAdapter(adapter);
    }

}
