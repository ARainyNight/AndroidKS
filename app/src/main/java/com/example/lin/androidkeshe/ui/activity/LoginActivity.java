package com.example.lin.androidkeshe.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lin.androidkeshe.MainActivity;
import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.json.LoginJs;
import com.example.lin.androidkeshe.utils.L;
import com.example.lin.androidkeshe.utils.ShareUtils;
import com.example.lin.androidkeshe.utils.UtilTools;
import com.example.lin.androidkeshe.view.CustomDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText loginActivity_username_et;
    private EditText loginActivity_password_et;
    private CheckBox loginActivity_rememberpsd_ck;
    private Button loginActivity_btn;
    private TextView loginActivity_forgetpsd_tv;
    private TextView loginActivity_new_user_register_tv;

    private CustomDialog dialog = null;

    private boolean isRememberpsd = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        loginActivity_btn.setOnClickListener(this);
        loginActivity_new_user_register_tv.setOnClickListener(this);
        loginActivity_forgetpsd_tv.setOnClickListener(this);
    }

    private void initView() {
        loginActivity_username_et = (EditText) findViewById(R.id.loginActivity_username_et);
        loginActivity_password_et = (EditText) findViewById(R.id.loginActivity_password_et);
        loginActivity_rememberpsd_ck = (CheckBox) findViewById(R.id.loginActivity_rememberpsd_ck);
        loginActivity_btn = (Button) findViewById(R.id.loginActivity_btn);
        loginActivity_forgetpsd_tv = (TextView) findViewById(R.id.loginActivity_forgetpsd_tv);
        loginActivity_new_user_register_tv = (TextView) findViewById(R.id.loginActivity_new_user_register_tv);

        dialog = new CustomDialog(this, 100, 100, R.layout.dialog_loding, R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);


        isRememberpsd = ShareUtils.getBoolean(this, "RememberPsd", false);
        loginActivity_rememberpsd_ck.setChecked(isRememberpsd);
        if (isRememberpsd) {
            loginActivity_username_et.setText(ShareUtils.getString(this, "name", null));
            loginActivity_password_et.setText(ShareUtils.getString(this, "psd", null));
        } else {
            loginActivity_username_et.setText("");
            loginActivity_password_et.setText("");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginActivity_btn:
                //登录
                login();
                break;
            case R.id.loginActivity_new_user_register_tv:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.loginActivity_forgetpsd_tv:
                Intent intent2 = new Intent(LoginActivity.this, ForgetPsdActivtiy.class);
                startActivity(intent2);
                break;
        }
    }

    private void login() {
        if (loginActivity_rememberpsd_ck.isChecked()) {
            ShareUtils.putString(LoginActivity.this, "login_name", loginActivity_username_et.getText().toString());
            ShareUtils.putString(LoginActivity.this, "login_psd", loginActivity_password_et.getText().toString());
            ShareUtils.putBoolean(LoginActivity.this, "isRememberpsd", loginActivity_rememberpsd_ck.isChecked());
        }
        final String name = loginActivity_username_et.getText().toString().trim();
        String psd = loginActivity_password_et.getText().toString().trim();
        if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(psd)) {
            dialog.show();
            //拿到okHttpClient对象
            OkHttpClient okHttpClient = new OkHttpClient();
            //2.构造Request
            Request.Builder builder = new Request.Builder();
            Request request =builder.get().url("http://10.130.187.164:8080/login?username="+name+"&password="+psd).build();

            //3.将Request封装为Call
            Call call= okHttpClient.newCall(request);


            L.d("LoginJS.................................");

            //4.执行call
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    L.e("onFailure"+e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    L.e("onResponse");

                  //  dialog.dismiss();
                   String res =  response.body().string();
                   Gson gson = new Gson();
                    dialog.dismiss();
                    LoginJs loginJs = gson.fromJson(res, new TypeToken<LoginJs>(){}.getType());
                    if (loginJs.getMsg().equals("登录成功")){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username",name);
                        startActivity(intent);
                        finish();
                    }else if (loginJs.getMsg().equals("密码错误")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                UtilTools.Dialog(LoginActivity.this,"密码错误");
                            }
                        });
                    }
                    L.e(res);
                }
            });
        } else {
            UtilTools.Dialog(LoginActivity.this, "输入框不能为空");
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        ShareUtils.putBoolean(this, "RememberPsd", loginActivity_rememberpsd_ck.isChecked());
        if (loginActivity_rememberpsd_ck.isChecked()) {
            ShareUtils.putString(this, "name", loginActivity_username_et.getText().toString());
            ShareUtils.putString(this, "psd", loginActivity_password_et.getText().toString());
        } else {
            ShareUtils.deleShare(this, "name");
            ShareUtils.deleShare(this, "psd");
        }

    }
}
