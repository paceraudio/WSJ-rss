package com.pacerapps.wsjrss.rss_download;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jeffwconaway on 10/2/16.
 */

public class HeadlineItem implements Parcelable {

    private static final String HEADLINE_TYPE_KEY = "headlineType";
    private static final String HEADLINE_ORDER_KEY = "headlineOrder";
    private static final String HEADLINE_KEY = "headline";
    private int headlineType;
    private int headlineOrder;
    private String headline;
    String description;

    public HeadlineItem() {

    }

    public int getHeadlineType() {
        return headlineType;
    }

    public void setHeadlineType(int headlineType) {
        this.headlineType = headlineType;
    }

    public int getHeadlineOrder() {
        return headlineOrder;
    }

    public void setHeadlineOrder(int headlineOrder) {
        this.headlineOrder = headlineOrder;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

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

    public static final Parcelable.Creator<HeadlineItem> CREATOR
            = new Parcelable.Creator<HeadlineItem>() {
        @Override
        public HeadlineItem createFromParcel(Parcel in) {
            return new HeadlineItem(in);
        }

        @Override
        public HeadlineItem[] newArray(int size) {
            return new HeadlineItem[size];
        }
    };

    private HeadlineItem(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        headlineType = bundle.getInt(HEADLINE_TYPE_KEY);
        headlineOrder = bundle.getInt(HEADLINE_ORDER_KEY);
        headline = bundle.getString(HEADLINE_KEY);
    }

}
