package com.declan.mobilesafer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.declan.mobilesafer.R;
import com.declan.mobilesafer.service.AddressService;
import com.declan.mobilesafer.utils.ConstantValue;
import com.declan.mobilesafer.utils.ServiceUtils;
import com.declan.mobilesafer.utils.SpUtils;
import com.declan.mobilesafer.view.SettingClickview;
import com.declan.mobilesafer.view.SettingItemview;


/**
 * Created by Administrator on 2016/5/27.
 */
public class SettingActivity extends AppCompatActivity{

    private String[] mToastStyle;
    private int mToast_style;
    private SettingClickview scv_toast_style;
    private SettingClickview scv_toast_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUpdate();
        initAddress();
        initToastStyle();
        initToastLocation();
    }

    private void initToastLocation() {
        scv_toast_location = (SettingClickview) findViewById(R.id.scv_toast_location);
        scv_toast_location.setTv_title("归属地显示位置");
        scv_toast_location.setTv_des("设置归属地提示框显示位置");
        scv_toast_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ToastLocationActivity.class));
            }
        });

    }

    private void initToastStyle() {
        scv_toast_style = (SettingClickview) findViewById(R.id.scv_toast);
        scv_toast_style.setTv_title("设置归属地显示样式");
        mToastStyle = new String[]{"透明", "橙色", "蓝色", "灰色", "绿色"};
        mToast_style = SpUtils.getInt(this, ConstantValue.TOAST_STYLE, 0);
        scv_toast_style.setTv_des(mToastStyle[mToast_style]);
        //监听点击事件，弹出对话框
        scv_toast_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastDialog();
            }
        });
    }

    /**
     * 吐司显示样式选择的对话框
     */
    private void showToastDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("请选择样式");
        builder.setSingleChoiceItems(mToastStyle, SpUtils.getInt(this, ConstantValue.TOAST_STYLE, 0), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SpUtils.putInt(getApplication(), ConstantValue.TOAST_STYLE, which);
                dialog.dismiss();
                scv_toast_style.setTv_des(mToastStyle[which]);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 是否显示电话归属地
     */
    private void initAddress() {
        final SettingItemview siv_address = (SettingItemview) findViewById(R.id.siv_address);
        boolean running = ServiceUtils.isRunning(this, "com.declan.mobilesafer.service.AddressService");
        siv_address.setCheck(running);
        siv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = siv_address.isCheck();
                siv_address.setCheck(!check);
                //如果选中需要去开启服务，否则关闭
                if(!check){
                    //开启
                    startService(new Intent(getApplicationContext(),AddressService.class));
                }else{
                    //关闭
                    stopService(new Intent(getApplicationContext(), AddressService.class));
                }
            }
        });
    }

    /**
     * 是否检测版本更新
     */
    private void initUpdate() {
        final SettingItemview siv_update = (SettingItemview) findViewById(R.id.siv_update);

        //获取已有的开关状态，用作显示
        boolean open_update = SpUtils.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
        //是否选中，然后根据上一次的存储结果作决定
        siv_update.setCheck(open_update);
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = siv_update.isCheck();
                siv_update.setCheck(!check);
                //将取反后的结果存储到sp中
                SpUtils.putBoolean(getApplicationContext(),ConstantValue.OPEN_UPDATE,!check);
            }
        });
    }
}
