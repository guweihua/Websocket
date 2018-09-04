package com.example.weihuagu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.weihuagu.BaseActivity;
import com.example.weihuagu.views.splash.WowSplashView;
import com.example.weihuagu.views.splash.WowView;
import com.example.weihuagu.websocket.R;

public class SplashDemoActivity extends BaseActivity {

    private WowSplashView mWowSplashView;
    private WowView mWowView;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_splash_demo;
    }


    @Override
    protected void initListeners() {


        mWowSplashView.setOnEndListener(new WowSplashView.OnEndListener() {
            @Override
            public void onEnd(WowSplashView wowSplashView) {
                mWowSplashView.setVisibility(View.GONE);
                mWowView.setVisibility(View.VISIBLE);
                mWowView.startAnimate(wowSplashView.getDrawingCache());

            }
        });


    }

    @Override
    protected void initThing(Bundle savedInstanceState) {

        mWowSplashView = findViewById(R.id.wowSplash);
        mWowView = findViewById(R.id.wowView);

        mWowSplashView.startAnimate();
    }

    @Override
    public boolean getisUseAutoLayout() {
        return false;
    }
}
