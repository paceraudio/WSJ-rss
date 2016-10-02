package com.pacerapps.wsjrss.rss_download;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * Created by jeffwconaway on 10/1/16.
 */

public class RssHeadlinesManager {

    static final int RSS_DOWNLOAD_FAILED = -1;
    static final int RSS_DOWNLOAD_STARTED = 1;
    static final int RSS_DOWNLOAD_COMPLETE = 2;
    static final int RSS_ASK_COMPLETE = 3;

    Handler uiHandler;

    private static RssHeadlinesManager instance;
    //RssTask rssTask;

    private RssHeadlinesManager() {
        initHandler();
    }

    public static RssHeadlinesManager getInstance() {
        if (instance == null) {
            instance = new RssHeadlinesManager();
        }
        return instance;
    }

    private void initHandler() {
        uiHandler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                RssTask rssTask = (RssTask) msg.obj;

                switch (msg.what) {
                    case RSS_DOWNLOAD_STARTED:
                        ProgressBar progressBar = rssTask.getProgressBarWeakReference();

                        if (progressBar != null) {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                        break;

                    case RSS_DOWNLOAD_COMPLETE:
                        ArrayAdapter<String> arrayAdapter = rssTask.getArrayAdapterWeakReference();

                        if (arrayAdapter != null) {
                            arrayAdapter.addAll(rssTask.getRssHeadlinesArrayList());
                            arrayAdapter.notifyDataSetChanged();
                        }
                        break;
                }

            }
        };
    }

    public RssTask downloadRssHeadlines(ProgressBar progressBar, ArrayAdapter<String> arrayAdapter) {

    }
}
