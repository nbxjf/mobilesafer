package com.declan.mobilesafer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.declan.mobilesafer.R;
import com.declan.mobilesafer.utils.ConstantValue;
import com.declan.mobilesafer.utils.SpUtils;

/**
 * Created by Administrator on 2016/6/5.
 */
public class ToastLocationActivity extends Activity {

    private ImageView iv_drag;
    private Button bt_top;
    private Button bt_buttom;
    private int startX;
    private int startY;
    private int moveX;
    private int moveY;
    private WindowManager mWM;
    private long[] mHits = new long[2];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_location);
        initUI();
    }

    private void initUI() {
        iv_drag = (ImageView) findViewById(R.id.iv_drag);
        bt_top = (Button) findViewById(R.id.bt_top);
        bt_buttom = (Button) findViewById(R.id.bt_buttom);
        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);
        int locationX =SpUtils.getInt(getApplicationContext(),ConstantValue.LOCATION_X,0);
        int locationY = SpUtils.getInt(getApplicationContext(),ConstantValue.LOCATION_Y,0);
        //iv_drag在相对布局中，所以其所在位置的规则需要由相对布局提供
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = locationX;
        layoutParams.topMargin = locationY;

        if(locationY > mWM.getDefaultDisplay().getHeight()/2){
            bt_top.setVisibility(View.VISIBLE);
            bt_buttom.setVisibility(View.INVISIBLE);
        }else{
            bt_buttom.setVisibility(View.VISIBLE);
            bt_top.setVisibility(View.INVISIBLE);
        }
        //将规则显示到iv_drag上
        iv_drag.setLayoutParams(layoutParams);

        //监听双击事件
        iv_drag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (mHits[mHits.length -1] - mHits[0] < 500) {
                    //满足双击事件后，调用代码
                    int left =  mWM.getDefaultDisplay().getWidth() / 2 - iv_drag.getWidth() / 2;
                    int top =  mWM.getDefaultDisplay().getHeight() / 2 - iv_drag.getHeight() / 2;
                    int right =  mWM.getDefaultDisplay().getWidth() / 2 + iv_drag.getWidth() / 2;
                    int buttom =  mWM.getDefaultDisplay().getHeight() / 2 + iv_drag.getHeight() / 2;
                    iv_drag.layout(left,top,right,buttom);
                    SpUtils.putInt(getApplicationContext(), ConstantValue.LOCATION_X, iv_drag.getLeft());
                    SpUtils.putInt(getApplicationContext(), ConstantValue.LOCATION_Y, iv_drag.getTop());
                }
            }
        });
        //拖拽监听
        iv_drag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveX = (int) event.getRawX();
                        moveY = (int) event.getRawY();
                        //移动距离
                        int distX = moveX - startX;
                        int distY = moveY - startY;

                        //当前控件所在屏幕的左上角的位置
                        int left = iv_drag.getLeft() + distX;
                        int top = iv_drag.getTop() + distY;
                        int right = iv_drag.getRight() + distX;
                        int buttom = iv_drag.getBottom() + distY;
                        //设置 容错。不能超出屏幕宽度
                        if (left < 0) {
                            return true;
                        }
                        if (right > mWM.getDefaultDisplay().getWidth()) {
                            return true;
                        }
                        if (top < 0) {
                            return true;
                        }
                        if (buttom > (mWM.getDefaultDisplay().getHeight() - 40)) {
                            return true;
                        }
                        if (top > mWM.getDefaultDisplay().getHeight() / 2) {
                            bt_top.setVisibility(View.VISIBLE);
                            bt_buttom.setVisibility(View.INVISIBLE);
                        } else {
                            bt_buttom.setVisibility(View.VISIBLE);
                            bt_top.setVisibility(View.INVISIBLE);
                        }
                        //设置空间的位置
                        iv_drag.layout(left, top, right, buttom);

                        //重置起始坐标
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        //记录坐标位置
                        SpUtils.putInt(getApplicationContext(), ConstantValue.LOCATION_X, iv_drag.getLeft());
                        SpUtils.putInt(getApplicationContext(), ConstantValue.LOCATION_Y, iv_drag.getTop());
                        break;
                }
                //返回false不响应西东事件,返回true相应事件
                //既要相应点击事件，又要相应拖拽事件，则只能返回false
                return false;
            }
        });
    }
}
