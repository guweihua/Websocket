package com.example.weihuagu.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weihuagu.websocket.R;

import java.util.ArrayList;

public class ElMaAdapter extends PagerAdapter {


    private ArrayList<View> views;
    private int[] imgs = {R.drawable.pizza, R.drawable.pic2, R.drawable.pic3};


    public ElMaAdapter(Context context) {
        views = new ArrayList<>();
        views.add(View.inflate(context, R.layout.item_img, null));
        views.get(0).findViewById(R.id.btn_buy).setOnClickListener(v -> Toast.makeText(context, "buy", Toast.LENGTH_SHORT).show());
        views.add(View.inflate(context, R.layout.item_img, null));

        views.add(View.inflate(context, R.layout.item_img, null));
    }




    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        views.get(position).findViewById(R.id.imageView).setBackgroundResource(imgs[position]);
        container.addView(views.get(position));

        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}
