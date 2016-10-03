package com.pacerapps.wsjrss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pacerapps.wsjrss.R;
import com.pacerapps.wsjrss.rss_download.HeadlineItem;

import java.util.ArrayList;

/**
 * Created by jeffwconaway on 10/2/16.
 */

public class HeadlineItemAdapter extends BaseAdapter {

    ArrayList<HeadlineItem> headlineItems;
    Context context;

    public HeadlineItemAdapter(Context context) {
        this.context = context;
        headlineItems = new ArrayList<>();
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

        TextView songTextView = (TextView) convertView.findViewById(R.id.text_view_rss_item);
        songTextView.setText(headlineItem.getHeadline());

        return convertView;
    }
}
