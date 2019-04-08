package com.example.lin.androidkeshe.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lin.androidkeshe.MainActivity;
import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.json.AddOrderJS;
import com.example.lin.androidkeshe.utils.ShareUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PayActivity extends AppCompatActivity{


    private String title;

    private String imageId;

    private String price;

    private String businessName;

    private int userId;

    private ImageView pay_img;
    private TextView pay_title;
    private TextView pay_price;
    private Button pay_btn ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        getIntentData();

        getUserId();



    }

    private void initView() {
        pay_btn =(Button)findViewById(R.id.pay_btn);
        pay_img=(ImageView)findViewById(R.id.pay_img);
        pay_price=(TextView)findViewById(R.id.pay_price);
        pay_title=(TextView)findViewById(R.id.pay_title);
    }

    private void getUserId() {

        userId = ShareUtils.getInt(this,"userid",0);

    }


    private void getIntentData() {

        title = getIntent().getStringExtra("title");
        imageId = getIntent().getStringExtra("imageId");
        price = getIntent().getStringExtra("price");
        businessName = getIntent().getStringExtra("businessName");

        pay_title.setText(title);
        pay_price.setText(price);
        Glide.with(this).load(imageId).into(pay_img);

        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request =builder.get()
                        .url("http://10.130.187.164:8080/addOrder?userid="+userId+"&" +
                                "commname="+title+"&commprice="+price+"&" +
                                "commbusinessname="+businessName+"&commimgid="+imageId)
                        .build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String res = response.body().string();
                        Gson gson  = new Gson();
                        AddOrderJS addOrderJS = gson.fromJson(res,new TypeToken<AddOrderJS>(){}.getType());
                        if (addOrderJS.getCode()==1003){
                            startActivity(new Intent(PayActivity.this, MainActivity.class));
                            finish();
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PayActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });


    }


}
