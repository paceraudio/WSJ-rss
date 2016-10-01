package com.pacerapps.wsjrss;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by jeffwconaway on 10/1/16.
 */

public class RssListAdapter extends ArrayAdapter<String> {


    public RssListAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }
}
