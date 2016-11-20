package com.declan.mobilesafer.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Administrator on 2016/6/2.
 */
public class AddressDao {
    //1 指定路径
    public static String path = "data/data/com.declan.mobilesafer/files/address.db";
    private static String mAddress;
    private static SQLiteDatabase db;

    /**
     *2 开启数据库，进行访问
     * @param phone 查询电话号码
     */
    public static String getAddress(String phone){
        mAddress = "未知号码";
        //正则表达式。匹配号码
        String regularExpresion = "^1[3-8]\\d{9}$";
        if(phone.matches(regularExpresion)){
            phone = phone.substring(0, 7);
            //打开数据库
            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
            //查询数据库
            Cursor cursor = db.query("data1", new String[]{"outkey"}, "id = ?", new String[]{phone}, null, null, null);
            //查到即可
            if(cursor.moveToNext()){
                String outkey = cursor.getString(0);
                //通过outkey查询data2表
                Cursor indexCursor = db.query("data2", new String[]{"location"}, "id = ?", new String[]{outkey}, null, null, null);
                if(indexCursor.moveToNext()){
                    mAddress = indexCursor.getString(0);
                }
            }else{
                mAddress = "未知号码";
            }
        }else{
            int length = phone.length();
            switch (length){
                case 3:
                    mAddress = "报警电话";
                    break;
                case 4:
                    break;
                case 5:
                    mAddress="服务电话";
                    break;
                case 7:
                case 8:
                    mAddress="本地电话";
                    break;
                case 11:
                    //3+8 区号加外地座机
                    String area = phone.substring(1, 3);
                    Cursor cursor = db.query("data2", new String[]{"location"}, "area = ?", new String[]{area}, null, null, null);
                    if(cursor.moveToNext()){
                        mAddress = cursor.getString(0);
                    }else {
                        mAddress = "未知号码";
                    }
                    break;
                case 12:
                    //4+8 区号加外地座机
                    String area1 = phone.substring(1, 4);
                    Cursor cursor1 = db.query("data2", new String[]{"location"}, "area = ?", new String[]{area1}, null, null, null);
                    if(cursor1.moveToNext()){
                        mAddress = cursor1.getString(0);
                    }else {
                        mAddress = "未知号码";
                    }
                    break;
            }
        }
        return  mAddress;
    }
}
