package com.bdloc.componnent;

/**
 * Created by xianguangjin on 16/6/28.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public class MyPosition {

    public MyPosition(String city, String district, String key, PtBean pt) {
        this.city = city;
        this.district = district;
        this.key = key;
        this.pt = pt;
    }

    public String city;
    public String district;  //区
    public String key;  //

    public PtBean pt;


    public static class PtBean {
        public PtBean(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double latitude;
        public double longitude;
    }


}
