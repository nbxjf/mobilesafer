package com.declan.mobilesafer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/5/30.
 */
public abstract class BaseSetupActivity extends AppCompatActivity{
    private GestureDetector gestureDetector;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if(e1.getX() - e2.getX() > 0){
                    //移动到下一页,调用子类的下一页方法
                    showNextPage();
                }else if(e1.getX() - e2.getX() < 0){
                    //返回上一页
                    showPrePage();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    /**
     * 监听屏幕上的响应事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * 跳转下一页方法，由子类决定跳转到哪一页
     */
    protected abstract void showNextPage();
    /**
     * 跳转上一页方法，由子类决定跳转到哪一页
     */
    protected abstract void showPrePage();

    /**
     * 点击下一页按钮
     * @param v
     */
    public void nextPage(View v){
        showNextPage();
    }
    /**
     * 点击上一页按钮
     * @param v
     */
    public void prePage(View v){
        showPrePage();
    }

}
