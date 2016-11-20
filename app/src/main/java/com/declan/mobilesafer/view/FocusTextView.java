package com.declan.mobilesafer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/5/27.
 */
public class FocusTextView  extends TextView{
    //使用java创建控件
    public FocusTextView(Context context) {
        super(context);
    }

    // 由系统调用（上下文环境+属性集）
    public FocusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //由系统调用（上下文环境+属性集+布局文件中的定义的样式文件构造方法）
    public FocusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //重写获取焦点的方法，返回true，默认获取焦点
    @Override
    public boolean isFocused() {
        return true;
    }
}
