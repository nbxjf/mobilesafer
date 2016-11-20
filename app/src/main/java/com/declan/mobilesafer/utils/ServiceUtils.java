package com.declan.mobilesafer.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2016/6/4.
 */
public class ServiceUtils {
    /**
     * 判断服务是否运行
     * @param ctx 上下文
     * @param servicename 服务名称
     * @return true  false
     */
    public static boolean isRunning(Context ctx,String servicename){
        //1 获取activityManager对象，获取当前手机正在运行的所有服务
        ActivityManager mManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        //2 获取手机中正在运行的服务(number 多少个服务)
        List<ActivityManager.RunningServiceInfo> runningServices = mManager.getRunningServices(1000);
        //3 遍历所有的服务集合
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices){
            if(servicename.equals(runningServiceInfo.service.getClassName())){
                return true;
            }
        }
        return false;
    }
}
