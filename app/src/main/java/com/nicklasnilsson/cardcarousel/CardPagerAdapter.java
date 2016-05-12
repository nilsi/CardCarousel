package com.nicklasnilsson.cardcarousel;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.ViewGroup;

import com.nicklasnilsson.cardcarousel.page.PageFragment;
import com.nicklasnilsson.cardcarousel.page.PageLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicklas Nilsson on 18/01/2016.
 */
public class CardPagerAdapter<T extends Parcelable> extends FragmentPagerAdapter
        implements OnPageChangeListener {
    private static final String TAG = CardViewPager.class.getSimpleName();
    private int pagesCount;
    private int firstPosition;

    private FragmentManager fragmentManager;
    private List<T> items;

    private Class pageFragmentClass;
    private int pageLayoutId;

    private int createdFragments = 0;

    private MainActivity activity;

    public CardPagerAdapter(FragmentManager fragmentManager,
                            Class pageFragmentClass,
                            int pageLayoutId,
                            List<T> items,
                            MainActivity activity) {
        super(fragmentManager);


        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.pageFragmentClass = pageFragmentClass;
        this.pageLayoutId = pageLayoutId;

        if (items == null) {
            this.items = new ArrayList<T>(0);
        } else {
            this.items = items;
        }

        pagesCount = this.items.size();

        firstPosition = pagesCount * 1000 / 2; //Start in the middle when opening app

    }

    @Override
    public Fragment getItem(int position) {

        position = position % pagesCount;

        try {
            PageFragment pf = (PageFragment) pageFragmentClass.newInstance();
            pf.setArguments(PageFragment.createArgs(pageLayoutId, items.get(position)));

            if (createdFragments > 80) {
                pf.startBottomPosition(true);
            }

            createdFragments++;
            return pf;

        } catch (IllegalAccessException e) {
            Log.w(TAG, e.getMessage());
        } catch (InstantiationException e) {
            Log.w(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    public int getCount() {
        return pagesCount * 1000; //pageCount * Loop
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {

        PageLayout current = getPageView(position);

        PageLayout next = getPageView(position + 1);
        PageLayout nextnext = getPageView(position + 2);
        PageLayout nextnextnext = getPageView(position + 3);

        PageLayout prev = getPageView(position - 1);
        PageLayout prevprev = getPageView(position - 2);
        PageLayout prevprevprev = getPageView(position - 3);
        PageLayout prevprevprevprev = getPageView(position - 4);

        /*if (positionOffset >= 0.5f) { //Example on how to play around with the cards current offset
            next.bringToFront();
        } else {
            current.bringToFront();
        }*/


        if (current != null) {
            current.setSkew(PagerSettings.getSkew() * positionOffset);
            current.setTranslated(PagerSettings.getTranslateY() * positionOffset);
        }

        // Making sure all cards appearing on the screen are correctly skewed and translated - should factor this out

        if (next != null) {
            next.setTranslated(0);
            next.setSkew(0);
        }

        if (nextnext != null) {
            nextnext.setTranslated(0);
            nextnext.setSkew(0);
        }

        if (nextnextnext != null) {
            nextnextnext.setTranslated(0);
            nextnextnext.setSkew(0);
        }

        if (prev != null) {
            prev.setTranslated(PagerSettings.getTranslateY());
            prev.setSkew(4f);
        }

        if (prevprev != null) {
            prevprev.setTranslated(PagerSettings.getTranslateY());
            prevprev.setSkew(4f);
        }

        if (prevprevprev != null) {
            prevprevprev.setTranslated(PagerSettings.getTranslateY());
            prevprevprev.setSkew(4f);
        }

        if (prevprevprevprev != null) {
            prevprevprevprev.setTranslated(PagerSettings.getTranslateY());
            prevprevprevprev.setSkew(4f);
        }

    }

    @Override
    public void onPageSelected(int position) {}

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == CardViewPager.SCROLL_STATE_IDLE) {
            if (activity.getPageLimit() == 0) {
                return;
            }
        }
    }

    @Override
    public float getPageWidth(int position) {
        return 1f;
    }

    public int getFirstPosition() {
        return firstPosition;
    }

    private PageLayout getPageView(int position) {
        Fragment f = fragmentManager.findFragmentByTag("android:switcher:" + activity.getViewPagerId() + ":" + position);
        if (f != null && f.getView() != null) {
            return (PageLayout) f.getView();
        } else {
            return null;
        }
    }

}