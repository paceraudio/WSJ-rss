package com.pacerapps.wsjrss.rss_download;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import static com.pacerapps.wsjrss.util.Constants.*;

/**
 * Created by jeffwconaway on 10/1/16.
 */

public class RssHeadlinesManager {

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
                ProgressBar progressBar = rssTask.getProgressBarWeakReference();

                switch (msg.what) {
                    case RSS_DOWNLOAD_STARTED:

                        if (progressBar != null) {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                        break;

                    case RSS_DOWNLOAD_COMPLETE:
                        ArrayAdapter<HeadlineItem> arrayAdapter = rssTask.getArrayAdapterWeakReference();

                        if (arrayAdapter != null) {
                            arrayAdapter.clear();
                            arrayAdapter.addAll(rssTask.getRssHeadlinesArrayList());
                            arrayAdapter.notifyDataSetChanged();
                        }
                        if (progressBar != null) {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        break;
                }

            }
        };
    }

    public RssTask downloadRssHeadlines(ProgressBar progressBar, ArrayAdapter<HeadlineItem> arrayAdapter) {
        RssTask rssTask = new RssTask(progressBar, arrayAdapter);
        rssTask.beginRssDownload();
        return null;
    }

    public void handleDownloadState(RssTask rssTask, int state) {

        switch (state) {
            case RSS_DOWNLOAD_STARTED:
                Message startedMessage = uiHandler.obtainMessage(state, rssTask);
                startedMessage.setTarget(uiHandler);
                startedMessage.sendToTarget();
                break;

            case RSS_DOWNLOAD_COMPLETE:
                Message completedMessage = uiHandler.obtainMessage(state, rssTask);
                completedMessage.setTarget(uiHandler);
                completedMessage.sendToTarget();
                break;
        }
    }
}
