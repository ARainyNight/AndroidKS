package com.example.lin.androidkeshe.application;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.example.lin.androidkeshe.utils.StaticClass;



/**
 * Created by lin on 2018/4/22.
 */

public class BaseApplication extends MultiDexApplication {

    //创建
    @Override
    public void onCreate() {
        super.onCreate();

//        //初始化Bugly
//        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID,true );

    }
}
