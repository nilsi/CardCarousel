package com.nicklasnilsson.cardcarousel;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.macbookpro.cardcarousel.R;
import com.nicklasnilsson.cardcarousel.page.PageFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Nicklas Nilsson on 18/01/2016.
 */
public class MainActivity extends FragmentActivity {
    private CardPagerAdapter<Card> pagerAdapter;
    private CardViewPager viewPager;
    private ArrayList<Card> items;
    private FixedSpeedScroller scroller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (savedInstanceState == null) {

            String colors[] = new String[7];
            String colors2[] = new String[7];

            colors[0] = "2D4CB4";
            colors[1] = "D147B6";
            colors[2] = "FF441F";
            colors[3] = "EDEDED";
            colors[4] = "3CDD4C";
            colors[5] = "ffff0a";
            colors[6] = "F19CBB";

            colors2[0] = "40569E";
            colors2[1] = "B861A6";
            colors2[2] = "F04E2F";
            colors2[3] = "C2C2C2";
            colors2[4] = "5ABC63";
            colors2[5] = "ffffaa";
            colors2[6] = "F19CBB";

            items = new ArrayList<>(colors.length);

            for (int i = 0; i < colors.length; i++) {
                items.add(new Card("Card " + i, colors2[i]));
            }

            if (items.size() == 7) {
                PagerSettings.setSettings7cards(this);
            }

        } else {
            items = savedInstanceState.getParcelableArrayList("items");
        }

        viewPager = (CardViewPager) findViewById(R.id.carousel_pager);

        pagerAdapter = new CardPagerAdapter<Card>(
                getSupportFragmentManager(),
                PageFragment.class,
                R.layout.page_layout,
                items,
                this);

        viewPager.setAdapter(pagerAdapter);

        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            scroller = new FixedSpeedScroller(viewPager.getContext());
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

        viewPager.jumpToCorrectStartpos();

        //Changing the scroll speed to something natural after moving initially scrolling down
        if (scroller != null) {
            final Handler handler = new Handler(); // Have to delay this for the ui to render before changing back speed
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scroller.setScrollDurationFactor(1.2);
                }
            }, 100);
        }

    }

    public int getViewPagerId() {
        return viewPager.getId();
    }

    public int getPageLimit() {
        return viewPager.getPageLimit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("items", items);
    }

}
