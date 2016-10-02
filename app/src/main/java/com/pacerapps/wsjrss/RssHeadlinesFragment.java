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
import android.widget.ProgressBar;

import com.pacerapps.wsjrss.rss_download.RssHeadlinesManager;

import java.util.ArrayList;

import static com.pacerapps.wsjrss.util.Constants.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class RssHeadlinesFragment extends Fragment implements AdapterView.OnItemClickListener{

    public static final String RSS_HEADLINES_KEY = "rssHeadlines";
    private ListView rssListView;
    private ArrayAdapter<String> rssArrayAdapter;
    private ProgressBar downloadingProgressBar;

    private ArrayList<String> savedAdapterContents;

    String fake = " f a k e ";


    public RssHeadlinesFragment() {
    }

    public ArrayAdapter<String> getRssArrayAdapter() {
        return rssArrayAdapter;
    }

    public ProgressBar getDownloadingProgressBar() {
        return downloadingProgressBar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            savedAdapterContents = savedInstanceState.getStringArrayList(RSS_HEADLINES_KEY);

        }
        return inflater.inflate(R.layout.fragment_rss, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        downloadingProgressBar = (ProgressBar) getActivity().findViewById(R.id.progress_bar_downloading);
        rssListView = (ListView) getActivity().findViewById(R.id.list_view_rss);
        rssArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.rss_list_item, R.id.text_view_rss_item);
        rssListView.setAdapter(rssArrayAdapter);
        rssListView.setOnItemClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        populateListViewWithSavedContents();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick: " + position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(RSS_HEADLINES_KEY, obtainAdapterContents(rssArrayAdapter));
    }

    public void populateRssListViewWithFakeData() {
        ArrayList<String> standIn = new ArrayList<>();
        standIn.add("Hjjpigs run wild!!!!");
        standIn.add("We don't know what to do!!!!");
        standIn.add(fake);
        rssArrayAdapter.addAll(standIn);
        rssArrayAdapter.notifyDataSetChanged();
    }

    public void downloadHeadlines() {
        RssHeadlinesManager rssHeadlinesManager = RssHeadlinesManager.getInstance();
        rssHeadlinesManager.downloadRssHeadlines(downloadingProgressBar, rssArrayAdapter);
    }

    private ArrayList<String> obtainAdapterContents(ArrayAdapter<String> arrayAdapter) {
        int count = arrayAdapter.getCount();
        ArrayList<String> itemsToSave = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            itemsToSave.add(arrayAdapter.getItem(i));
        }
        return itemsToSave;
    }

    private void populateListViewWithSavedContents() {
        if (savedAdapterContents != null) {
            rssArrayAdapter.addAll(savedAdapterContents);
            rssArrayAdapter.notifyDataSetChanged();
        }
    }
}
