package com.pacerapps.wsjrss.rss_download;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.pacerapps.wsjrss.util.Constants.LIFESTYLE;
import static com.pacerapps.wsjrss.util.Constants.MARKETS_NEWS;
import static com.pacerapps.wsjrss.util.Constants.OPINION;
import static com.pacerapps.wsjrss.util.Constants.TECHNOLOGY;
import static com.pacerapps.wsjrss.util.Constants.U_S_BUSINESS;
import static com.pacerapps.wsjrss.util.Constants.WORLD_NEWS;

/**
 * Created by jeffwconaway on 10/2/16.
 */

class WsjXmlParser {

    private static final String RSS = "rss";
    private static final String CHANNEL = "channel";
    private static final String ITEM = "item";
    private static final String TITLE = "title";
    private static final String OPINION_STR = "Opinion";
    private static final String WORLD_NEWS_STR = "World News";
    private static final String U_S_BUSINESS_STR = "U.S. Business";
    private static final String MARKET_NEWS_STR = "Market News";
    private static final String TECHNOLOGY_WHAT_S_NEWS_STR = "Technology: What's News";
    private static final String LIFESTYLE_STR = "Lifestyle";
    private static String nameSpace = null;

    ArrayList<HeadlineItem> parseRssXml(InputStream inputStream, int itemType) throws XmlPullParserException, IOException {

        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(inputStream, null);
        parser.nextTag();
        return readFeed(parser, itemType);
    }

    private ArrayList<HeadlineItem> readFeed(XmlPullParser parser, int itemType) throws XmlPullParserException, IOException {
        ArrayList<HeadlineItem> headlineItems = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, nameSpace, RSS);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the channel tag
            if (name.equals(CHANNEL)) {
                headlineItems = readChannel(parser, itemType);
            } else {
                skipElement(parser);
            }
        }
        return headlineItems;

    }

    private ArrayList<HeadlineItem> readChannel(XmlPullParser parser, int itemType) throws XmlPullParserException, IOException {
        ArrayList<HeadlineItem> headlineItems = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, nameSpace, CHANNEL);

        // insert a Headline item to show the category of the following articles
        headlineItems.add(insertHeadlineCategory(itemType));

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the item tag
            if (name.equals(ITEM)) {
                headlineItems.add(readItem(parser, itemType));
            } else {
                skipElement(parser);
            }
        }
        return headlineItems;
    }

    private HeadlineItem readItem(XmlPullParser parser, int itemType) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, nameSpace, ITEM);
        String title = null;
        HeadlineItem headlineItem = new HeadlineItem(true);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TITLE)) {
                title = readTitle(parser);
                headlineItem.setHeadline(title);
            } else {
                skipElement(parser);
            }
        }
        headlineItem.setCategory(itemType);
        return headlineItem;
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, nameSpace, TITLE);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, TITLE);
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skipElement(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private HeadlineItem insertHeadlineCategory(int headlineType) {
        String category = "";
        switch (headlineType) {
            case OPINION:
                category = OPINION_STR;
                break;
            case WORLD_NEWS:
                category = WORLD_NEWS_STR;
                break;
            case U_S_BUSINESS:
                category = U_S_BUSINESS_STR;
                break;
            case MARKETS_NEWS:
                category = MARKET_NEWS_STR;
                break;
            case TECHNOLOGY:
                category = TECHNOLOGY_WHAT_S_NEWS_STR;
                break;
            case LIFESTYLE:
                category = LIFESTYLE_STR;
                break;
        }
        HeadlineItem headlineItem = new HeadlineItem(false);
        headlineItem.setHeadline(category);
        headlineItem.setCategory(headlineType);
        return headlineItem;
    }
}
