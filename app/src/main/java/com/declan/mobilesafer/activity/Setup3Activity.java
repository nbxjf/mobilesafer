package com.declan.mobilesafer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.declan.mobilesafer.R;
import com.declan.mobilesafer.utils.ConstantValue;
import com.declan.mobilesafer.utils.SpUtils;
import com.declan.mobilesafer.utils.ToastUtils;

/**
 * Created by Administrator on 2016/5/29.
 */
public class Setup3Activity  extends BaseSetupActivity{

    private EditText et_safe_phone;
    private Button bt_select_contacts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        initUI();
    }

    @Override
    protected void showNextPage() {
        String phone = et_safe_phone.getText().toString();
        if(!TextUtils.isEmpty(phone)){
            Intent intent = new Intent(getApplicationContext(), Setup4Activity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.anim_next_in, R.anim.anim_next_out);
            //如果是输入的电话号码
            SpUtils.putString(getApplicationContext(), ConstantValue.SAFE_PHONE, phone);
        }else{
            ToastUtils.show(getApplicationContext(),"请选择输入联系人");
        }
    }

    @Override
    protected void showPrePage() {
        Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.anim_pre_in, R.anim.anim_pre_out);
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        //输入联系人
        et_safe_phone = (EditText) findViewById(R.id.et_safe_phone);
        et_safe_phone.setText(SpUtils.getString(getApplicationContext(),ConstantValue.SAFE_PHONE,""));
        //选择联系人
        bt_select_contacts = (Button) findViewById(R.id.bt_select_contacts);
        bt_select_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactsListActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            String phone = data.getStringExtra("phone");
            et_safe_phone.setText(phone);

            //存储联系人
            SpUtils.putString(getApplicationContext(), ConstantValue.SAFE_PHONE, phone);
        }
    }

}
