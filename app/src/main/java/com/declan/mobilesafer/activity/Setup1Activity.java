package com.declan.mobilesafer.activity;

import android.content.Intent;
import android.os.Bundle;

import com.declan.mobilesafer.R;


/**
 * Created by Administrator on 2016/5/29.
 */
public class Setup1Activity extends BaseSetupActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }

    @Override
    protected void showNextPage() {
        Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
        startActivity(intent);
        finish();
        //开启平移动画
        overridePendingTransition(R.anim.anim_next_in,R.anim.anim_next_out);
    }

    @Override
    protected void showPrePage() {

    }
}
