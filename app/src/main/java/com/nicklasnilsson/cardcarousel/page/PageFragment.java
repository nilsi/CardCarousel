package com.nicklasnilsson.cardcarousel.page;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nicklasnilsson.cardcarousel.Card;
import com.nicklasnilsson.cardcarousel.CardViewPager;
import com.nicklasnilsson.cardcarousel.PagerSettings;
import com.example.macbookpro.cardcarousel.R;

/**
 * Created by Nicklas Nilsson on 18/01/2016.
 */
public class PageFragment extends Fragment {

    private PageLayout pageLayout;
    private boolean startBottomPosition;

    public static Bundle createArgs(int pageLayoutId, Parcelable item) {
        Bundle args = new Bundle();
        args.putInt("page_layout_id", pageLayoutId);
        args.putParcelable("item", item);
        return args;
    }

    public void startBottomPosition(boolean startBottom) {
        this.startBottomPosition = startBottom;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        int pageLayoutId = getArguments().getInt("page_layout_id");
        pageLayout = (PageLayout) inflater.inflate(pageLayoutId, container, false);
        pageLayout.setRotation(90);

        Card item = getArguments().getParcelable("item");

        View pageContent = pageLayout.findViewById(R.id.page_content);

        //pageLayout.setBackgroundColor(Color.parseColor("#00" + item.getLayoutColor())); //00 transparent
        //pageContent.getBackground().setColorFilter(Color.parseColor("#" + item.getColor()), PorterDuff.Mode.SRC_ATOP);


        LayerDrawable bgDrawable = (LayerDrawable) pageContent.getBackground();
        final GradientDrawable shape = (GradientDrawable)   bgDrawable.findDrawableByLayerId(R.id.card_color);
        shape.setColor(Color.parseColor("#" + item.getColor()));

        TextView title = (TextView) pageContent.findViewById(R.id.card_name_text_view);
        title.setText(item.getTitle());

        if (pageContent != null) {
            pageContent.setOnTouchListener((CardViewPager) container);
            pageContent.setTag(item);
        }

        if (startBottomPosition) {
            setStartposAndSkew(PagerSettings.getSkew(), PagerSettings.getTranslateY());
        }

        return pageLayout;
    }


    public void setStartposAndSkew(float skew, int translate) {
        pageLayout.setTranslated(translate);
        pageLayout.setSkew(skew);
    }

}