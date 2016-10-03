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

public class WsjXmlParser {

    private static String nameSpace = null;

    public ArrayList<HeadlineItem> parseRssXml(InputStream inputStream, int itemType) throws XmlPullParserException, IOException {

        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(inputStream, null);
        parser.nextTag();
        return readFeed(parser, itemType);
    }

    private ArrayList<HeadlineItem> readFeed(XmlPullParser parser, int itemType) throws XmlPullParserException, IOException {
        ArrayList<HeadlineItem> headlineItems = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, nameSpace, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the channel tag
            if (name.equals("channel")) {
                headlineItems = readChannel(parser, itemType);
            } else {
                skipElement(parser);
            }
        }
        return headlineItems;

    }

    private ArrayList<HeadlineItem> readChannel(XmlPullParser parser, int itemType) throws XmlPullParserException, IOException {
        ArrayList<HeadlineItem> headlineItems = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, nameSpace, "channel");

        // insert a Headline item to show the category of the following articles
        headlineItems.add(insertHeadlineCategory(itemType));

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the item tag
            if (name.equals("item")) {
                headlineItems.add(readItem(parser, itemType));
            } else {
                skipElement(parser);
            }
        }
        return headlineItems;
    }

    private HeadlineItem readItem(XmlPullParser parser, int itemType) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, nameSpace, "item");
        String title = null;
        HeadlineItem headlineItem = new HeadlineItem(true);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTitle(parser);
                headlineItem.setHeadline(title);
            } else {
                skipElement(parser);
            }
        }
        headlineItem.setHeadlineType(itemType);
        return headlineItem;
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, nameSpace, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, "title");
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
        String type = "";
        switch (headlineType) {
            case OPINION:
                type = "Opinion";
                break;
            case WORLD_NEWS:
                type = "World News";
                break;
            case U_S_BUSINESS:
                type = "U.S. Business";
                break;
            case MARKETS_NEWS:
                type = "Market News";
                break;
            case TECHNOLOGY:
                type = "Technology: What's News";
                break;
            case LIFESTYLE:
                type = "Lifestyle";
                break;
        }
        HeadlineItem headlineItem = new HeadlineItem(false);
        headlineItem.setHeadline(type);
        return headlineItem;
    }
}
