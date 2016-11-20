package com.declan.mobilesafer.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/5/26.
 */
public class ToastUtils {
    public static void show(Context ctx, String msg) {
        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
    }
}
