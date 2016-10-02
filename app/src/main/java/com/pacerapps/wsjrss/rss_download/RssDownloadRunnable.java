package com.pacerapps.wsjrss.rss_download;

import android.util.Log;

import java.util.ArrayList;

import static com.pacerapps.wsjrss.util.Constants.*;

/**
 * Created by jeffwconaway on 10/1/16.
 */

public class RssDownloadRunnable implements Runnable {

    RssTask rssTask;

    public RssDownloadRunnable(RssTask rssTask) {
        this.rssTask = rssTask;
    }

    @Override
    public void run() {
        rssTask.handleDownloadState(RSS_DOWNLOAD_STARTED);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Log.e(TAG, "RssDownloadRunnable run: ", e);
        }

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("This is from the Runnable!!");
        arrayList.add("It seems to be working!!");
        arrayList.add("This is from the Runnable!!");
        arrayList.add("This is from the Runnable!!");

        rssTask.setRssHeadlinesArrayList(arrayList);

        rssTask.handleDownloadState(RSS_DOWNLOAD_COMPLETE);
    }
}
