package com.pacerapps.wsjrss.rss_download;

import android.util.Log;

import com.pacerapps.wsjrss.util.Constants;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.pacerapps.wsjrss.util.Constants.*;

/**
 * Created by jeffwconaway on 10/1/16.
 */

public class RssDownloadRunnable implements Runnable {

    RssTask rssTask;

    public RssDownloadRunnable(RssTask rssTask) {
        this.rssTask = rssTask;
    }

    /*@Override
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
    }*/

    @Override
    public void run() {
        rssTask.handleDownloadState(RSS_DOWNLOAD_STARTED);

        try {
            ArrayList<HeadlineItem> headlineItems = downloadRssHeadlines(OPINION);
            if (headlineItems != null) {
                rssTask.setRssHeadlinesArrayList(headlineItems);
            }
            rssTask.handleDownloadState(RSS_DOWNLOAD_COMPLETE);
        } catch (IOException e) {
            Log.e(TAG, "RssDownloadRunnable run: ", e);
        }

    }

    private ArrayList<HeadlineItem> downloadRssHeadlines(int headlineType) throws IOException {
        InputStream inputStream = null;
        ArrayList<HeadlineItem> headlineItems = null;

        try {
            URL url = obtainUrlByType(headlineType);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(TAG, "downloadRssHeadlines: response code: " + response);
            inputStream = connection.getInputStream();
            //String inputStr = inputStreamToString(inputStream, 100000);
            //Log.d(TAG, "downloadRssHeadlines: " + inputStr);
            WsjXmlParser parser = new WsjXmlParser();

            try {
                 headlineItems = parser.parseRssXml(inputStream, headlineType);
                for (HeadlineItem item : headlineItems) {
                    Log.d(TAG, "downloadRssHeadlines: headline: " + item.getHeadline());
                }
                Log.d(TAG, "downloadRssHeadlines: headlineItems: " );
            } catch (XmlPullParserException e) {
                Log.e(TAG, "downloadRssHeadlines: ", e);
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

        }
        return new URL(urlStr);
    }

    public String inputStreamToString(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    /*private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 *//* milliseconds *//*);
            conn.setConnectTimeout(15000 *//* milliseconds *//*);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }*/
}
