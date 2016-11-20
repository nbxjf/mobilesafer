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
public class SettingItemview extends RelativeLayout {

    private CheckBox cb_box;
    private TextView tv_des;
    private String destitle;
    private String desoff;
    private String deson;

    public SettingItemview(Context context) {
        this(context,null);
    }

    public SettingItemview(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingItemview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //xml转换成view对象
        View.inflate(context, R.layout.setting_item_view, this);
        TextView tv_title = (TextView) this.findViewById(R.id.tv_title);
        tv_des = (TextView)this.findViewById(R.id.tv_des);
        cb_box = (CheckBox) this.findViewById(R.id.cb_box);
        //获取自定义属性的操作
        initAttrs(attrs);
        tv_title.setText(destitle);
    }

    private void initAttrs(AttributeSet attrs) {
        /*for(int i = 0;i < attrs.getAttributeCount();i ++){
            String name = attrs.getAttributeName(i);
            String value = attrs.getAttributeValue(i);
        }*/
        destitle = attrs.getAttributeValue(ConstantValue.NAMESPACE, "destitle");
        desoff = attrs.getAttributeValue(ConstantValue.NAMESPACE, "desoff");
        deson = attrs.getAttributeValue(ConstantValue.NAMESPACE, "deson");
    }

    //判断是否选中
    public boolean isCheck(){
        return cb_box.isChecked();
    }

    /**
     *
     * @param isCheck 是否开启
     */
    public void setCheck(boolean isCheck){
        cb_box.setChecked(isCheck);
        if(isCheck ==true){
            tv_des.setText(deson);
        }else{
            tv_des.setText(desoff);
        }
    }
}
