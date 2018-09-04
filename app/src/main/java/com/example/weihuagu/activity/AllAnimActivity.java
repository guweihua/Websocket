package com.example.weihuagu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.weihuagu.BaseActivity;
import com.example.weihuagu.MainActivity;
import com.example.weihuagu.utils.UiSwitch;
import com.example.weihuagu.websocket.R;

public class AllAnimActivity extends BaseActivity {


    private Button btOpen;
    private Button btVp;
    private Button btSplash;
    private Button btSearch;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_all_anim;
    }


    @Override
    protected void initListeners() {


        btOpen.setOnClickListener(v -> {
            UiSwitch.single(AllAnimActivity.this, AnimActivity.class);
        });

        btVp.setOnClickListener(v -> {
            UiSwitch.single(AllAnimActivity.this, ELMaDetialsActivity.class);

        });

        btSplash.setOnClickListener(v -> {

            UiSwitch.single(AllAnimActivity.this, SplashDemoActivity.class);
        });

        btSearch.setOnClickListener(v -> {
            UiSwitch.single(AllAnimActivity.this,SearchAnimActivity.class);
        });


    }

    @Override
    protected void initThing(Bundle savedInstanceState) {

        btOpen = findViewById(R.id.bt_open);
        btVp = findViewById(R.id.bt_vp);
        btSplash = findViewById(R.id.bt_splash);
        btSearch = findViewById(R.id.bt_search);

    }

    @Override
    public boolean getisUseAutoLayout() {
        return false;
    }


}
