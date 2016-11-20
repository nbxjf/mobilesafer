package com.declan.mobilesafer.receiver;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.telephony.SmsMessage;

import com.declan.mobilesafer.R;
import com.declan.mobilesafer.service.LocationService;
import com.declan.mobilesafer.utils.ConstantValue;
import com.declan.mobilesafer.utils.SpUtils;

/**
 * Created by Administrator on 2016/6/1.
 */
public class SmsReceiver extends BroadcastReceiver {
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        //1 判断是否开启了防盗保护
        boolean open_safe_security = SpUtils.getBoolean(context, ConstantValue.OPEN_SAFE_SECURITY, false);
        if(open_safe_security){
            //2 获取短信内容
            Object[] objects = (Object[]) intent.getExtras().get("pdus");
            String format = intent.getStringExtra("format");
            //3 多条短信的话，循环遍历
            for (Object object : objects){
                //4 创建短信对象
                SmsMessage sms = SmsMessage.createFromPdu((byte[])object,format);
                //5 获取短信内容
                String originatingAddress = sms.getOriginatingAddress();
                String messageBody = sms.getMessageBody();
                //6 判断是够包含播放音乐的关键字
                if(messageBody.contains("#*alarm*#")){
                    //7 播放音乐
                    //MediaPlayer player = new MediaPlayer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ylzs);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                    abortBroadcast();//拦截短信
                }
                if(messageBody.contains("#*location*#")){
                    //8 开启获取位置服务
                    context.startService(new Intent(context, LocationService.class));
                    abortBroadcast();//拦截短信
                }
                if(messageBody.contains("#*lockscreen*#")){
                    //9 锁屏

                }

                if(messageBody.contains("#*wipedata*#")){
                    //10 清楚数据

                }

            }
        }
    }
}
