package com.pacerapps.wsjrss.rss_download;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by jeffwconaway on 10/1/16.
 */

public class RssTask {

    RssDownloadRunnable rssDownloadRunnable;

    ArrayList<String> rssHeadlinesArrayList;

    WeakReference<ListView> listViewWeakReference;
    WeakReference<ProgressBar> progressBarWeakReference;
    WeakReference<ArrayAdapter<String>> arrayAdapterWeakReference;

    public RssTask() {
        rssDownloadRunnable = new RssDownloadRunnable(this);
    }

    public ListView getListViewWeakReference() {
        if (listViewWeakReference != null) {
            return listViewWeakReference.get();
        }
        return null;
    }

    public ProgressBar getProgressBarWeakReference() {
        if (progressBarWeakReference != null) {
            return progressBarWeakReference.get();
        }
        return null;
    }

    public ArrayAdapter<String> getArrayAdapterWeakReference() {
        if (arrayAdapterWeakReference != null) {
            return arrayAdapterWeakReference.get();
        }
        return null;
    }

    public ArrayList<String> getRssHeadlinesArrayList() {
        return rssHeadlinesArrayList;
    }

    public void setRssHeadlinesArrayList(ArrayList<String> rssHeadlinesArrayList) {
        this.rssHeadlinesArrayList = rssHeadlinesArrayList;
    }
}
