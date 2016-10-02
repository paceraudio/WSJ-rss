package com.pacerapps.wsjrss.rss_download;

import java.util.ArrayList;

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
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("This is from the Runnable!!");
        arrayList.add("It seems to be working!!");
        arrayList.add("But I bet this crashed several times before getting to this point!!!");

        rssTask.setRssHeadlinesArrayList(arrayList);
    }
}
