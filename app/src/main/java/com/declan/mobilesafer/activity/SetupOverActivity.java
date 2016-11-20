package com.declan.mobilesafer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.declan.mobilesafer.R;
import com.declan.mobilesafer.utils.ConstantValue;
import com.declan.mobilesafer.utils.SpUtils;


/**
 * Created by Administrator on 2016/5/29.
 */
public class SetupOverActivity extends AppCompatActivity{

    private TextView tv_reset_setup;
    private ImageView iv_lock;
    private TextView tv_phone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean setup_over = SpUtils.getBoolean(this, ConstantValue.SETUP_OVER, false);
        if(setup_over){
            //设置密码成功，并且四个导航页面完成，跳转到功能列表页面
            setContentView(R.layout.activity_setup_over);
            initUI();
        }else{
            //四个导航页面未完成，跳转到第一个导航页面
            Intent intent = new Intent(this, Setup1Activity.class);
            startActivity(intent);

            //跳到第一个导航界面后，将当前的setup_over界面finish
            finish();
        }

    }

    private void initUI() {
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        String string = SpUtils.getString(this, ConstantValue.SAFE_PHONE, "");
        tv_phone.setText(string);
        iv_lock = (ImageView) findViewById(R.id.iv_lock);
        tv_reset_setup = (TextView) findViewById(R.id.tv_reset_setup);
        tv_reset_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Setup1Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
