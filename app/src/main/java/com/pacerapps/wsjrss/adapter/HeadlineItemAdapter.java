package com.pacerapps.wsjrss.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pacerapps.wsjrss.R;
import com.pacerapps.wsjrss.rss_download.HeadlineItem;

import java.util.ArrayList;
import java.util.HashMap;

import static com.pacerapps.wsjrss.util.Constants.TAG;

/**
 * Created by jeffwconaway on 10/2/16.
 */

public class HeadlineItemAdapter extends BaseAdapter {

    ArrayList<HeadlineItem> headlineItems;
    Context context;

    /*This is used to track categories (Opinion, U.S. News, etc) that have already been downloaded
     * and added to the ListView.  The key will be the category type, and the value will be the
     * number of headlines (plus the category "header") that the category holds.  We can track
     * where the category of headlines should be inserted into the headlineItems ArrayList so that
     * we mirror the order of the different feeds online, and always have a consistent category
     * order in our ListView*/
    HashMap<Integer, Integer> orderingHashMap;

    public HeadlineItemAdapter(Context context) {
        this.context = context;
        headlineItems = new ArrayList<>();
        orderingHashMap = new HashMap<>();
    }

    public ArrayList<HeadlineItem> getHeadlineItems() {
        return headlineItems;
    }

    public void setHeadlineItems(ArrayList<HeadlineItem> headlineItems) {
        this.headlineItems = headlineItems;

    }

    @Override
    public int getCount() {
        return headlineItems.size();
    }

    @Override
    public HeadlineItem getItem(int position) {
        return headlineItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HeadlineItem headlineItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.rss_list_item, parent, false);
        }

        TextView headlineTextView = (TextView) convertView.findViewById(R.id.text_view_rss_item);
        headlineTextView.setText(headlineItem.getHeadline());
        if (!headlineItem.isHeadline()) {
            headlineTextView.setTextSize(24);
            headlineTextView.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
        } else {
            headlineTextView.setTextSize(16);
            headlineTextView.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        }

        return convertView;
    }

    public synchronized void clearAdapter() {
        headlineItems.clear();
        clearOrderingHashMap();
        notifyDataSetChanged();
    }

    public synchronized void addItemsToAdapter(ArrayList<HeadlineItem> items) {
        int category = items.get(0).getCategory();
        int insertionIndex = findInsertionIndex(category);

        /*safety - if something goes wrong, insert headlines at beginning of list.  The order of the
        categories will be lost, but we won't crash*/
        if (insertionIndex > headlineItems.size()) {
            insertionIndex = 0;
        }

        headlineItems.addAll(insertionIndex, items);
        updateOrderingHashMap(category, items.size());
        notifyDataSetChanged();
    }

    private synchronized int findInsertionIndex(int category) {
        int insertionIndex = 0;

        if (!orderingHashMap.isEmpty() && category != 0) {

            for (int i : orderingHashMap.keySet()) {

                if (i < category) {
                    // get the amount of headlines (plus the category "header") under that category
                    insertionIndex += orderingHashMap.get(i);
                }
            }
        }

        return insertionIndex;
    }

    private synchronized void updateOrderingHashMap(int category, int headlineSize) {
        orderingHashMap.put(category, headlineSize);
    }

    private synchronized void clearOrderingHashMap() {
        orderingHashMap.clear();
    }
}
