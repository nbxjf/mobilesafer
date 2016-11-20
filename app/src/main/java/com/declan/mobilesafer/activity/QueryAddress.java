package com.declan.mobilesafer.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.declan.mobilesafer.R;
import com.declan.mobilesafer.engine.AddressDao;

/**
 * Created by Administrator on 2016/6/2.
 */
public class QueryAddress  extends AppCompatActivity{

    private EditText et_phone;
    private Button bt_query;
    private TextView tv_query_result;
    public String mAddress;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            tv_query_result.setText(mAddress);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_address);
        initUI();
    }

    private void initUI() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        bt_query = (Button) findViewById(R.id.bt_query);
        tv_query_result = (TextView) findViewById(R.id.tv_query_result);
        bt_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = et_phone.getText().toString();
                if(!TextUtils.isEmpty(phone)){
                    query(phone);
                }else{
                    //为空时的抖动动画
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
                    et_phone.startAnimation(shake);
                    //手机震动效果实现
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    //震动的毫米值
                    vibrator.vibrate(500);
                    //规律震动（震动规则（不震动时间，震动时间。。。。。），重复次数）
                    //vibrator.vibrate(new long[]{1000,1000},2);
                }
            }
        });
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = et_phone.getText().toString();
                query(phone);
            }
        });
    }

    /**
     * 耗时操作
     * 获取电话归属地
     * @param phone
     */
    private void query(final String phone) {
        new Thread(){
            @Override
            public void run() {
                mAddress = AddressDao.getAddress(phone);
                //消息机制
                handler.sendEmptyMessage(0);
            }
        }.start();
    }
}
