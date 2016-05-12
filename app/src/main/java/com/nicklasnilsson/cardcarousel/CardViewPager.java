package com.nicklasnilsson.cardcarousel;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

/**
 * Created by Nicklas Nilsson on 18/01/2016.
 */
public class CardViewPager extends ViewPager implements OnTouchListener {

    private Resources resources;

    private final String packageName;
    private int pageLimit = 160;

    public CardViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        resources = context.getResources();
        packageName = context.getPackageName();

    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter == null) {
            return;
        }

        setOnPageChangeListener((OnPageChangeListener) adapter);

        setCurrentItem(((CardPagerAdapter) adapter).getFirstPosition());
    }

    private long lastTouchDown;
    private static int CLICK_ACTION_THRESHHOLD = 200;


    @Override
    public boolean onTouch(View view, MotionEvent event) {

        int X_LEFT_MARGIN = 0;
        int X = (int) event.getRawX() + X_LEFT_MARGIN;
        int Y = (int) event.getRawY();

        if (X < PagerSettings.getCardWidth() && Y < PagerSettings.getCardHeight()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastTouchDown = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_UP:
                    if (System.currentTimeMillis() - lastTouchDown < CLICK_ACTION_THRESHHOLD) {
                        Toast.makeText(getContext(), "Open settings for card!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

        return false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);

        setRotation(-90);

        float centerPager = (width - height) * 0.5f; // Moves viewpager to right position after rotating.
        setTranslationX(centerPager); //Investigate this
        setTranslationY(-centerPager);

        setOffscreenPageLimit(pageLimit);

        setPageMargin(-Dimensions.convertDpToPixelInt(845, getContext()));

        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
    }

    public int getPageLimit(){
        return pageLimit;
    }

    public void jumpToCorrectStartpos() {
        post(new Runnable() {
            @Override
            public void run() {
                setCurrentItem(((CardPagerAdapter) getAdapter()).getFirstPosition() - 80);
            }
        });
    }
}
