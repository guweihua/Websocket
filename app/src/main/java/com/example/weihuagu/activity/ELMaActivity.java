package com.example.weihuagu.activity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.weihuagu.BaseActivity;
import com.example.weihuagu.MarginConfig;
import com.example.weihuagu.adapter.ListAdapter;
import com.example.weihuagu.views.elma.CtrlLinearLayoutManager;
import com.example.weihuagu.views.elma.ZoomHeaderView;
import com.example.weihuagu.websocket.R;

public class ELMaActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ViewPager mViewPager;
    private ZoomHeaderView mZoomHeader;

    private boolean isFirst = true;

    private RelativeLayout mBottomView;

    public static int bottomY;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_elma;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initThing(Bundle savedInstanceState) {
        mRecyclerView = findViewById(R.id.recyclerView);
        mViewPager = findViewById(R.id.viewpager);
        mZoomHeader = findViewById(R.id.zoomHeader);
        mViewPager.setAdapter(new com.example.weihuagu.adapter.ElMaAdapter(getApplicationContext()));


        mViewPager.setOffscreenPageLimit(4);
        CtrlLinearLayoutManager layoutManager = new CtrlLinearLayoutManager(this);

        //未展开禁止滑动
        layoutManager.setScrollEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new ListAdapter());
        mRecyclerView.setAlpha(0);
        mBottomView = findViewById(R.id.rv_bottom);

    }

    @Override
    public boolean getisUseAutoLayout() {
        return true;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirst) {

            for (int i = 0; i < mViewPager.getChildCount(); i++) {
                View viewById = mViewPager.getChildAt(i).findViewById(R.id.ll_bottom);
                viewById.setY(mViewPager.getChildAt(i).findViewById(R.id.imageView).getHeight());
                viewById.setX(MarginConfig.MARGIN_LEFT_RIGHT);
                mZoomHeader.setY(mZoomHeader.getY() - 1);
                isFirst = false;
            }

        }

        bottomY = (int) mBottomView.getY();
        mBottomView.setTranslationY(mBottomView.getY() + mBottomView.getHeight());
        mZoomHeader.setBottomView(mBottomView, bottomY);
    }


    @Override
    public void onBackPressed() {
        if (mZoomHeader.isExpand()) {
            mZoomHeader.restore(mZoomHeader.getY());
        } else {
            finish();
        }
    }


}
