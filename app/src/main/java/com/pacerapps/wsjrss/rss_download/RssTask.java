package com.pacerapps.wsjrss.rss_download;

import android.widget.ProgressBar;

import com.pacerapps.wsjrss.adapter.HeadlineItemAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by jeffwconaway on 10/1/16.
 */

public class RssTask {

    private RssDownloadRunnable rssDownloadRunnable;

    private ArrayList<HeadlineItem> rssHeadlinesArrayList;

    private WeakReference<ProgressBar> progressBarWeakReference;
    private WeakReference<HeadlineItemAdapter> headlineItemAdapterWeakReference;
    private int headlineType;

    public RssTask(ProgressBar progressBar, HeadlineItemAdapter adapter, int headlineType) {
        rssDownloadRunnable = new RssDownloadRunnable(this);
        progressBarWeakReference = new WeakReference<>(progressBar);
        headlineItemAdapterWeakReference = new WeakReference<>(adapter);
        this.headlineType = headlineType;
    }

    public int getHeadlineType() {
        return headlineType;
    }

    public RssDownloadRunnable getRssDownloadRunnable() {
        return rssDownloadRunnable;
    }

    public ProgressBar getProgressBarWeakReference() {
        if (progressBarWeakReference != null) {
            return progressBarWeakReference.get();
        }
        return null;
    }


    public HeadlineItemAdapter getHeadlineItemAdapterWeakReference() {
        if (headlineItemAdapterWeakReference != null) {
            return headlineItemAdapterWeakReference.get();
        }
        return null;
    }


    public ArrayList<HeadlineItem> getRssHeadlinesArrayList() {
        return rssHeadlinesArrayList;
    }

    public void setRssHeadlinesArrayList(ArrayList<HeadlineItem> rssHeadlinesArrayList) {
        this.rssHeadlinesArrayList = rssHeadlinesArrayList;
    }

    public void handleDownloadState(int state) {
        RssHeadlinesManager manager = RssHeadlinesManager.getInstance();
        manager.handleDownloadState(this, state);
    }
}
