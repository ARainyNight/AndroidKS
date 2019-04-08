package com.example.lin.androidkeshe.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.entity.MyUser;
import com.example.lin.androidkeshe.utils.ShareUtils;


/**
 * 设置界面
 *
 * */
public class MySettingActivity extends BaseAppCompatActivity implements View.OnClickListener{



    private Button mySettingActivity_exit_login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getToolbarTitle().setText("设置");

        initView();
    }

    private void initView() {
    //    mySettingActivity_account_number_btn = (Button)findViewById(R.id.mySettingActivity_account_number_btn);
        mySettingActivity_exit_login_btn =(Button)findViewById(R.id.mySettingActivity_exit_login_btn);

      //  mySettingActivity_account_number_btn.setOnClickListener(this);
        mySettingActivity_exit_login_btn.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_setting;
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.mySettingActivity_account_number_btn:
//                startActivity(new Intent(MySettingActivity.this,MyInfoActivity.class));
//                //账号设置
//                break;
            case R.id.mySettingActivity_exit_login_btn:
                //退出登录
//                MyUser.logOut();
//                MyUser currentUser = (MyUser) MyUser.getCurrentUser();
                startActivity(new Intent(MySettingActivity.this,LoginActivity.class));
                finish();
                break;
        }
    }

}
