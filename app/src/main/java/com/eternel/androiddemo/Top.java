package com.eternel.androiddemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class Top extends RelativeLayout {

    private Scroller scroller;
    private FrameLayout.LayoutParams layoutParams;
    private int height;
    private int starty = 0;

    public Top(Context context) {
        this(context, null);
    }

    public Top(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Top(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                height = getHeight();
                layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                layoutParams.setMargins(0, -height, 0, 0);
                setLayoutParams(layoutParams);
            }
        });
        scroller = new Scroller(context, new DecelerateInterpolator());
    }


    public void scrollView(int startY, int endY) {
        if (starty <= 0 && (endY - startY) < 0) {
            return;
        }
        int dy = endY - startY;
        scroller.startScroll(0, starty, 0, dy);
        postInvalidateOnAnimation();
        starty = starty + dy;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            int currY = scroller.getCurrY();
            if (currY + 20 > height) {
                layoutParams.setMargins(0, 0, 0, 0);
            } else if (currY - 10 < 0) {
                layoutParams.setMargins(0, -height, 0, 0);
            } else {
                layoutParams.setMargins(0, currY - height, 0, 0);
            }
            setLayoutParams(layoutParams);
            postInvalidateOnAnimation();

        }
    }
}
