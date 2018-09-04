package com.bdloc.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.com.ysnows.bdloc.R;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

public class BdlocActivity extends AppCompatActivity {

    protected TextView tvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_bdloc);
        tvResult = findViewById(R.id.tv_result);

        initBdloc();
        initPermission();

//        initCurrent();

    }

    private void initCurrent() {
        CC.obtainBuilder(com.quansu.cons.CP.cp_bdloc)
                .setActionName(com.quansu.cons.CP.CURRENT_POSITION)
                .build()
                .callAsync(new IComponentCallback() {
                    @Override
                    public void onResult(CC cc, CCResult result) {

                        List<String> current_position = result.getDataItem("current_position");

                        Log.e("dasldka", "CURRENT_POSITION:" +current_position);
                    }
                });

    }

    public void toLoc(View view) {
        CC.obtainBuilder(com.quansu.cons.CP.cp_bdloc)
                .setActionName(com.quansu.cons.CP.ACTION_LOC)
                .build()
                .callAsync(new IComponentCallback() {
                    @Override
                    public void onResult(CC cc, CCResult result) {
                        tvResult.setText(result.getCode() + "");
                    }
                });

    }


    /**
     * 初始化定位权限
     */
    private void initPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.ACCESS_FINE_LOCATION)
                .start();
    }

    /**
     * 初始化百度地图
     */
    private void initBdloc() {
        CC.obtainBuilder(com.quansu.cons.CP.cp_bdloc)
                .setActionName(com.quansu.cons.CP.ACTION_INIT_BDLOC)
                .build()
                .call();
    }


}
