package com.example.lin.androidkeshe.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lin.androidkeshe.R;

public class MyLocationActivity extends  BaseAppCompatActivity implements View.OnClickListener{

    private EditText mylocation_et;
    private Button mylocation_update_btn;
    private Button mylocation_save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        setEnable(false);
        getToolbarTitle().setText("收货地址");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_location;


    }

    //控制焦点
    private void setEnable(boolean is) {
        mylocation_et.setEnabled(is);
        if (is) {
            //可见
            mylocation_save_btn.setVisibility(View.VISIBLE);
        } else {
            //不可见
            mylocation_save_btn.setVisibility(View.INVISIBLE);

        }
    }

    private void initView() {
        mylocation_et = (EditText) findViewById(R.id.mylocation_et);
        mylocation_update_btn = (Button) findViewById(R.id.mylocation_update_btn);
        mylocation_save_btn = (Button) findViewById(R.id.mylocation_save_btn);

        mylocation_update_btn.setOnClickListener(this);
        mylocation_save_btn.setOnClickListener(this);
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mylocation_update_btn:
                setEnable(true);

                break;
            case R.id.mylocation_save_btn:
                setEnable(false);
                break;
        }
    }
}
