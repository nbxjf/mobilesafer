package com.declan.mobilesafer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.declan.mobilesafer.R;
import com.declan.mobilesafer.utils.ConstantValue;
import com.declan.mobilesafer.utils.SpUtils;
import com.declan.mobilesafer.utils.ToastUtils;
import com.declan.mobilesafer.view.SettingItemview;

/**
 * Created by Administrator on 2016/5/29.
 */
public class Setup2Activity extends BaseSetupActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        initUI();
    }

    @Override
    protected void showNextPage() {
        String simSerialNumber = SpUtils.getString(this, ConstantValue.SIM_NUMBER, "");
        if(!TextUtils.isEmpty(simSerialNumber)){
            Intent intent = new Intent(getApplicationContext(), Setup3Activity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.anim_next_in, R.anim.anim_next_out);
        }else{
            //Toast.makeText(this,"请绑定sim卡",Toast.LENGTH_SHORT).show();
            ToastUtils.show(this, "请绑定sim卡");
        }
    }

    @Override
    protected void showPrePage() {
        Intent intent = new Intent(getApplicationContext(), Setup1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.anim_pre_in,R.anim.anim_pre_out);
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        final SettingItemview siv_sim_bind = (SettingItemview) findViewById(R.id.siv_sim_bind);
        //1 回显，读取已有绑定状态
        String sim_number = SpUtils.getString(this, ConstantValue.SIM_NUMBER, "");
        //2 判断序列号是否为空
        if(TextUtils.isEmpty(sim_number)){
            siv_sim_bind.setCheck(false);
        }else{
            siv_sim_bind.setCheck(true);
        }
        siv_sim_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //3 获取原有的状态
                boolean check = siv_sim_bind.isCheck();
                //4 将原有状态取反，存储
                siv_sim_bind.setCheck(!check);
                if (!check) {
                    //5 存储
                    //获取sim卡序列号
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String simSerialNumber = telephonyManager.getSimSerialNumber();
                    SpUtils.putString(getApplicationContext(), ConstantValue.SIM_NUMBER, simSerialNumber);
                } else {
                    // 6 将存储sim卡号的节点删除
                    SpUtils.remove(getApplicationContext(), ConstantValue.SIM_NUMBER);
                }
            }
        });
    }

}
