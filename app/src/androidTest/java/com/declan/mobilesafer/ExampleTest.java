package com.declan.mobilesafer;

import android.test.AndroidTestCase;

import com.declan.mobilesafer.db.dao.BlackNumberDao;

/**
 * Created by Administrator on 2016/7/12.
 */
public class ExampleTest extends AndroidTestCase {
    public void insert(){
        BlackNumberDao dao = BlackNumberDao.getInstance(getContext());
        dao.insert("110","1");
    }
}
