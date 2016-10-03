package com.pacerapps.wsjrss.rss_download;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jeffwconaway on 10/2/16.
 */

public class HeadlineItem implements Parcelable {

    private static final String HEADLINE_TYPE_KEY = "category";
    private static final String HEADLINE_KEY = "headline";

    private int category;
    private String headline;

    // value for separating category items from headlines
    private boolean isHeadline;

    HeadlineItem(boolean isHeadline) {
        this.isHeadline = isHeadline;
    }

    public boolean isHeadline() {
        return isHeadline;
    }

    public int getCategory() {
        return category;
    }

    void setCategory(int category) {
        this.category = category;
    }

    public String getHeadline() {
        return headline;
    }

    void setHeadline(String headline) {
        this.headline = headline;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        Bundle bundle = new Bundle();
        bundle.putInt(HEADLINE_TYPE_KEY, category);
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
        category = bundle.getInt(HEADLINE_TYPE_KEY);
        headline = bundle.getString(HEADLINE_KEY);
    }

}
