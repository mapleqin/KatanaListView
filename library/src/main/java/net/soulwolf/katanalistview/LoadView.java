/*
 * Copyright (C) 2015 Leholady(乐活女人) Inc. All rights reserved.
 */
package net.soulwolf.katanalistview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * author: EwenQin
 * since : 16/5/14 下午1:32.
 */
public class LoadView extends ImageView {

    private static final String TAG = "LoadView";

    private AnimationDrawable mAnimationDrawable;

    public LoadView(Context context) {
        this(context, null);
    }

    public LoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Drawable drawable = getDrawable();
        if(drawable instanceof AnimationDrawable){
            this.mAnimationDrawable = (AnimationDrawable) drawable;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(mAnimationDrawable != null){
            mAnimationDrawable.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mAnimationDrawable != null){
            this.mAnimationDrawable.stop();
        }
    }
}
