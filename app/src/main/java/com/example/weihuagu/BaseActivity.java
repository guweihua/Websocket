package com.example.weihuagu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";

    /**
     * @return 提供LayoutId
     */
    abstract protected int provideContentViewId();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(provideContentViewId());


        initThing(savedInstanceState);

        initListeners();
    }

    protected void beforeSetCView(Bundle savedInstanceState) {

    }


    protected abstract void initListeners();


    protected abstract void initThing(Bundle savedInstanceState);




    public abstract boolean getisUseAutoLayout();
}
