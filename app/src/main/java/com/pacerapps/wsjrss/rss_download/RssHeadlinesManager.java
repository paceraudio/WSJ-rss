package com.pacerapps.wsjrss.rss_download;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;

import com.pacerapps.wsjrss.adapter.HeadlineItemAdapter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.pacerapps.wsjrss.util.Constants.LIFESTYLE;
import static com.pacerapps.wsjrss.util.Constants.MARKETS_NEWS;
import static com.pacerapps.wsjrss.util.Constants.OPINION;
import static com.pacerapps.wsjrss.util.Constants.RSS_DOWNLOAD_COMPLETE;
import static com.pacerapps.wsjrss.util.Constants.RSS_DOWNLOAD_STARTED;
import static com.pacerapps.wsjrss.util.Constants.TECHNOLOGY;
import static com.pacerapps.wsjrss.util.Constants.U_S_BUSINESS;
import static com.pacerapps.wsjrss.util.Constants.WORLD_NEWS;

/**
 * Created by jeffwconaway on 10/1/16.
 */

public class RssHeadlinesManager {

    Handler uiHandler;

    private static RssHeadlinesManager instance;

    private final BlockingQueue<Runnable> headlineDownloadQueue;
    private static final int KEEP_THREAD_ALIVE = 2;
    private static final TimeUnit KEEP_THREAD_ALIVE_UNIT = TimeUnit.SECONDS;
    private static final int CORES = Runtime.getRuntime().availableProcessors();
    ThreadPoolExecutor executor;

    int[] headlineTypesArray = {OPINION, WORLD_NEWS, U_S_BUSINESS, MARKETS_NEWS, TECHNOLOGY, LIFESTYLE};


    private RssHeadlinesManager() {
        initHandler();
        headlineDownloadQueue = new LinkedBlockingQueue<>();
        initExecutor();
    }

    public static RssHeadlinesManager getInstance() {
        if (instance == null) {
            instance = new RssHeadlinesManager();
        }
        return instance;
    }

    private void initExecutor() {
        executor = new ThreadPoolExecutor(CORES, CORES, KEEP_THREAD_ALIVE, KEEP_THREAD_ALIVE_UNIT, headlineDownloadQueue);
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
                            adapter.addItemsToAdapter(rssTask.getRssHeadlinesArrayList());
                        }

                        if (progressBar != null) {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
            }
        };
    }

    public RssTask downloadRssHeadlines(ProgressBar progressBar, HeadlineItemAdapter adapter) {
        for (int type : headlineTypesArray) {
            RssTask rssTask = new RssTask(progressBar, adapter, type);
            executor.execute(rssTask.getRssDownloadRunnable());
        }

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
