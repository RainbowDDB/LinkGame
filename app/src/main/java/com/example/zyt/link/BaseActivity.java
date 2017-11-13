package com.example.zyt.link;

/*
 * 全局调用方法
 */

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class BaseActivity extends AppCompatActivity{
    private static final String TAG = "BaseActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示当前活动名
        Log.d(TAG,getClass().getSimpleName());
        //将活动添加到活动管理器中
        ActivityCollector.addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //将活动从任务管理器中移除
        ActivityCollector.removeActivity(this);
    }

}
