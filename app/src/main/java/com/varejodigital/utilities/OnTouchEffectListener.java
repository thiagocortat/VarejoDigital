package com.varejodigital.utilities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

/**
 * Special OnTouchListener that applies some visual effects when user taps the view
 *
 * @author rtoshiro
 * @version 2015.0602
 */
public class OnTouchEffectListener implements View.OnTouchListener {

    public final static int DEFAULT_DARKFACTOR = 20;

    public interface OnTouchEffectCallback {
        boolean onSingleTap(View v, MotionEvent event);
    }

    protected int defaultColor;
    protected int darkFactor;
    protected Context context;
    protected OnTouchEffectCallback callback;

    public OnTouchEffectListener(Context context) {
        super();
        this.context = context;
        this.defaultColor = Integer.MIN_VALUE;
        this.darkFactor = DEFAULT_DARKFACTOR;
    }

    /**
     * Constructor
     *
     * @param context    View's context
     * @param darkFactor Factor of darkness from 0 to 100. 90 is darket than 10. Default is OnTouchEffectListener.DEFAULT_DARKFACTOR.
     */
    public OnTouchEffectListener(Context context, int darkFactor) {
        super();
        this.context = context;
        this.defaultColor = Integer.MIN_VALUE;
        this.darkFactor = darkFactor;
    }

    /**
     * Constructor
     *
     * @param context    View's context
     * @param darkFactor Factor of darkness from 0 to 100. 90 is darket than 10. Default is OnTouchEffectListener.DEFAULT_DARKFACTOR.
     * @param callback   Callback method to be called after onTouch event
     */
    public OnTouchEffectListener(Context context, int darkFactor, OnTouchEffectCallback callback) {
        super();
        this.context = context;
        this.defaultColor = Integer.MIN_VALUE;
        this.darkFactor = darkFactor;
        this.callback = callback;
    }

    private int darker(int color, float factor) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        return Color.argb(a,
                Math.max((int) (r * factor), 0),
                Math.max((int) (g * factor), 0),
                Math.max((int) (b * factor), 0));
    }

    private void showDefaultColor(View v) {
        Drawable background = v.getBackground();
        if (background instanceof ColorDrawable) {
            v.setBackgroundColor(defaultColor);
        } else {
            v.getBackground().clearColorFilter();
        }
    }

    private void showTouchedColor(View v) {
        // ColorDrawable.setColorFilter() only works in LOLLIPOP
        Drawable background = v.getBackground();
        if (background instanceof ColorDrawable) {
            if (defaultColor == Integer.MIN_VALUE)
                defaultColor = ((ColorDrawable) background).getColor();

            int color = darker(defaultColor, ((100 - darkFactor) / 100.f));
            v.setBackgroundColor(color);
        } else {
            int color = darker(0xffffff, ((100 - darkFactor) / 100.f));
            ColorFilter filter = new LightingColorFilter(color, 0);
            v.getBackground().setColorFilter(filter);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                showTouchedColor(v);

                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                Rect viewRect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                if (!viewRect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())) {
                    showDefaultColor(v);
                } else {
                    showTouchedColor(v);
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                showDefaultColor(v);
                break;
            }
            case MotionEvent.ACTION_UP: {
                showDefaultColor(v);

                if (callback != null) {
                    Rect viewRect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                    if (viewRect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())) {
                        callback.onSingleTap(v, event);
                    }
                }

                break;
            }
        }

        return false;
    }
}

