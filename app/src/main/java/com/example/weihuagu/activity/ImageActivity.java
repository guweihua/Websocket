package com.example.weihuagu.activity;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.weihuagu.utils.drawphoto.DataUtil;
import com.example.weihuagu.websocket.R;
import com.liyi.viewer.data.ViewData;
import com.liyi.viewer.factory.ImageLoader;
import com.liyi.viewer.listener.OnImageChangedListener;
import com.liyi.viewer.listener.OnViewLongClickListener;
import com.liyi.viewer.listener.OnWatchStatusListener;
import com.liyi.viewer.widget.ImageViewer;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {


    private List<Object> mImageList;
    private List<ViewData> mViewDatas;
    private ImageView image;
    private ImageViewer imagePreivew;


    boolean isLong = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        initView();


        initListener();





    }

    private void initListener() {

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                initPreivew(0);


            }
        });
    }

    private void initPreivew(int position) {

//        if (mViewDatas.get(position).getWidth() == 0) {
//
//                int[] location = new int[2];
//                // 获取在整个屏幕内的绝对坐标
//                ViewData viewData = mViewDatas.get(0);
//                viewData.setX(location[0]);
//                // 此处注意，获取 Y 轴坐标时，需要根据实际情况来处理《状态栏》的高度，判断是否需要计算进去
//                viewData.setY(location[1]);
////                viewData.setWidth(mViewDatas.get(0).getMeasuredWidth());
////                viewData.setHeight(autoGridView.getChildAt(i).getMeasuredHeight());
////                mViewDatas.set(i, viewData);
//
//        }


        ViewData viewData = mViewDatas.get(0);
        int[] location = new int[2];
        viewData.setX(location[0]);
        viewData.setY(location[1]);
        viewData.setWidth(500);
        viewData.setHeight(300);
        mViewDatas.set(0, viewData);


        imagePreivew.setStartPosition(position);
        imagePreivew.setImageData(mImageList);

        imagePreivew.setViewData(mViewDatas);
        imagePreivew.setImageZoomable(true);
        imagePreivew.doExitAnim(true);
        imagePreivew.doEnterAnim(true);


        imagePreivew.setOnImageChangedListener(new OnImageChangedListener() {
            @Override
            public void onImageSelected(int position, ImageView view) {


                if (isLong == false) {
                    int viewState = imagePreivew.getViewState();
                    float imageScale = imagePreivew.getImageScale();
                    Log.e("adfaf", "状态：" + viewState + "    imageScale缩放级别：" + imageScale);
                }


            }
        });


        imagePreivew.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(final int position, Object src, final ImageView view) {
                Glide.with(ImageActivity.this)
                        .load(src)
//                        .apply(mOptions)
                        .into(new SimpleTarget<Drawable>() {

                            @Override
                            public void onLoadStarted(@Nullable Drawable placeholder) {
                                super.onLoadStarted(placeholder);
                                view.setImageDrawable(placeholder);
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                view.setImageDrawable(errorDrawable);
                            }

                            @Override
                            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                view.setImageDrawable(resource);
                                mViewDatas.get(position).setImageWidth(resource.getIntrinsicWidth());
                                mViewDatas.get(position).setImageHeight(resource.getIntrinsicHeight());
                            }
                        });
            }
        });


        imagePreivew.setOnViewLongClickListener(new OnViewLongClickListener() {
            @Override
            public boolean onViewLongClick(int position, View view) {


                Log.e("adfaf", "onViewLongClick:");

                isLong = true;


                float y = view.getY();
                float translationY = view.getTranslationY();
                float pivotY = view.getPivotY();
                float rotationY = view.getRotationY();
                float scaleY = view.getScaleY();
                int scrollY = view.getScrollY();

                Log.e("adfaf", "onViewLongClick:" + "    Y:" + y + "    translationY:" + translationY);
                Log.e("adfaf", "onViewLongClick:" + "    pivotY:" + pivotY + "    rotationY:" + rotationY);
                Log.e("adfaf", "onViewLongClick:" + "    scaleY:" + scaleY + "    scrollY:" + scrollY);

                return false;
            }
        });

        imagePreivew.setOnWatchStatusListener(new OnWatchStatusListener() {
            @Override
            public void onWatchStart(int state, int position, ImageView view) {

                Log.e("adfaf", "onWatchStart:");


            }

            @Override
            public void onWatchDragging(ImageView view) {


                float y = view.getY();
                float translationY = view.getTranslationY();

                Log.e("adfaf", "onWatchDragging:" + "    Y:" + y);

//
//                if (y > 500) {
//                    ScaleAnimation scaleAnimation = startScaleAnimation(y);
//
//                    imagePreivew.startAnimation(scaleAnimation);
//                }


//                setImageParam(y, imagePreivew);


            }

            @Override
            public void onWatchReset(int state, ImageView view) {
                Log.e("adfaf", "onWatchReset:");

                imagePreivew.clearAnimation();
            }

            @Override
            public void onWatchEnd(int state) {
                Log.e("adfaf", "onWatchEnd:");
            }
        });


        imagePreivew.watch();


    }


    private void initView() {


        image = findViewById(R.id.image);
        imagePreivew = findViewById(R.id.imagePreivew);


        mImageList = DataUtil.getImageData();
        mViewDatas = new ArrayList<>();


        for (int i = 0, len = mImageList.size(); i < len; i++) {
            ViewData viewData = new ViewData();
            mViewDatas.add(viewData);
        }


        Glide.with(ImageActivity.this)
                .load("http://img5.duitang.com/uploads/item/201404/11/20140411214939_XswXa.jpeg")
//                .apply(mOptions)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                    }

                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        image.setImageDrawable(resource);
//                        mImageList.set(position, resource);
//                        mViewDatas.get(position).setImageWidth(resource.getIntrinsicWidth());
//                        mViewDatas.get(position).setImageHeight(resource.getIntrinsicHeight());
                    }
                });


    }


    public void setImageParam(float y, ImageViewer imagePreivew) {


        ViewGroup.LayoutParams layoutParams = imagePreivew.getLayoutParams();


        int width = layoutParams.width;
        int height = layoutParams.height;

        if (y > 100) {


            layoutParams.width = (int) (y / 3);
            layoutParams.height = (int) (y / 3);


        } else {

            layoutParams.width = width;
            layoutParams.height = height;


        }


        imagePreivew.setLayoutParams(layoutParams);
    }


    public ScaleAnimation startScaleAnimation(float y) {
        /**
         * ScaleAnimation第一种构造
         *
         * @param fromX X方向开始时的宽度，1f表示控件原有大小
         * @param toX X方向结束时的宽度，
         * @param fromY Y方向上开的宽度，
         * @param toY Y方向结束的宽度
         * 这里还有一个问题：缩放的中心在哪里？ 使用这种构造方法，默认是左上角的位置，以左上角为中心开始缩放
         */
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 2f, 1f, 2f);
        /**
         * ScaleAnimation第二种构造解决了第一种构造的缺陷， 无法指定缩放的位置
         *
         * @param fromX 同上
         * @param toX 同上
         * @param fromY 同上
         * @param toY 同上
         * @param pivotX 缩放的轴心X的位置，取值类型是float，单位是px像素，比如：X方向控件中心位置是mIvScale.getWidth() / 2f
         * @param pivotY 缩放的轴心Y的位置，取值类型是float，单位是px像素，比如：X方向控件中心位置是mIvScale.getHeight() / 2f
         */
