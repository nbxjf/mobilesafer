package com.declan.mobilesafer.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;

import com.declan.mobilesafer.utils.ConstantValue;
import com.declan.mobilesafer.utils.SpUtils;

/**
 * Created by Administrator on 2016/6/1.
 */
public class LocationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //获取手机的经纬度坐标
        //1 获取位置管理对象
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //2 以最优的方式获取经纬度坐标
        Criteria criteria = new Criteria();
        //允许花费
        criteria.setCostAllowed(true);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);//获取经纬度的精确度
        String bestProvider = lm.getBestProvider(criteria, true);
        //3 在一定的时间间隔或者一定距离后获取经纬坐标
        MyLocationListener myLocationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(bestProvider, 0, 0, myLocationListener);
    }

    class MyLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude();//精度
            double latitude = location.getLatitude(); //纬度
            SmsManager aDefault = SmsManager.getDefault();
            String safe_phone = SpUtils.getString(getApplicationContext(), ConstantValue.SAFE_PHONE, "");
            aDefault.sendTextMessage(safe_phone,null,longitude+","+latitude,null,null);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

}
