package com.pacerapps.wsjrss.rss_download;

import android.util.Log;

import com.pacerapps.wsjrss.util.Constants;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.pacerapps.wsjrss.util.Constants.RSS_DOWNLOAD_COMPLETE;
import static com.pacerapps.wsjrss.util.Constants.RSS_DOWNLOAD_FAILED;
import static com.pacerapps.wsjrss.util.Constants.RSS_DOWNLOAD_STARTED;
import static com.pacerapps.wsjrss.util.Constants.TAG;

/**
 * Created by jeffwconaway on 10/1/16.
 */

public class RssDownloadRunnable implements Runnable {

    public static final int TIMEOUT_MILLIS = 10000;
    public static final int CONNECT_TIMEOUT_MILLIS = 15000;
    public static final String GET = "GET";
    RssTask rssTask;

    public RssDownloadRunnable(RssTask rssTask) {
        this.rssTask = rssTask;
    }

    @Override
    public void run() {
        rssTask.handleDownloadState(RSS_DOWNLOAD_STARTED);

        try {
            ArrayList<HeadlineItem> headlineItems = downloadRssHeadlines(rssTask.getHeadlineCategory());
            if (headlineItems != null) {
                rssTask.setRssHeadlinesArrayList(headlineItems);
                rssTask.handleDownloadState(RSS_DOWNLOAD_COMPLETE);
            }

        } catch (IOException e) {
            Log.e(TAG, "RssDownloadRunnable run: ", e);
            rssTask.handleDownloadState(RSS_DOWNLOAD_FAILED);
        }

    }

    private ArrayList<HeadlineItem> downloadRssHeadlines(int headlineType) throws IOException {
        InputStream inputStream = null;
        ArrayList<HeadlineItem> headlineItems = null;

        try {
            URL url = obtainUrlByType(headlineType);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(TIMEOUT_MILLIS);
            connection.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
            connection.setRequestMethod(GET);
            connection.setDoInput(true);
            connection.connect();
            int response = connection.getResponseCode();


            if (response >= 400) {
                rssTask.handleDownloadState(RSS_DOWNLOAD_FAILED);
                Log.d(TAG, "downloadRssHeadlines: bad response code!!! " + response);
                return null;
            }

            inputStream = connection.getInputStream();
            WsjXmlParser parser = new WsjXmlParser();

            try {
                 headlineItems = parser.parseRssXml(inputStream, headlineType);

            } catch (XmlPullParserException e) {
                Log.e(TAG, "downloadRssHeadlines: ", e);
                rssTask.handleDownloadState(RSS_DOWNLOAD_FAILED);
                return null;
            }

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return headlineItems;
    }

    private URL obtainUrlByType(int headlineType) throws MalformedURLException {
        String urlStr = "";
        switch (headlineType) {
            case 0:
                urlStr = Constants.OPINION_URL;
                break;
            case 1:
                urlStr = Constants.WORLD_NEWS_URL;
                break;
            case 2:
                urlStr = Constants.U_S_BUSINESS_URL;
                break;
            case 3:
                urlStr = Constants.MARKET_NEWS_URL;
                break;
            case 4:
                urlStr = Constants.TECHNOLOGY_URL;
                break;
            case 5:
                urlStr = Constants.LIFESTYLE_URL;
        }
        return new URL(urlStr);
    }
}
