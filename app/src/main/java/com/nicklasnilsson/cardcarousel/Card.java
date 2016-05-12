package com.nicklasnilsson.cardcarousel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nicklas Nilsson on 18/01/2016.
 */
public class Card implements Parcelable {
    private String title;
    private String color;

    public Card(String title, String cardColor) {
        this.title = title;
        this.color = cardColor;
    }

    private Card(Parcel in) {
        title = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public static final Creator<Card> CREATOR =
            new Creator<Card>() {
                @Override
                public Card createFromParcel(Parcel in) {
                    return new Card(in);
                }

                @Override
                public Card[] newArray(int size) {
                    return new Card[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

