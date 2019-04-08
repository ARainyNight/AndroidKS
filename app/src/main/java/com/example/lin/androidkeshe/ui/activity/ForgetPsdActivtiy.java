package com.example.lin.androidkeshe.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.entity.MyUser;



public class ForgetPsdActivtiy extends BaseActivity implements View.OnClickListener {

    private EditText forgetPsdActivity_email_et;
    private Button forgetPsdActivity_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psd_activtiy);

        initView();
    }

    private void initView() {
        forgetPsdActivity_email_et = (EditText) findViewById(R.id.forgetPsdActivity_email_et);
        forgetPsdActivity_btn = (Button) findViewById(R.id.forgetPsdActivity_btn);

        forgetPsdActivity_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forgetPsdActivity_btn:
                final String email = forgetPsdActivity_email_et.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {
//                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
//                        @Override
//                        public void done(BmobException e) {
//                            if (e == null) {
//                                Toast.makeText(ForgetPsdActivtiy.this, "重置密码请求成功，请到" + email + "重置密码", Toast.LENGTH_SHORT).show();
//                                finish();
//                            } else {
//                                Toast.makeText(ForgetPsdActivtiy.this, "重置密码失败" + email, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                } else {
                    Toast.makeText(ForgetPsdActivtiy.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
