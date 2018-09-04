package com.bdloc.componnent;

import android.support.annotation.NonNull;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.quansu.BaseApp;


/**
 * 百度定位封装类
 */
public class BaiduLocation {

    public static double mylongitude = -1.0;// 经度
    public static double mylatitude = -1.0;// 纬度
    public static String myProvince = null;// 省份
    public static String myCity = null;// 城市
    public static String myCounty = null;// 县
    public static String myCityadd = null;// 详细地址
    private static LocationClient locationClient;


    /**
     * 回调位置的接口定义
     */
    public interface MyLocationListener {
        //        void myLocatin(double mylongitude, double mylatitude, String province, String city, String street);
        void myLocatin(BDLocation location);
    }

    ;

    public static MyLocationListener myLocationListener;

    /**
     * 回调位置方法
     *
     * @param myLocationListener
     */
    public static void setMyLocationListener(MyLocationListener myLocationListener) {
        BaiduLocation.myLocationListener = myLocationListener;
    }


    /**
     * 获取当前位置
     */
    public static void getLocation() {
        //声明LocationClient类
        locationClient = new LocationClient(BaseApp.getInstance().getApplicationContext());
        locationClient.setLocOption(getLocationClientOption());
        // 注册位置监听器
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null) {
                    return;
                }

                if (location != null) {
                    int locType = location.getLocType();
                    String city = location.getCity();
                    String district = location.getDistrict();
                }

                if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    myCityadd = location.getAddrStr();//详细地址
                    if (myLocationListener != null) {
                        myLocationListener.myLocatin(location);
                    }
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    if (myLocationListener != null) {
                        myLocationListener.myLocatin(location);
                    }
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                }


            }
        });

        locationClient.start();

        /*
         * 当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。调用requestLocation(
         * )后，每隔设定的时间，定位SDK就会进行一次定位。如果定位SDK根据定位依据发现位置没有发生变化，就不会发起网络请求，
         * 返回上一次定位的结果；如果发现位置改变，就进行网络请求进行定位，得到新的定位结果。
         * 定时定位时，调用一次requestLocation，会定时监听到定位结果。
         */
//        locationClient.requestLocation();
//        locationClient.stop();
    }

    @NonNull
    private static LocationClientOption getLocationClientOption() {
        // 设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedLocationPoiList(true);  //获取附近 poi
//        option.setIsNeedLocationDescribe(true); //位置语义化
        option.setOpenGps(true); // 是否打开GPS
        option.setCoorType("bd09ll"); // 设置返回值的坐标类型
//        option.setScanSpan(5000); // 设置定时定位的时间间隔。单位毫秒
//        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setAddrType("all");
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        return option;
    }


    public static LocationClient getLocationClient() {
        return locationClient;
    }
}
