package com.declan.mobilesafer.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.declan.mobilesafer.db.BlackNumberOpenHelper;
import com.declan.mobilesafer.db.domain.BlackNumberInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
public class BlackNumberDao {

    private final BlackNumberOpenHelper blackNumberOpenHelper;

    //单例模式
    //1 私有化构造方法
    private BlackNumberDao(Context context){
        //创建数据库以及表结构
        blackNumberOpenHelper = new BlackNumberOpenHelper(context);
    }
    //2 声明一个当前类的对象
    private static BlackNumberDao blackNumberDao = null;
    //3 提供一个静态方法，如果当前的对象为空，创建一个新的
    public static BlackNumberDao getInstance(Context context){
        if( blackNumberDao == null){
            blackNumberDao = new BlackNumberDao(context);
        }
        return blackNumberDao;
    }

    /**
     * 增
     * @param phone 号码
     * @param mode 拦截类型
     */
    public void insert(String phone,String mode){
        //开启数据库准备做写入操作
        SQLiteDatabase db = blackNumberOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone",phone);
        values.put("mode",mode);
        db.insert("blacknumber",null,values);
        db.close();
    }

    /**
     * 根据号码删除
     * @param phone 号码
     */
    public void delete(String phone){
        SQLiteDatabase db = blackNumberOpenHelper.getWritableDatabase();
        db.delete("blacknumber", "phone=?", new String[]{phone});
        db.close();
    }

    /**
     * 根据电弧号码更新拦截模式
     * @param phone 号码
     * @param mode 要更新为的模式
     */
    public void update(String phone,String mode){
        SQLiteDatabase db = blackNumberOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mode",mode);
        db.update("blacknumber",values,"phone = ?",new String[]{phone});
        db.close();
    }

    /**
     *
     * @return 查询数据库中所有的号码
     */
    public List<BlackNumberInfo> queryAll(){
        SQLiteDatabase db = blackNumberOpenHelper.getWritableDatabase();
        Cursor blacknumber = db.query("blacknumber", new String[]{"phone", "mode"}, null, null, null, null, "_id desc");
        List<BlackNumberInfo> list = new ArrayList<BlackNumberInfo>();
        while (blacknumber.moveToNext()){
            BlackNumberInfo blackNumberInfo = new BlackNumberInfo();
            blackNumberInfo.phone = blacknumber.getString(0);
            blackNumberInfo.mode = blacknumber.getString(1);
            list.add(blackNumberInfo);
        }
        blacknumber.close();
        db.close();
        return list;
    }
}
