package com.example.weihuagu.common.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weihuagu.adapter.ImagePagerAdapter;
import com.example.weihuagu.common.view.ViewPagerFixed;
import com.example.weihuagu.websocket.R;

import java.util.ArrayList;

public class ImagePagerActivity extends AppCompatActivity {


    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    public static final String EXTRA_IS_NEED_DOWNLOAD = "is_need_download";


    private ViewPagerFixed mPager;
    private TextView indicator;
    private ImageView downloadTv;

    private int pagerPosition;

    ImagePagerAdapter mAdapter;
    ArrayList<String> urls;
    private boolean is_need_download = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);


        mPager = (ViewPagerFixed) findViewById(R.id.pager);


        indicator = (TextView) findViewById(R.id.indicator);
        downloadTv = (ImageView) findViewById(R.id.img_down);


        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        is_need_download = getIntent().getBooleanExtra(EXTRA_IS_NEED_DOWNLOAD, true);
        urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);

        mAdapter = new ImagePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);


        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
        if (urls.size() == 1) {
            indicator.setVisibility(View.GONE);
        } else {
            indicator.setVisibility(View.VISIBLE);
        }
        indicator.setText(text);


        if (!is_need_download) {
            downloadTv.setVisibility(View.GONE);
        } else {
            downloadTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    download();
                }
            });



        }


        // 更新下标
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, mPager.getAdapter().getCount());
                indicator.setText(text);
            }
        });

        mPager.setCurrentItem(pagerPosition);

    }


        private void download() {
//            Dialog.showProgressingDialog(getC, getContext().getString(R.string.saving));
//            String url = urls.get(mPager.getCurrentItem());
//            okHttpDownloadPic(getApplicationContext(), url);

    }
}
