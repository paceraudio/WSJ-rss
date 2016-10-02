package com.pacerapps.wsjrss.rss_download;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jeffwconaway on 10/2/16.
 */

public class HeadlineItem implements Parcelable
{

    public static final String HEADLINE_TYPE_KEY = "headlineType";
    public static final String HEADLINE_ORDER_KEY = "headlineOrder";
    public static final String HEADLINE_KEY = "headline";
    int headlineType;
    int headlineOrder;
    String headline;
    String description;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        Bundle bundle = new Bundle();
        bundle.putInt(HEADLINE_TYPE_KEY, headlineType);
        bundle.putInt(HEADLINE_ORDER_KEY, headlineOrder);
        bundle.putString(HEADLINE_KEY, headline);
    }

    public static final Parcelable.Creator<HeadlineItem> HEADLINE_ITEM_CREATOR
            = new Parcelable.Creator<HeadlineItem>() {
        @Override
        public HeadlineItem createFromParcel(Parcel in) {
            return new HeadlineItem(in);
        }

        @Override
        public HeadlineItem[] newArray(int size) {
            return new HeadlineItem[0];
        }
    };

    private HeadlineItem(Parcel in) {
        Bundle bundle = in.readBundle();
        headlineType = bundle.getInt(HEADLINE_TYPE_KEY);
        headlineOrder = bundle.getInt(HEADLINE_ORDER_KEY);
        headline = bundle.getString(HEADLINE_KEY);
    }

}
