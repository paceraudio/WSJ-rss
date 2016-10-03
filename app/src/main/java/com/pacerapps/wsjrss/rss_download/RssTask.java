package com.pacerapps.wsjrss.rss_download;

import android.widget.ProgressBar;

import com.pacerapps.wsjrss.adapter.HeadlineItemAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by jeffwconaway on 10/1/16.
 */

 class RssTask {

    private RssDownloadRunnable rssDownloadRunnable;

    private ArrayList<HeadlineItem> rssHeadlinesArrayList;

    private WeakReference<ProgressBar> progressBarWeakReference;
    private WeakReference<HeadlineItemAdapter> headlineItemAdapterWeakReference;
    private int headlineType;

    RssTask(ProgressBar progressBar, HeadlineItemAdapter adapter, int headlineType) {
        rssDownloadRunnable = new RssDownloadRunnable(this);
        progressBarWeakReference = new WeakReference<>(progressBar);
        headlineItemAdapterWeakReference = new WeakReference<>(adapter);
        this.headlineType = headlineType;
    }

    int getHeadlineType() {
        return headlineType;
    }

    RssDownloadRunnable getRssDownloadRunnable() {
        return rssDownloadRunnable;
    }

    ProgressBar getProgressBarWeakReference() {
        if (progressBarWeakReference != null) {
            return progressBarWeakReference.get();
        }
        return null;
    }


    HeadlineItemAdapter getHeadlineItemAdapterWeakReference() {
        if (headlineItemAdapterWeakReference != null) {
            return headlineItemAdapterWeakReference.get();
        }
        return null;
    }


    ArrayList<HeadlineItem> getRssHeadlinesArrayList() {
        return rssHeadlinesArrayList;
    }

    void setRssHeadlinesArrayList(ArrayList<HeadlineItem> rssHeadlinesArrayList) {
        this.rssHeadlinesArrayList = rssHeadlinesArrayList;
    }

    void handleDownloadState(int state) {
        RssHeadlinesManager manager = RssHeadlinesManager.getInstance();
        manager.handleDownloadState(this, state);
    }
}
