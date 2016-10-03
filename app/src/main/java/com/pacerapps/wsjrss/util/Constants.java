package com.pacerapps.wsjrss.util;

/**
 * Created by jeffwconaway on 10/2/16.
 */

public class Constants {

    public static final String TAG = "WSJ_RSS";

    public static final int RSS_DOWNLOAD_FAILED = -1;
    public static final int RSS_DOWNLOAD_STARTED = 1;
    public static final int RSS_DOWNLOAD_COMPLETE = 2;

    public static final int OPINION = 0;
    public static final int WORLD_NEWS = 1;
    public static final int U_S_BUSINESS = 2;
    public static final int MARKETS_NEWS = 3;
    public static final int TECHNOLOGY = 4;
    public static final int LIFESTYLE = 5;

    public static final String OPINION_URL = "http://www.wsj.com/xml/rss/3_7041.xml";
    public static final String WORLD_NEWS_URL = "http://www.wsj.com/xml/rss/3_7085.xml";
    public static final String U_S_BUSINESS_URL = "http://www.wsj.com/xml/rss/3_7014.xml";
    public static final String MARKET_NEWS_URL = "http://www.wsj.com/xml/rss/3_7031.xml";
    public static final String TECHNOLOGY_URL = "http://www.wsj.com/xml/rss/3_7455.xml";
    public static final String LIFESTYLE_URL = "http://www.wsj.com/xml/rss/3_7201.xml";

}
