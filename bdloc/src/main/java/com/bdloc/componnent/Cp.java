package com.bdloc.componnent;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.Poi;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.com.ysnows.bdloc.R;
import com.quansu.BaseApp;
import com.quansu.cons.CP;
import com.quansu.utils.Msg;
import com.quansu.utils.Toasts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.quansu.utils.RxBus.getDefault;
import static com.quansu.utils.SPUtil.MSG;

public class Cp implements IComponent {


    @Override
    public String getName() {
        return CP.cp_bdloc;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();

        switch (actionName) {
            case CP.ACTION_INIT_BDLOC:
                //百度地图
                SDKInitializer.initialize(BaseApp.getInstance().getApplicationContext());
                SDKInitializer.setCoordType(CoordType.BD09LL);
                CC.sendCCResult(cc.getCallId(), CCResult.success());
                return false;
            case CP.ACTION_LOC:
                loc(cc);
                return true;
            case CP.CURRENT_POSITION:
                currentPosition(cc);
                return true;
            case CP.SEARCH_POSITION:

                String city = cc.getParamItem("city");
                String content = cc.getParamItem("content");

                search(cc, city, content);
                return true;
            case CP.CLOSE_POSITION:
                stopPos();
                return false;
        }

        return false;
    }

    private void stopPos() {
        BaiduLocation.getLocationClient().stop();
    }

    /**
     * 初始化搜索模块，注册搜索事件监听
     *
     * @param cc
     * @param city
     * @param content
     */
    private void search(final CC cc, String city, String content) {


        PoiSearch poiSearch = PoiSearch.newInstance();


        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {

                if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    return;
                }


                // 判断搜索结果状态码result.error是否等于检索结果状态码， SearchResult.ERRORNO值的没问题
                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {

                    List<PoiInfo> allPoi = poiResult.getAllPoi();
                    List<String> nearbySearchAddress = new ArrayList<>();

                    Log.e("dasldkasdjkdaa", "onGetPoiResult:" + allPoi);


                    for (int i = 0; i < allPoi.size(); i++) {
                        nearbySearchAddress.add(allPoi.get(i).address);
                    }

                    Log.e("dasldka", "onGetPoiResult:" + nearbySearchAddress);

                    if (nearbySearchAddress != null && nearbySearchAddress.size() > 0) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("data", nearbySearchAddress);


                        CC.sendCCResult(cc.getCallId(), CCResult.success(map));
                    }


                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });


        if (city != null && content != null) {
            poiSearch.searchInCity((new PoiCitySearchOption())
                    .city(city)
                    .keyword(content)
                    .pageNum(30));
            poiSearch.destroy();
        }


    }

    /**
     * 当前位置
     *
     * @param cc
     */
    private void currentPosition(final CC cc) {
        LocationClient mLocationClient = new LocationClient(BaseApp.getInstance().getApplicationContext());

        final List<String> nearbyAddress = new ArrayList<>();
        BaiduLocation.getLocationClient().registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {

                if (null != bdLocation) {

                    List<Poi> poiList = bdLocation.getPoiList();
                    if (poiList != null && poiList.size() > 0) {
                        for (int i = 0; i < poiList.size(); i++) {
                            nearbyAddress.add(poiList.get(i).getName());
                        }
                    }

                    Log.e("dasldka", "locType:" + nearbyAddress);


                    if (nearbyAddress != null && nearbyAddress.size() > 0) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("data", nearbyAddress);
                        CC.sendCCResult(cc.getCallId(), CCResult.success(map));
                    }


//                nearbyPosition.setData(nearbyAddress, city, doubles, locationDescribe);
                }
            }
        });
//        BaiduLocation.getLocationClient().start();


    }

    /**
     * 当前位置信息
     *
     * @param cc
     */
    private void loc(final CC cc) {
        BaiduLocation.setMyLocationListener(new BaiduLocation.MyLocationListener() {
            @Override
            public void myLocatin(BDLocation location) {


                HashMap<String, Object> map = new HashMap<>();
                map.put("province", location.getProvince());
                map.put("city", location.getCity());
                map.put("district", location.getDistrict());
                map.put("longitude", location.getLongitude());
                map.put("latitude", location.getLatitude());
                map.put("street", location.getAddress().street);




                map.put("address", location.getAddress().address);


                BaiduLocation.getLocationClient().stop();
                CC.sendCCResult(cc.getCallId(), CCResult.success(map));
            }
        });

        BaiduLocation.getLocation();
    }


}
