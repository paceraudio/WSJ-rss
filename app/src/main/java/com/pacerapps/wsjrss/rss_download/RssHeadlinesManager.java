package com.pacerapps.wsjrss.rss_download;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.pacerapps.wsjrss.adapter.HeadlineItemAdapter;

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
                        HeadlineItemAdapter adapter = rssTask.getHeadlineItemAdapterWeakReference();

                        if (adapter != null) {
                            adapter.getHeadlineItems().clear();
                            adapter.getHeadlineItems().addAll(rssTask.getRssHeadlinesArrayList());
                            adapter.notifyDataSetChanged();
                        }
                        if (progressBar != null) {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        break;
                }

            }
        };
    }

    public RssTask downloadRssHeadlines(ProgressBar progressBar, HeadlineItemAdapter adapter /*ArrayAdapter<HeadlineItem> arrayAdapter*/) {
        RssTask rssTask = new RssTask(progressBar, adapter);
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
