package com.nicklasnilsson.cardcarousel;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by Nicklas Nilsson on 18/01/2016.
 */
public class FixedSpeedScroller extends Scroller {
    private double mScrollFactor = 0;

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    /**
     * Set the factor by which the scroll will change on releaseing the finger
     */
    public void setScrollDurationFactor(double scrollFactor) {
        mScrollFactor = scrollFactor;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy,
                            int duration) {
        super.startScroll(startX, startY, dx, dy,
                (int) (duration * mScrollFactor));
    }
}
