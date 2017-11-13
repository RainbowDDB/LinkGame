package com.example.zyt.link;

/*
* 活动管理器类
*/

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector{

    private static final String TAG = "ActivityCollector";

    public static List<Activity> activities = new ArrayList<>();

    //获取当前活动(第0个活动为StartActivity，第1个活动为linkgame)
    public static Activity getActivity(int i){
        Activity activity = activities.get(i);
        return activity;
    }

    //添加活动
    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    //移去活动
    public static void removeActivity(Activity activity){
       activities.remove(activity);
   }

    //清理全部活动
    public static void finishAll(){
        for(Activity activity : activities){
            if(!activity.isFinishing()){
                 activity.finish();
            }
        }
        activities.clear();
    }
}
