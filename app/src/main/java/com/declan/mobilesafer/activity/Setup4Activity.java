package com.declan.mobilesafer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.declan.mobilesafer.R;
import com.declan.mobilesafer.utils.ConstantValue;
import com.declan.mobilesafer.utils.SpUtils;
import com.declan.mobilesafer.utils.ToastUtils;

/**
 * Created by Administrator on 2016/5/29.
 */
public class Setup4Activity extends BaseSetupActivity{

    private CheckBox cb_box;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        initUI();
    }

    @Override
    protected void showNextPage() {
        boolean open_safe_security = SpUtils.getBoolean(this, ConstantValue.OPEN_SAFE_SECURITY, false);
        if(open_safe_security){
            Intent intent = new Intent(getApplicationContext(), SetupOverActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.anim_next_in, R.anim.anim_next_out);
            SpUtils.putBoolean(this, ConstantValue.SETUP_OVER,true);
        }else{
            ToastUtils.show(this,"请选择开启安全设置");
        }
    }

    @Override
    protected void showPrePage() {
        Intent intent = new Intent(getApplicationContext(), Setup3Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.anim_pre_in, R.anim.anim_pre_out);
    }

    /**
     * 初始化
     */
    private void initUI() {
        cb_box = (CheckBox) findViewById(R.id.cb_box);
        //1 是否选中状态的回显过程
        boolean open_safe_security = SpUtils.getBoolean(this, ConstantValue.OPEN_SAFE_SECURITY, false);
        //2 更具状态修改checkbox的文字显示
        if(open_safe_security){
            cb_box.setText("安全设置已开启");
        }else {
            cb_box.setText("安全设置已关闭");
        }
        cb_box.setChecked(open_safe_security);
        cb_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_box.setChecked(isChecked);
                SpUtils.putBoolean(getApplicationContext(), ConstantValue.OPEN_SAFE_SECURITY, cb_box.isChecked());
                if (isChecked) {
                    cb_box.setText("安全设置已开启");
                } else {
                    cb_box.setText("安全设置已关闭");
                }
            }
        });
    }
}
