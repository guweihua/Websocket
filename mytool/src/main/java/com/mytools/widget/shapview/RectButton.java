package com.quansu.widget.shapview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.quansu.widget.baseview.BaseButton;
import com.ysnows.quansu.R;

/**
 * Created by xianguangjin on 2017/3/4.
 */

public class RectButton extends BaseButton {


    private float radius;
    private RectF rectF;

    public RectButton(Context context) {
        this(context, null);
    }

    public RectButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.RectView);
        radius = attributes.getDimension(R.styleable.RectView_rv_radius, 0f);

        attributes.recycle();
    }

    @Override
    public void draw(Canvas canvas) {
        Path clipPath = new Path();
        rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());

        clipPath.addRoundRect(rectF, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);

        super.draw(canvas);
    }

}
