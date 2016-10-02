package com.pacerapps.wsjrss;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class RssActivityFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView rssListView;
    private ArrayAdapter rssArrayAdapter;

    String fake = " f a k e ";
    public static final String TAG = RssActivityFragment.class.getSimpleName();

    public RssActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rss, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        rssListView = (ListView) getActivity().findViewById(R.id.list_view_rss);
        rssArrayAdapter = new ArrayAdapter(getContext(), R.layout.rss_list_item, R.id.text_view_rss_item);
        rssListView.setAdapter(rssArrayAdapter);
        rssListView.setOnItemClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        populateRssListView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick: " + position);
    }

    public void populateRssListView() {
        ArrayList<String> standIn = new ArrayList<>();
        standIn.add("Hjjpigs run wild!!!!");
        standIn.add("We don't know what to do!!!!");
        standIn.add(fake);
        rssArrayAdapter.addAll(standIn);
        rssArrayAdapter.notifyDataSetChanged();
    }

    public void launchRssTask() {

    }
}
