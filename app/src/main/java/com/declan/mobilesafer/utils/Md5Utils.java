package com.declan.mobilesafer.utils;

import android.content.Intent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/5/28.
 */
public class Md5Utils {

    /**
     * md5加密函数
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String md5(String str){
        try {
            //指定加密算法类型
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            //将要加密的字符串转换成byte数组
            byte[] bytes = md5.digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes){
                int i = b & 0xff;
                //将int类型的i转换成16进制字符
                String hexString = Integer.toHexString(i);
                if(hexString.length() < 2){
                    hexString = "0"+hexString;
                }
                //将字符串拼接成32位
                stringBuffer.append(hexString);
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
