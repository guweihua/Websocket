package com.example.weihuagu;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.weihuagu.activity.AllAnimActivity;
import com.example.weihuagu.activity.AnimActivity;
import com.example.weihuagu.activity.BlurActivity;
import com.example.weihuagu.activity.DiscoveryVideoActivity;
import com.example.weihuagu.activity.ELMaActivity;
import com.example.weihuagu.activity.ELMaDetialsActivity;
import com.example.weihuagu.activity.ExtentBaseActivity;
import com.example.weihuagu.activity.ImageActivity;
import com.example.weihuagu.activity.ImageDragActivity;
import com.example.weihuagu.activity.PreviewVideoActivity;
import com.example.weihuagu.activity.WebActivity;
import com.example.weihuagu.utils.UiSwitch;
import com.example.weihuagu.views.BaseView;
import com.example.weihuagu.views.TitleBar;
import com.example.weihuagu.websocket.R;
import com.example.weihuagu.websocket.WebSocketActivity;
import com.wanglu.photoviewerlibrary.PhotoViewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {
//public class MainActivity extends AppCompatActivity {


    private ImageView video;


    int mMediaWidth;
    int mMediaHeight;
    int mRotate;


    String uriPath = "https://video.quansuwangluo.com/video/iOS/d/A930E2E56424D10F045E08F1408BD847.mp4";
    private Button b1;
    private Button b2;
    private Button b3;
    private ImageView image;
    private ImageView image2;
    private ImageView image3;

    ArrayList<String> paths = new ArrayList<>();
    List<String> paths2 = new ArrayList<>();
    private Button btVidoe;
    private Button blur;
    private Button btBase;
    private Button btAnim;
    private Button btElma;


    @Override
    protected int provideContentViewId() {


        return R.layout.activity_main;
    }


    @Override
    protected void initListeners() {


        btAnim.setOnClickListener(v -> {
            UiSwitch.single(MainActivity.this, AllAnimActivity.class);
        });


        b1.setOnClickListener(v -> {


            UiSwitch.single(MainActivity.this, WebActivity.class);

        });


        b2.setOnClickListener(v -> {
            UiSwitch.single(MainActivity.this, WebSocketActivity.class);

        });

        b3.setOnClickListener(v -> {
            UiSwitch.single(MainActivity.this, ImageActivity.class);
        });

        image.setOnClickListener(v -> {


            Intent intent = new Intent(MainActivity.this, ImageDragActivity.class);
            int location[] = new int[2];
            image.getLocationOnScreen(location);
            intent.putExtra("left", location[0]);
            intent.putExtra("top", location[1]);
            intent.putExtra("height", image.getHeight());
            intent.putExtra("width", image.getWidth());
            intent.putExtra(ImageDragActivity.EXTRA_IMAGE_INDEX, 0);
            intent.putStringArrayListExtra(ImageDragActivity.EXTRA_IMAGE_URLS, paths);


            startActivity(intent);
            overridePendingTransition(0, 0);
        });


        image2.setOnClickListener(v -> {


            Intent intent = new Intent(MainActivity.this, ImageDragActivity.class);
            int location[] = new int[2];
            image.getLocationOnScreen(location);
            intent.putExtra("left", location[0]);
            intent.putExtra("top", location[1]);
            intent.putExtra("height", image.getHeight());
            intent.putExtra("width", image.getWidth());
            intent.putExtra(ImageDragActivity.EXTRA_IMAGE_INDEX, 1);
            intent.putStringArrayListExtra(ImageDragActivity.EXTRA_IMAGE_URLS, paths);


            startActivity(intent);
            overridePendingTransition(0, 2);
        });

        image3.setOnClickListener(v -> {


            Intent intent = new Intent(MainActivity.this, ImageDragActivity.class);
            int location[] = new int[2];
            image.getLocationOnScreen(location);
            intent.putExtra("left", location[0]);
            intent.putExtra("top", location[1]);
            intent.putExtra("height", image.getHeight());
            intent.putExtra("width", image.getWidth());
            intent.putExtra(ImageDragActivity.EXTRA_IMAGE_INDEX, 2);
            intent.putStringArrayListExtra(ImageDragActivity.EXTRA_IMAGE_URLS, paths);


            startActivity(intent);
            overridePendingTransition(0, 2);
        });


        video.setOnClickListener(v -> startVideoActivity());


        btVidoe.setOnClickListener(v -> {
            UiSwitch.single(MainActivity.this, DiscoveryVideoActivity.class);

        });


        blur.setOnClickListener(v -> {
            UiSwitch.single(MainActivity.this, BlurActivity.class);

        });
    }

    @Override
    protected void initThing(Bundle savedInstanceState) {


        initView();


        new Thread() {
            @Override
            public void run() {
                super.run();

                Map<String, String> map = new HashMap<>();


                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(uriPath, map);


                String width = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
                String height = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
                String rotate = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
                mRotate = Integer.valueOf(rotate);
                mMediaWidth = Integer.valueOf(width);
                mMediaHeight = Integer.valueOf(height);
            }
        }.start();


    }

    @Override
    public boolean getisUseAutoLayout() {
        return true;
    }


    private void initView() {

        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        btAnim = findViewById(R.id.bt_anim);
        video = findViewById(R.id.b4);
        image = findViewById(R.id.image);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        btVidoe = findViewById(R.id.bt_vidoe);
        blur = findViewById(R.id.blur);

        TitleBar titleBar = findViewById(R.id.title_bar);


        Glide.with(this)
                .load("http://cdnneed.quansuwangluo.com/twitter/iOS/j/843AABBC74C3F7F11272ED6A352717A0.jpg")
                .into(video);


        String url1 = "http://img02.tooopen.com/images/20160509/tooopen_sy_161967094653.jpg";
        String url2 = "http://cdnneed.quansuwangluo.com/twitter/iOS/j/843AABBC74C3F7F11272ED6A352717A0.jpg";
        String url3 = "http://img02.tooopen.com/images/20160509/tooopen_sy_161967094653.jpg";

        Glide.with(this)
                .load(url1)
                .into(image);

        Glide.with(this)
                .load(url2)
                .into(image2);

        Glide.with(this)
                .load(url3)
                .into(image3);

        paths.add(url1);
        paths.add(url2);
        paths.add(url3);


//        PhotoViewer
//                .setData(paths2)
//                .setCurrentPage(1)
//                .setPicSpace(0)
//                .setCountOfRow(5)
//                .setClickView(image)
//                .setShowImageViewInterface(new PhotoViewer.ShowImageViewInterface() {
//                    @Override
//                    public void show(ImageView imageView, String s) {
//                        Glide.with(MainActivity.this).load(s).into(imageView);
//
//                    }
//                })
//          .start(this);


        Log.e("asjfksafja", "try" + mMediaWidth + "    " + mMediaHeight + "    " + mRotate);


    }

    private void startVideoActivity() {


        Log.e("asjfksafja", "" + mMediaWidth + "    " + mMediaHeight + "    " + mRotate);


        int location[] = new int[2];
        video.getLocationOnScreen(location);
        Intent intent = new Intent(MainActivity.this, PreviewVideoActivity.class);//你要跳转的界面
        intent.putExtra("left", location[0]);
        intent.putExtra("top", location[1]);
        intent.putExtra("height", video.getHeight());
        intent.putExtra("width", video.getWidth());
        intent.putExtra("path", uriPath);
        intent.putExtra("mediaWidth", mMediaWidth);
        intent.putExtra("mediaHeight", mMediaHeight);
        intent.putExtra("rotateAngle", mRotate);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


}
