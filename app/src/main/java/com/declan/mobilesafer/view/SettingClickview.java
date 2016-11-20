package com.declan.mobilesafer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.declan.mobilesafer.R;
import com.declan.mobilesafer.utils.ConstantValue;

/**
 * Created by Administrator on 2016/5/27.
 */
public class SettingClickview extends RelativeLayout {

    private TextView tv_des;
    private TextView tv_title;

    public SettingClickview(Context context) {
        this(context, null);
    }

    public SettingClickview(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingClickview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //xml转换成view对象
        View.inflate(context, R.layout.setting_click_view, this);
        tv_title = (TextView) this.findViewById(R.id.tv_title);
        tv_des = (TextView)this.findViewById(R.id.tv_des);
    }

    public void setTv_title(String text){
        tv_title.setText(text);
    }

    public void setTv_des(String text){
        tv_des.setText(text);
    }


}
