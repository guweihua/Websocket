package com.example.weihuagu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.weihuagu.common.fragment.ImageDetailFragment;

import java.util.ArrayList;

public class ImagePagerAdapter extends FragmentStatePagerAdapter {


    ArrayList<String> urls;


    public ImagePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Fragment getItem(int position) {
        String url = urls.get(position);
        return ImageDetailFragment.newInstance(url, "");
    }
}
