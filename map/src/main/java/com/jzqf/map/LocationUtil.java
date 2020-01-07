package com.jzqf.map;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;

/**
 * 百度定位
 * 2020/1/7 9:31
 *
 * @author LiuWeiHao
 * Email 872235631@qq.com
 */
public class LocationUtil {
    private final static String TAG = LocationUtil.class.getName();

    /**
     * SDK初始化
     *
     * @param applicationContext 系统上下文
     */
    public static void initSdk(Context applicationContext) {
        SDKInitializer.initialize(applicationContext);
    }

    public static LocationClient initLocation(Context context, BDAbstractLocationListener listener) {
        LocationClient locationClient = new LocationClient(context);
        initSetting(locationClient);
//        locationClient.registerLocationListener(new BDAbstractLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                //获取定位结果
//                StringBuffer sb = new StringBuffer(256);
//                sb.append("\nlatitude : ").append(bdLocation.getLatitude());
//                sb.append("\nlontitude : ").append(bdLocation.getLongitude());
//                sb.append("\ncity:").append(bdLocation.getCity());
//                //获取定位精准度
//                sb.append("\nradius : ").append(bdLocation.getRadius());
//                //位置语义化信息
//                sb.append("\nlocationdescribe : ").append(bdLocation.getLocationDescribe());
//                Log.i(TAG, "onReceiveLocation定位成功:" + sb.toString());
//            }
//        });
        locationClient.registerLocationListener(listener);
        locationClient.restart();
        return locationClient;
    }

    /**
     * 初始化定位设置
     *
     * @param locationClient LocationClient
     */
    private static void initSetting(LocationClient locationClient) {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000 * 20;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(false);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位
        locationClient.setLocOption(option);
    }

}
