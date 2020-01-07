package com.jzqf.map;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

public class MapActivity extends AppCompatActivity {
    private final static int REQUEST_GPS = 1101;
    MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        initView();
    }

    private void initView() {
        mMapView=findViewById(R.id.mapView);
        mBaiduMap = mMapView.getMap();
        // 未打开位置开关，可能导致定位失败或定位不准，提示用户或做相应处理
        if (!GpsHelper.isOpen(getBaseContext())) {
            DialogHelper.showAlertDialog(MapActivity.this, "请打开GPS定位",
                    "取消", (dialog, which) -> {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, REQUEST_GPS);
                        } else {
                            Toast.makeText(getBaseContext(), "未打开GPS，此功能无法使用!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }, dialog -> {
                        Toast.makeText(getBaseContext(), "未打开GPS，此功能无法使用!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
        } else {
            initLocation();
        }
    }

    private void initLocation() {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = LocationUtil.initLocation(this, new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //获取定位结果
                String sb = "\nlatitude : " + bdLocation.getLatitude()
                        + "\nlontitude : " + bdLocation.getLongitude()
                        + "\ncity:" + bdLocation.getCity()
                        + "\nradius : " + bdLocation.getRadius()
                        + "\nlocationdescribe : " + bdLocation.getLocationDescribe();
                Log.i("BaiduMapActivity", "定位成功:" + sb);
                LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GPS) {
            if (GpsHelper.isOpen(getApplicationContext())) {
                initLocation();
            } else {
                Toast.makeText(getBaseContext(), "未打开GPS，此功能无法使用!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时必须调用mMapView. onResume ()
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时必须调用mMapView. onPause ()
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        // 在activity执行onDestroy时必须调用mMapView.onDestroy()
        mMapView.onDestroy();
    }
}
