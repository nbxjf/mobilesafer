package com.declan.mobilesafer.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.declan.mobilesafer.activity.SettingActivity;

/**
 * Created by Administrator on 2016/5/27.
 */
public class SpUtils {

    private static SharedPreferences sp;

    /**
     * 写方法
     * @param ctx 上下文
     * @param key 节点名称
     * @param value 值
     */
    public static void putBoolean(Context ctx,String key,boolean value){
        //（存储节点文件名称，读写方式）
        if(sp == null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();
    }

    /**
     * 读操作
     * @param ctx 上下文环境
     * @param key 节点名称
     * @param defValue 没有节点的时候的默认值
     * @return 节点值
     */
    public static boolean getBoolean(Context ctx,String key,boolean defValue){
        if(sp == null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defValue);
    }


    /**
     * 写方法
     * @param ctx 上下文
     * @param key 节点名称
     * @param value 值
     */
    public static void putString(Context ctx,String key,String value){
        //（存储节点文件名称，读写方式）
        if(sp == null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value).commit();
    }

    /**
     * 读操作
     * @param ctx 上下文环境
     * @param key 节点名称
     * @param defValue 没有节点的时候的默认值
     * @return 节点值
     */
    public static String getString(Context ctx,String key,String defValue){
        if(sp == null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key,defValue);
    }

    /**
     * 移除子节点
     * @param ctx
     * @param key
     */
    public static void remove(Context ctx,String key){
        if(sp == null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();
    }

    public static int getInt(Context ctx,String key,int defValue) {
        if(sp == null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getInt(key,defValue);
    }

    public static void putInt(Context ctx,String key,int value) {
        if(sp == null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key,value).commit();
    }
}
