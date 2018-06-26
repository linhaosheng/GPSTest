package com.android.gpstest;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gpstest.util.DateUtils;

import java.util.Iterator;

public class MyGpsTestActivity extends AppCompatActivity implements View.OnClickListener {

    private MyGpsStatusFragment gps_status;
    private MyGpsSkyFragment gps_sky;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Button btn_changview;
    private ImageView setting_back;


    private LocationManager mLocationManager;
    private LocationListener locationListener;
    private final static String TAG = "MyGpsTestActivity===";
    private LocationInterface locationInterface;

    private TextView tv_shijian;
    private TextView tv_jingdu;
    private TextView tv_weidu;
    private TextView tv_shudu;
    private TextView tv_weixing;
    private TextView tv_jingquedu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_my_gps_test);


        /**
         * 拿到事务管理器并开启事务
         */
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        btn_changview = findViewById(R.id.btn_changview);
        btn_changview.setOnClickListener(this);
        btn_changview.setTag(0);

        /**
         * 启动默认选中第一个
         */
        gps_status = new MyGpsStatusFragment();
        transaction.replace(R.id.fragment_content, gps_status);
        transaction.commit();
        initView();
        initData();
    }

    private void setLocationInterface(LocationInterface mLocationInterface) {
        this.locationInterface = mLocationInterface;
    }


    private void initData() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if (locationInterface != null) {
                    locationInterface.updateLocation(location);
                }
                if (location != null) {
                    updateView(location);
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        GpsStatus.Listener listener = new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int event) {
                switch (event) {
                    //第一次定位
                    case GpsStatus.GPS_EVENT_FIRST_FIX:
                        Log.i(TAG, "第一次定位");
                        break;
                    //卫星状态改变
                    case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                        Log.i(TAG, "卫星状态改变");
                        //获取当前状态
                        GpsStatus gpsStatus = mLocationManager.getGpsStatus(null);
                        if (gps_status != null) {
                            gps_status.updateGpsSkyView(gpsStatus);
                        }

                        if (gps_sky != null) {
                            gps_sky.updateChatView(gpsStatus);
                        }
                        //获取卫星颗数的默认最大值
                        int maxSatellites = gpsStatus.getMaxSatellites();
                        //创建一个迭代器保存所有卫星
                        Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                        int count = 0;
                        int useCount = 0;
                        while (iters.hasNext() && count <= maxSatellites) {
                            GpsSatellite status = iters.next();
                            count++;
                            if (status.getSnr() > 0.0f) {
                                useCount++;
                            }
                        }
                        tv_weixing.setText(useCount + "/" + count);
                        System.out.println("搜索到：" + count + "颗卫星");
                        break;
                    //定位启动
                    case GpsStatus.GPS_EVENT_STARTED:
                        if (gps_status != null) {
                            gps_status.gpsStart();
                        }
                        // Toast.makeText(MyGpsTestActivity.this,"定位启动",Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "定位启动");
                        break;
                    //定位结束
                    case GpsStatus.GPS_EVENT_STOPPED:
                        if (gps_status != null) {
                            gps_status.gpsStop();
                        }
                        // Toast.makeText(MyGpsTestActivity.this,"定位结束",Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "定位结束");
                        break;
                }
            }
        };
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        mLocationManager.addGpsStatusListener(listener);
    }


    private void initView() {
        tv_shijian = findViewById(R.id.tv_shijian);
        tv_jingdu = findViewById(R.id.tv_jingdu);
        tv_weidu = findViewById(R.id.tv_weidu);
        tv_shudu = findViewById(R.id.tv_shudu);
        tv_weixing = findViewById(R.id.tv_weixing);
        tv_jingquedu = findViewById(R.id.tv_jingquedu);

        setting_back = findViewById(R.id.setting_back);
        setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void updateView(Location location) {

        if (location != null) {
            long time = location.getTime();
            tv_shijian.setText(DateUtils.date(time));

            tv_jingdu.setText("" + (float) location.getLongitude());
            tv_weidu.setText("" + (float) location.getLatitude());
            tv_shudu.setText("" + location.getSpeed() + "m/s");
            tv_jingquedu.setText("" + location.getAccuracy() + "m");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_changview:
                manager = getSupportFragmentManager();
                transaction = manager.beginTransaction();
                int tag = (int) btn_changview.getTag();
                if (tag == 0) {
                    /**
                     * 为了防止重叠，需要点击之前先移除其他Fragment
                     */
                    hideFragment(transaction);
                    gps_sky = new MyGpsSkyFragment();
                    transaction.replace(R.id.fragment_content, gps_sky);
                    transaction.commit();
                    btn_changview.setTag(1);
                    btn_changview.setText(this.getText(R.string.weixinstatus));
                } else {
                    hideFragment(transaction);
                    gps_status = new MyGpsStatusFragment();
                    transaction.replace(R.id.fragment_content, gps_status);
                    transaction.commit();
                    btn_changview.setTag(0);
                    btn_changview.setText(this.getText(R.string.fenbu));
                }
                break;
        }
    }

    /*
 * 去除（隐藏）所有的Fragment
 * */
    private void hideFragment(FragmentTransaction transaction) {
        if (gps_status != null) {
            //transaction.hide(f1);隐藏方法也可以实现同样的效果，不过我一般使用去除
            transaction.remove(gps_status);
        }
        if (gps_sky != null) {
            //transaction.hide(f2);
            transaction.remove(gps_sky);
        }
    }


}
