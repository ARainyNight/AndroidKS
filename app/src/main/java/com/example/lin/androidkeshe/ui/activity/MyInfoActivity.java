package com.example.lin.androidkeshe.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lin.androidkeshe.R;

public class MyInfoActivity extends BaseAppCompatActivity implements View.OnClickListener{

    private EditText myInfoActivity_phone_number_et;
    private Button myInfoActivity_update_btn;
    private Button myInfoActivity_sava_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getToolbarTitle().setText("我的信息");

        initView();
        setenable(false);
    }

    private void initView() {
        myInfoActivity_phone_number_et =(EditText)findViewById(R.id.myInfoActivity_phone_number_et);
        myInfoActivity_update_btn =(Button)findViewById(R.id.myInfoActivity_update_btn);
        myInfoActivity_sava_btn =(Button)findViewById(R.id.myInfoActivity_sava_btn);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_info;
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    public void setenable(boolean is) {
        myInfoActivity_phone_number_et.setEnabled(is);
        if (is){
            myInfoActivity_sava_btn.setVisibility(View.VISIBLE);
        }else {
            myInfoActivity_sava_btn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.myInfoActivity_update_btn:
                setenable(true);
                break;
            case R.id.myInfoActivity_sava_btn:
                setenable(false);
                break;
        }
    }
}
