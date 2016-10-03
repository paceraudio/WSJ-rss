package com.pacerapps.wsjrss;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.pacerapps.wsjrss.adapter.HeadlineItemAdapter;
import com.pacerapps.wsjrss.rss_download.HeadlineItem;
import com.pacerapps.wsjrss.rss_download.RssHeadlinesManager;

import java.util.ArrayList;

import static com.pacerapps.wsjrss.util.Constants.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class RssHeadlinesFragment extends Fragment implements AdapterView.OnItemClickListener{

    public static final String RSS_HEADLINES_KEY = "rssHeadlines";

    private ListView rssListView;
    private HeadlineItemAdapter headlineItemAdapter;
    private ProgressBar downloadingProgressBar;

    private ArrayList<HeadlineItem> savedAdapterContents;


    public RssHeadlinesFragment() {
    }


    public ProgressBar getDownloadingProgressBar() {
        return downloadingProgressBar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            savedAdapterContents = savedInstanceState.getParcelableArrayList(RSS_HEADLINES_KEY);

        }
        return inflater.inflate(R.layout.fragment_rss, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        downloadingProgressBar = (ProgressBar) getActivity().findViewById(R.id.progress_bar_downloading);
        rssListView = (ListView) getActivity().findViewById(R.id.list_view_rss);
        headlineItemAdapter = new HeadlineItemAdapter(getContext());
        rssListView.setAdapter(headlineItemAdapter);
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
        outState.putParcelableArrayList(RSS_HEADLINES_KEY, headlineItemAdapter.getHeadlineItems());
    }


    public void downloadHeadlines() {
        headlineItemAdapter.clearAdapter();
        RssHeadlinesManager rssHeadlinesManager = RssHeadlinesManager.getInstance();
        rssHeadlinesManager.downloadRssHeadlines(downloadingProgressBar, headlineItemAdapter);

    }

    private void populateListViewWithSavedContents() {
        if (savedAdapterContents != null) {
            headlineItemAdapter.setHeadlineItems(savedAdapterContents);
            headlineItemAdapter.notifyDataSetChanged();
        }
    }
}
