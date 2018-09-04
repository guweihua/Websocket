package com.example.weihuagu.activity;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.weihuagu.BaseActivity;
import com.example.weihuagu.views.JazzyViewPager;
import com.example.weihuagu.websocket.R;

public class ExtentBaseActivity extends BaseActivity {


    private JazzyViewPager viewPager;
    private int[] mImgIds;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_extent_base;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initThing(Bundle savedInstanceState) {

        viewPager = findViewById(R.id.viewpager);


        mImgIds = new int[]{R.drawable.ic_car_1, R.drawable.ic_car_2, R.drawable.ic_car_3,
                R.drawable.ic_car_4, R.drawable.ic_car_5, R.drawable.ic_car_6};


        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImgIds.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(ExtentBaseActivity.this);
                imageView.setImageResource(mImgIds[position]);
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                container.addView(imageView);
                viewPager.setObjectForPosition(imageView, position);
                return imageView;
            }


        });


    }

    @Override
    public boolean getisUseAutoLayout() {
        return true;
    }
}
