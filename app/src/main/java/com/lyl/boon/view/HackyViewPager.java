package com.lyl.boon.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 解决 PhotoView 缩小的时候 程序会挂掉。
 * Wing_Li
 * 2016/4/15.
 */
public class HackyViewPager extends ViewPager {

    public HackyViewPager(Context context) {
        super( context );
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super( context, attrs );
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent( ev );
        } catch (IllegalArgumentException e) {
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return false;
    }

}
