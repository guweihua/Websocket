package com.example.weihuagu.activity;

import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.weihuagu.BaseActivity;
import com.example.weihuagu.websocket.R;

import java.util.Timer;
import java.util.TimerTask;

public class AnimActivity extends BaseActivity {

    private ImageView viewById;


    int what = 0X11;
    int data = 0;


    private ClipDrawable clipDrawable;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_anim;
    }


    @Override
    protected void initListeners() {

    }

    @Override
    protected void initThing(Bundle savedInstanceState) {
        viewById = findViewById(R.id.img);

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.test_animation);
//
//        viewById.startAnimation(animation);


        clipDrawable = (ClipDrawable) viewById.getDrawable();


        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (data >= 10000) {
                    timer.cancel();
                }
                handler.obtainMessage(what).sendToTarget();
            }
        }, 0, 100);

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            switch (msg.what) {
                case 0x11:

                    clipDrawable.setLevel(data);//扩大截取的图片面积
                    data += 200;

                    break;
            }


        }
    };


    @Override
    public boolean getisUseAutoLayout() {
        return false;
    }
}
