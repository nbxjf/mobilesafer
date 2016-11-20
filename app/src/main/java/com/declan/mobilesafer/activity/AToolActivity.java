package com.declan.mobilesafer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.declan.mobilesafer.R;


/**
 * Created by Administrator on 2016/6/2.
 */
public class AToolActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atool);
    }

    public void tv_query_address(View v){
        startActivity(new Intent(getApplicationContext(),QueryAddress.class));
    }
}
