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

    private RssDownloadRunnable rssDownloadRunnable;
    private Thread executingThread;

    private ArrayList<String> rssHeadlinesArrayList;

    private WeakReference<ListView> listViewWeakReference;
    private WeakReference<ProgressBar> progressBarWeakReference;
    private WeakReference<ArrayAdapter<String>> arrayAdapterWeakReference;

    public RssTask(ProgressBar progressBar, ArrayAdapter<String> arrayAdapter) {
        rssDownloadRunnable = new RssDownloadRunnable(this);
        progressBarWeakReference = new WeakReference<>(progressBar);
        arrayAdapterWeakReference = new WeakReference<>(arrayAdapter);
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

    public void beginRssDownload() {
        Thread thread = new Thread(rssDownloadRunnable);
        executingThread = thread;
        thread.start();
    }

    public void handleDownloadState(int state) {
        RssHeadlinesManager manager = RssHeadlinesManager.getInstance();
        manager.handleDownloadState(this, state);
    }
}
