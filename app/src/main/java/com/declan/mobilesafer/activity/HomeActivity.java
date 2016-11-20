package com.declan.mobilesafer.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.declan.mobilesafer.R;
import com.declan.mobilesafer.utils.ConstantValue;
import com.declan.mobilesafer.utils.Md5Utils;
import com.declan.mobilesafer.utils.SpUtils;

public class HomeActivity extends AppCompatActivity {


    private GridView gv_home;
    private String[] titleStr;
    private int[] img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();
        initData();//初始化数据
    }

    private void initData() {
        //准备数据(文字、图片)
        titleStr = new String[]{
            "手机防盗","通信卫士","软件管理","进程管理","流量管理","手机杀毒","缓存清理","高级工具","设置中心"
        };
        img = new int[]{
                R.drawable.home_safe,R.drawable.home_callmsgsafe,R.drawable.home_apps,
                R.drawable.home_taskmanager,R.drawable.home_netmanager,R.drawable.home_trojan,
                R.drawable.home_sysoptimize,R.drawable.home_tools,R.drawable.home_settings
        };
        //九宫格控件设置数据适配器
        gv_home.setAdapter(new MyAdapter());
        //注册九宫格单个条目的点击事件
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //开启对话框
                        showDialog();
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(),BlackNumberActivity.class));
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        //跳转到高级工具功能列表界面
                        startActivity(new Intent(getApplicationContext(),AToolActivity.class));
                        break;
                    case 8:
                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void showDialog() {
        //判断本地是否有设置密码
        String pwd = SpUtils.getString(this, ConstantValue.MOBILE_SAFE_PWD, "");
        if(TextUtils.isEmpty(pwd)){
           showSetPwdDialog();
        }else{
            //2 确认密码对话框
            showInputPwdDialog();
        }
    }

    /**
     * 再次进入，输入密码，弹出对话框
     */
    private void showInputPwdDialog() {
        //1 初始设置对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        final View inflate = View.inflate(this, R.layout.dialog_input_pwd, null);
        dialog.setView(inflate);
        dialog.show();
        Button bt_submit = (Button) inflate.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) inflate.findViewById(R.id.bt_cancel);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText set_pwd = (EditText) inflate.findViewById(R.id.et_set_pwd);
                String pwd = set_pwd.getText().toString();
                if(!TextUtils.isEmpty(pwd) ){
                    String sp_pwd = SpUtils.getString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PWD, "");
                    if(Md5Utils.md5(pwd).equals(sp_pwd)){
                        Intent intent = new Intent(getApplicationContext(), SetupOverActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }else {
                        Toast.makeText(getApplicationContext(),"密码不正确",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"请输入密码",Toast.LENGTH_SHORT).show();
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 首次进入设置密码，弹出对话框
     */
    private void showSetPwdDialog() {
        //1 初始设置对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        final View inflate = View.inflate(this, R.layout.dialog_set_pwd, null);
        dialog.setView(inflate);
        dialog.show();
        Button bt_submit = (Button) inflate.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) inflate.findViewById(R.id.bt_cancel);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText set_pwd = (EditText) inflate.findViewById(R.id.et_set_pwd);
                EditText reset_pwd = (EditText) inflate.findViewById(R.id.et_reset_pwd);
                String pwd = set_pwd.getText().toString();
                String repwd = reset_pwd.getText().toString();
                if(!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(repwd)){
                    if(pwd.equals(repwd)){
                        Intent intent = new Intent(getApplicationContext(), SetupOverActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                        SpUtils.putString(getApplicationContext(),ConstantValue.MOBILE_SAFE_PWD, Md5Utils.md5(pwd));
                    }else {
                        Toast.makeText(getApplicationContext(),"请输入相同的密码",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"请输入密码",Toast.LENGTH_SHORT).show();
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void initUI() {
        gv_home = (GridView) findViewById(R.id.gv_home);
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return titleStr.length;
        }

        @Override
        public Object getItem(int position) {
            return titleStr[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(),R.layout.gridview_item,null);
            ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_title.setText(titleStr[position]);
            iv_icon.setBackgroundResource(img[position]);
            return view;
        }
    }
}
