package com.example.weihuagu.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.weihuagu.MainActivity;
import com.example.weihuagu.websocket.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView imgSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);


        initView();


    }





    private void initView() {

        imgSplash = findViewById(R.id.img_splash);


//        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
//        alphaAnimation.setDuration(2000);//设置动画播放时长1000毫秒（1秒）
//        imgSplash.startAnimation(alphaAnimation);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);





    }
}
