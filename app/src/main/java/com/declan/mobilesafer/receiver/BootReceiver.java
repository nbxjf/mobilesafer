package com.declan.mobilesafer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.declan.mobilesafer.utils.ConstantValue;
import com.declan.mobilesafer.utils.SpUtils;

/**
 * Created by Administrator on 2016/6/1.
 */
public class BootReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //1 获取开机后的收集的sim号码
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String sim = tm.getSimSerialNumber();
        //2 比对
        String sim_number = SpUtils.getString(context, ConstantValue.SIM_NUMBER,"");
        if(!sim_number.equals(sim)){
            SmsManager aDefault = SmsManager.getDefault();
            String safe_phone = SpUtils.getString(context, ConstantValue.SAFE_PHONE, "");
            aDefault.sendTextMessage(safe_phone,null,"sim卡已经被换掉啦啦啦啦啦",null,null);
        }
    }
}
