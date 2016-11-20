package com.declan.mobilesafer.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.declan.mobilesafer.R;
import com.declan.mobilesafer.utils.ConstantValue;
import com.declan.mobilesafer.utils.SpUtils;

public class AddressService extends Service {

    private TelephonyManager telephonyManager;
    private MyPhoneStateListener myPhoneStateListener;
    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private View mViewToast;
    private WindowManager mWM;
    private TextView textView;

    public AddressService() {
    }

    @Override
    public void onCreate() {
        //开启吐司，显示吐司的提示
        //1 获取电话管理者
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //2 监听电话的状态
        myPhoneStateListener = new MyPhoneStateListener();
        telephonyManager.listen(myPhoneStateListener,PhoneStateListener.LISTEN_CALL_STATE); 

        //获取窗体对象
        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);
        super.onCreate();
    }

    class MyPhoneStateListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state){
                //空闲状态
                case TelephonyManager.CALL_STATE_IDLE:
                    if(mWM != null && mViewToast != null){
                        mWM.removeView(mViewToast);
                    }
                    break;
                //响铃状态
                case TelephonyManager.CALL_STATE_RINGING:
                    showToast();
                    break;
                //摘机状态
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    /**
     * 打印吐司
     */
    private void showToast() {
        //Toast.makeText(getApplicationContext(),"xxx",Toast.LENGTH_SHORT).show();

        final WindowManager.LayoutParams params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.setTitle("Toast");
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE; //默认能够被触摸
        //默认显示位置
        params.gravity = Gravity.LEFT + Gravity.TOP;
        //params.x是窗体x轴的坐标
        params.x = SpUtils.getInt(getApplicationContext(),ConstantValue.LOCATION_X,0);
        params.y = SpUtils.getInt(getApplicationContext(),ConstantValue.LOCATION_Y,0);
        //吐司的显示效果
        mViewToast = View.inflate(getApplicationContext(), R.layout.toast_view, null);
        textView = (TextView) mViewToast.findViewById(R.id.tv_taost);

        //从sp中获取索引值
        int color = SpUtils.getInt(getApplicationContext(), ConstantValue.TOAST_STYLE,0);
        switch (color){
            case 0:
                break;
            case 1:
                //mToastStyle = new String[]{"透明", "橙色", "蓝色", "灰色", "绿色"};
                textView.setBackgroundColor(Color.parseColor("#E9967A"));
                break;
            case 2:
                textView.setBackgroundColor(Color.parseColor("#0000FF"));
                break;
            case 3:
                textView.setBackgroundColor(Color.parseColor("#666666"));
                break;
            case 4:
                textView.setBackgroundColor(Color.parseColor("#00FF00"));
                break;
        }
        mWM.addView(mViewToast,params);
    }

    @Override
    public void onDestroy() {
        //取消对电话状态的监听
        if(telephonyManager != null && myPhoneStateListener != null){
            telephonyManager.listen(myPhoneStateListener,PhoneStateListener.LISTEN_NONE);
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
