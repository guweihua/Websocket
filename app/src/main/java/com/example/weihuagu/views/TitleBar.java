package com.example.weihuagu.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weihuagu.utils.UiUtils;
import com.example.weihuagu.websocket.R;

public class TitleBar extends LinearLayout {

    private BaseView view;

    private Toolbar toolbar;
    private ImageView imgLeft;
    private TextView tvLeft;
    private TextView tvTitle;
    private ImageView imgRight;
    private ImageView imgRightLeft;
    private TextView tvRight;

    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int i) {

        inflate(context, R.layout.widget_title_bar, this);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        int style = typedArray.getInt(R.styleable.TitleBar_title_bar_style, 0);
        String title = typedArray.getString(R.styleable.TitleBar_title_bar_title);
        String rightText = typedArray.getString(R.styleable.TitleBar_title_bar_right_text);
        String leftText = typedArray.getString(R.styleable.TitleBar_title_bar_left_text);
        Drawable rightDrawable = typedArray.getDrawable(R.styleable.TitleBar_title_bar_right_img);
        Drawable leftDrawable = typedArray.getDrawable(R.styleable.TitleBar_title_bar_left_img);
        Drawable rightLeftDrawable = typedArray.getDrawable(R.styleable.TitleBar_title_bar_right_left_img);
        boolean leftImgEnabled = typedArray.getBoolean(R.styleable.TitleBar_title_bar_left_img_enabled, true);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgLeft = (ImageView) findViewById(R.id.img_left);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        imgRight = (ImageView) findViewById(R.id.img_right);
        imgRightLeft = (ImageView) findViewById(R.id.img_right_left);
        tvRight = (TextView) findViewById(R.id.tv_right);


        if (style == 0) {
            setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else if (style == 1) {
            setBackgroundColor(context.getResources().getColor(R.color.white));
            int statusBarHeight = UiUtils.getStatusBarHeight(getContext());
            setPadding(0, statusBarHeight, 0, 0);
        } else if (style == 2) {
            setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        setLeftImgEnabled(leftImgEnabled);

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(VISIBLE);
            setTitle(title);
        }

        if (!TextUtils.isEmpty(rightText)) {
            setRightText(rightText);
        }

        if (!TextUtils.isEmpty(leftText)) {
            setLeftText(leftText);
        }

        if (rightDrawable != null) {
            setRitghtImg(rightDrawable);
        }
        if (leftDrawable != null) {
            setLeftImg(leftDrawable);
        }
        if (rightLeftDrawable != null) {
            setRitghtLeftImg(rightLeftDrawable);
        }

        imgLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() != null) {

                    ((Activity) getContext()).finish();

                }
            }
        });

        typedArray.recycle();

    }

    /**
     * 设置右边的图片，并显示出来
     *
     * @param drawable
     */
    public void setRitghtImg(Drawable drawable) {
        imgRight.setVisibility(VISIBLE);
        imgRight.setImageDrawable(drawable);
    }


    /**
     * 隐藏右边图片
     *
     * @param
     */
    public void setRitghtImgHide() {
        imgRight.setVisibility(GONE);
    }

    /**
     * 设置左边的图片，并显示出来
     *
     * @param drawable
     */
    public void setLeftImg(Drawable drawable) {
        imgLeft.setVisibility(VISIBLE);
        imgLeft.setImageDrawable(drawable);
    }

    /**
     * 设置右边靠左的图片，并显示出来
     *
     * @param drawable
     */
    public void setRitghtLeftImg(Drawable drawable) {
        imgRightLeft.setVisibility(VISIBLE);
        imgRightLeft.setImageDrawable(drawable);
    }

    /**
     * 设置右边textview的文字，并显示出来
     *
     * @param rightText
     */
    public void setRightText(String rightText) {
        tvRight.setVisibility(VISIBLE);
        tvRight.setText(rightText);
    }

    /**
     * 设置左边textview的文字，并显示出来
     *
     * @param leftText
     */
    public void setLeftText(String leftText) {
        tvLeft.setVisibility(VISIBLE);
        tvLeft.setText(leftText);
    }


    public TextView getTvRight() {
        return tvRight;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setTitle(int ResId) {
        tvTitle.setText(getContext().getString(ResId));
    }

    public ImageView getImgLeft() {
        return imgLeft;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public ImageView getImgRight() {
        return imgRight;
    }

    public ImageView getImgRightLeft() {
        return imgRightLeft;
    }


    public void setView(BaseView view) {
        this.view = view;
    }

    /**
     * 左边的图片是否显示
     *
     * @param isEnabled
     */
    public void setLeftImgEnabled(boolean isEnabled) {
        this.imgLeft.setVisibility(isEnabled ? VISIBLE : GONE);
    }


}
