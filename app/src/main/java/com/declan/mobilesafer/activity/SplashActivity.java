package com.declan.mobilesafer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.declan.mobilesafer.R;
import com.declan.mobilesafer.utils.ConstantValue;
import com.declan.mobilesafer.utils.SpUtils;
import com.declan.mobilesafer.utils.StreamUtils;
import com.declan.mobilesafer.utils.ToastUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class SplashActivity extends AppCompatActivity {

    //更新新版本的状态码
    private static final int UPDATE_VERSION = 100;
    //进入主界面的状态码
    private static final int ENTER_HOME = 101;
    private static final int URL_ERROR = 102; //Url地址出错异常
    private static final int IO_ERROR = 103; // IO异常
    private static final int JSON_ERROR = 104; //json数据异常
    private TextView tv_version_name;
    private int localVersionCode;
    private String versionDes;

    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case UPDATE_VERSION:
                    //更新操作
                    showUpdateDialog();
                    break;
                case ENTER_HOME:
                    //进入应用程序主界面,activity的跳转
                    enterHome();
                    break;
                case URL_ERROR:
                    ToastUtils.show(getApplicationContext(), "URL异常");
                    enterHome();
                    break;
                case IO_ERROR:
                    ToastUtils.show(getApplicationContext(), "读取异常");
                    enterHome();
                    break;
                case JSON_ERROR:
                    ToastUtils.show(getApplicationContext(), "json解析异常");
                    enterHome();
                    break;

            }

        }
    };
    private String downloadUrl;
    private RelativeLayout rl_root;

    /**
     * 弹出对话框提示用户更新
     */
    private void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setIcon(R.drawable.)
        builder.setTitle("版本更新");
        builder.setMessage(versionDes);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadApk();
            }
        });
        builder.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });
        //用户点击取消按钮就直接进入主界面
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void downloadApk() {
        //apk下载,放置apk的所在地址
        //1 判断sd卡是否可用
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //2 sd卡路径
            String path =Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"mobilesafer.apk";
            //3 发送请求，获取apk
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.download(downloadUrl, path, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    //下载成功
                    File result = responseInfo.result;
                    System.out.println("下载成功");
                    //提示用户安装
                    installApk(result);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    //下载失败
                    System.out.println("下载失败");
                }

            });
        }
    }

    /**
     * 安装apk
     */
    private void installApk(File file) {
        //系统应用界面
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        //startActivity(intent);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);
        initData();
        //initAnimation();
        //初始化数据库
        initDB();
    }

    /**
     * 初始化数据库
     */
    private void initDB() {
        //1 归属地的数据库的拷贝过程
        initAddressDB("address.db");
    }

    /**
     * 拷贝数据库值到files文件夹下
     * @param dbName 数据库名称
     */
    private void initAddressDB(String dbName) {
        //1 在files文件夹下创建同名dbName数据库文件
        File filesDir = getFilesDir();
        File file = new File(filesDir, dbName);
        if(file.exists()){
            return;
        }
        InputStream stream = null;
        FileOutputStream fos = null;
        //2 读取第三方资产目录下的文件
        try {
            stream = getAssets().open(dbName);
            //3 将读取的内容写入到制定的文件下去
            fos = new FileOutputStream(file);
            //4 每次读取的内容的大小
            byte[] bs = new byte[1024];
            int temp = -1;
            while ((temp =stream.read(bs)) != -1){
                fos.write(bs,0,temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
              if(stream!=null && fos != null){
                  try {
                      stream.close ();
                      fos.close();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
        }
    }

    /**
     * 淡入动画效果
     */
   /* private void initAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0,1);
        animation.setDuration(3000);
        rl_root.startAnimation(animation);
    }*/

    /**
     * 进入主界面
     */
    private void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        //在进入主界面后，销毁导航界面
        finish();
    }


    /**
     * 初始化数据
     */
    private void initData() {
        tv_version_name.setText("版本名称：" + getVersionName());
        //获取当前versionCode
        localVersionCode = getVersionCode();

        //判断是够更新
        if(SpUtils.getBoolean(this, ConstantValue.OPEN_UPDATE,false)){
            //获取服务器的版本,进行更新
            checkVersion();
        }else{
            //进入主界面,用消息机制，不然直接进入主界面
            handler.sendEmptyMessageDelayed(ENTER_HOME,3000);
        }
    }

    /**
     * 链接服务器，获取json数据包，获得版本信息数据
     */
    private void checkVersion() {
        final Thread thread = new Thread(){
            @Override
            public void run() {
                String path = "http://192.168.1.120:80/android/update.json";
                //Message msg = new Message();
                Message msg = Message.obtain();
                long startTime = System.currentTimeMillis();
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);
                    if(conn.getResponseCode() == 200){
                        InputStream is = conn.getInputStream();
                        String json = StreamUtils.streamToString(is);
                        //json的解析
                        JSONObject jsonObject = new JSONObject(json);
                        String versionName = jsonObject.getString("versionName");
                        String versionCode = jsonObject.getString("versionCode");
                        String Des = jsonObject.getString("versionDes");
                        versionDes = new String(Des.getBytes(),"utf-8");
                        //versionDes = URLDecoder.decode(URLEncoder.encode(xmString, "UTF-8"));
                        downloadUrl = jsonObject.getString("downloadUrl");

                        //比对服务器版本号与本地版本号
                        if(localVersionCode < Integer.parseInt(versionCode) ){
                            //本地版本号小于服务器版本号,子线程不能刷新UI，使用handle
                            msg.what = UPDATE_VERSION;
                        }else{
                            //否则进入主界面
                            msg.what = ENTER_HOME;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    msg.what = URL_ERROR;
                } catch (IOException e) {
                    e.printStackTrace();
                    msg.what = IO_ERROR;
                }catch (JSONException e) {
                    e.printStackTrace();
                    msg.what = JSON_ERROR;
                }finally {
                    //指定睡眠时间,3s显示之间，少于3s不做处理，不然睡够3s
                    long endTime = System.currentTimeMillis();
                    if((endTime - startTime)<3000){
                        try {
                            Thread.sleep(3000-(endTime - startTime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendMessage(msg);
                }

            }
        };
        thread.start();
    }

    /**
     * 获取versionName
     */
    public String getVersionName() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取versionCode
     * @return int versionCode
     */

    public int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
