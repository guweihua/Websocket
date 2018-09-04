package com.quansu;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.quansu.cons.CP;
import com.ysnows.quansu.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 *
 */
public abstract class BaseApp extends Application implements Thread.UncaughtExceptionHandler {
//public abstract class BaseApp extends Application implements Thread.UncaughtExceptionHandler {


    public static Boolean refresh = false;
    public boolean webSocketFail = false;


    private ArrayList<Activity> activities = new ArrayList<>();
    private static BaseApp instance;

    public static BaseApp getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();


        /** 说明:
         *KLog 配置
         */
        if (shouldInit()) {
            instance = this;
            /** 说明:
             *测试初始化,正式发布的时候注视掉
             */
//            Stetho.initialize(
//                    Stetho.newInitializerBuilder(this)
//                            .enableDumpapp(
//                                    Stetho.defaultDumperPluginsProvider(this))
//                            .enableWebKitInspector(
//                                    Stetho.defaultInspectorModulesProvider(this))
//                            .build());


            initApp();
        }


        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null);


    }

    /**
     * 初始化工作
     */
    protected void initApp() {

    }

    public abstract void initWebsocket();

    protected BaseApp getContext() {
        return this;
    }


    @Override
    public void uncaughtException(Thread arg0, Throwable arg1) {
        System.exit(0);
    }

    /**
     * Activity关闭时，删除Activity列表中的Activity对象
     */
    public void removeActivity(Activity a) {
        activities.remove(a);
    }

    /**
     * 向Activity列表中添加Activity对象
     */
    public void addActivity(Activity a) {
        activities.add(a);
    }

    /**
     * 关闭Activity列表中的所有Activity
     */
    public void killApp() {
        for (Activity activity : activities) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    public void CloseApp(){
        for (Activity activity : activities) {
            if (null != activity) {
                activity.finish();
            }
        }
    }




    /**
     * 判断是否是主进程
     *
     * @return
     */
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();

        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

}