//        ScaleAnimation scaleAnimation1 = new ScaleAnimation(1f, 2f, 1f, 2f, image.getWidth() / 2f, image.getHeight() / 2f);

        /**
         * ScaleAnimation第三种构造在第二种构造的基础上，可以通过多种方式指定轴心的位置，通过Type来约束
         *
         * @param fromX 同上
         * @param toX 同上
         * @param fromY 同上T
         * @param toY 同上
         * @param pivotXType 用来约束pivotXValue的取值。取值有三种：Animation.ABSOLUTE，Animation.RELATIVE_TO_SELF，Animation.RELATIVE_TO_PARENT
         * Type：Animation.ABSOLUTE：绝对，如果设置这种类型，后面pivotXValue取值就必须是像素点；比如：控件X方向上的中心点，pivotXValue的取值mIvScale.getWidth() / 2f
         *            Animation.RELATIVE_TO_SELF：相对于控件自己，设置这种类型，后面pivotXValue取值就会去拿这个取值是乘上控件本身的宽度；比如：控件X方向上的中心点，pivotXValue的取值0.5f
         *            Animation.RELATIVE_TO_PARENT：相对于它父容器（这个父容器是指包括这个这个做动画控件的外一层控件）， 原理同上，
         * @param pivotXValue  配合pivotXType使用，原理在上面
         * @param pivotYType 原理同上
         * @param pivotYValue 原理同上
         */
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1f, 2f, 1f, 2f, ScaleAnimation.ABSOLUTE, 0.5f, ScaleAnimation.ABSOLUTE, 0.5f);
        //设置动画持续时长
        scaleAnimation2.setDuration(3000);
        //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
        scaleAnimation2.setFillAfter(true);
        //设置动画结束之后的状态是否是动画开始时的状态，true，表示是保持动画开始时的状态
        scaleAnimation2.setFillBefore(true);
        //设置动画的重复模式：反转REVERSE和重新开始RESTART
        scaleAnimation2.setRepeatMode(ScaleAnimation.REVERSE);
        //设置动画播放次数
        scaleAnimation2.setRepeatCount(ScaleAnimation.INFINITE);


        return scaleAnimation2;
        //开始动画
        //清除动画
//        image.clearAnimation();
        //同样cancel（）也能取消掉动画
//        scaleAnimation2.cancel();
    }


}
