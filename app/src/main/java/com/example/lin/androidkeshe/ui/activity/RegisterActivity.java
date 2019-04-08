package com.example.lin.androidkeshe.ui.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.entity.MyUser;
import com.example.lin.androidkeshe.json.LoginJs;
import com.example.lin.androidkeshe.json.RegisterJS;
import com.example.lin.androidkeshe.utils.UtilTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText registerActivity_username_et;
    private EditText registerActivity_password_et;
    private EditText registerActivity_password_again_et;
    private RadioGroup register_rg;
    private RadioButton registerActivity_man_rb;
    private RadioButton registerActivity_women_rb;
    private EditText registerActivity_desc_et;
    private EditText registerActivity_email_et;
    private Button registerActivity_btn;
    private EditText registerActivity_PhoneNumber;


    //性别
    private boolean isGender = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        registerActivity_btn.setOnClickListener(this);
    }

    private void initView() {
        registerActivity_username_et = (EditText) findViewById(R.id.registerActivity_username_et);
        registerActivity_password_et = (EditText) findViewById(R.id.registerActivity_password_et);
        registerActivity_password_again_et = (EditText) findViewById(R.id.registerActivity_password_again_et);
//        register_rg = (RadioGroup) findViewById(R.id.register_rg);
//        registerActivity_man_rb = (RadioButton) findViewById(R.id.registerActivity_man_rb);
//        registerActivity_women_rb = (RadioButton) findViewById(R.id.registerActivity_women_rb);
//        registerActivity_desc_et = (EditText) findViewById(R.id.registerActivity_desc_et);
      //  registerActivity_email_et = (EditText) findViewById(R.id.registerActivity_email_et);
        registerActivity_btn = (Button) findViewById(R.id.registerActivity_btn);
        registerActivity_PhoneNumber = (EditText) findViewById(R.id.registerActivity_PhoneNumber);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerActivity_btn:
                register();
                break;
        }
    }

    private void register() {

        String name = registerActivity_username_et.getText().toString().trim();
        String psd = registerActivity_password_et.getText().toString().trim();
        String psd_again = registerActivity_password_again_et.getText().toString().trim();
//        String desc = registerActivity_desc_et.getText().toString().trim();
//        String mail = registerActivity_email_et.getText().toString().trim();
        String phone = registerActivity_PhoneNumber.getText().toString().trim();

        if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(psd) & !TextUtils.isEmpty(psd_again) &  !TextUtils.isEmpty(phone)) {

            //判断两次输入的密码是否一致
            if (psd.equals(psd_again)) {
//                //判断性别
//                register_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                        if (checkedId == R.id.registerActivity_man_rb) {
//                            isGender = true;
//                        } else if (checkedId == R.id.registerActivity_women_rb) {
//                            isGender = false;
//                        }
//                    }
//                });

                //判断简介是否为空
//                if (TextUtils.isEmpty(desc)) {
//                    desc = "这个人很懒，什么都没有留下！";
//                }

//                //注册
//                MyUser user = new MyUser();
//                user.setUsername(name);
//                user.setPassword(psd);
//                user.setDesc(desc);
//                user.setEmail(mail);
//                user.setMobilePhoneNumber(phone);
//                user.setSex(isGender);
//
//                user.signUp(new SaveListener<MyUser>() {
//                    @Override
//                    public void done(MyUser myUser, BmobException e) {
//                        if (e == null) {
//                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT);
//                            finish();
//                        } else {
//                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT);
//                        }
//                    }
//                });
                OkHttpClient httpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.get().url("http://10.130.187.164:8080/register?" +
                        "username="+name+"&password="+psd+"&phoneNumber="+phone).build();
                Call call = httpClient.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                            String res = response.body().string();
                            Gson gson = new Gson();
                        RegisterJS registerJS = gson.fromJson(res,new TypeToken<RegisterJS>(){}.getType());
                        if (registerJS.getCode() ==501){
                            //注册成功
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            finish();
                        }else if (registerJS.getCode() == 402){
                            //用户存在
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    UtilTools.Dialog(RegisterActivity.this,"用户存在");
                                }
                            });
                        }else {
                            //注册失败
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    UtilTools.Dialog(RegisterActivity.this,"注册失败");
                                }
                            });
                        }
                    }
                });
            } else {
                UtilTools.Dialog(this, "两次输入的密码不一致");
            }
        } else {
            UtilTools.Dialog(this, "输入框不能为空");
        }
    }
}
