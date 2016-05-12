package com.nicklasnilsson.cardcarousel;

import android.content.Context;

import com.nicklasnilsson.cardcarousel.Dimensions;

/**
 * Created by Nicklas Nilsson on 18/01/2016.
 */
public class PagerSettings {

    private static float skew;
    private static int translateY;

    private static int cardWidth;
    private static int cardHeight;

    public static float getSkew() {
        return skew;
    }

    public static int getTranslateY() {
        return translateY;
    }

    public static int getCardWidth() {
        return cardWidth;
    }

    public static int getCardHeight() {
        return cardHeight;
    }

    public static void setSettings7cards(Context context) {
        translateY = Dimensions.convertDpToPixelInt(163f, context);
        cardWidth = Dimensions.convertDpToPixelInt(340f, context);
        cardHeight = Dimensions.convertDpToPixelInt(200f, context);
        skew = 4f;
    }

}
