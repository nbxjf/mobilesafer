package com.declan.mobilesafer.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.declan.mobilesafer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class ContactsListActivity extends AppCompatActivity{

    private ListView lv_contact;
    List<String> list_name = new ArrayList<String>();
    List<String> list_phone = new ArrayList<String>();
    private MyAdapter myAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        initUI();
        initData();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myAdapter = new MyAdapter();
            lv_contact.setAdapter(myAdapter);
        }
    };


    class MyAdapter extends  BaseAdapter{

        @Override
        public int getCount() {
            return list_name.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.listview_contact_item, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            tv_name.setText(list_name.get(position));
            tv_phone.setText(list_phone.get(position));
            return view;
        }
    }


    /**
     * 获取本地联系人数据
     */
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                while(cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    list_name.add(name);
                    list_phone.add(phone);
                }
                cursor.close();
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    /**
     * 初始化
     */
    private void initUI() {
        lv_contact = (ListView) findViewById(R.id.lv_contact);
        lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 1 获取条目中的索引
                if(list_phone.get(position)!= null){
                    String phone = list_phone.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("phone",phone);
                    setResult(0,intent);
                    finish();
                }
            }
        });
    }
}
