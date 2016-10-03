package com.pacerapps.wsjrss;

import android.app.Activity;
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
import com.pacerapps.wsjrss.util.ConnectivityUtils;

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
    private boolean stateSaved;

    public RssHeadlinesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            savedAdapterContents = savedInstanceState.getParcelableArrayList(RSS_HEADLINES_KEY);
            stateSaved = true;
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
        if (stateSaved) {
            populateListViewWithSavedContents();
        } else {
            downloadHeadlines();
        }

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
        ConnectivityUtils connectivityUtils = new ConnectivityUtils(getContext());
        boolean connected = connectivityUtils.isNetworkAvailable();

        if (connected) {
            headlineItemAdapter.clearAdapter();
            RssHeadlinesManager rssHeadlinesManager = RssHeadlinesManager.getInstance();
            rssHeadlinesManager.downloadRssHeadlines(downloadingProgressBar, headlineItemAdapter);
        }
        showAppropriateSnackbar(connected);
    }

    public void showAppropriateSnackbar(boolean connected) {
        Activity activity = getActivity();

        if (activity instanceof RssActivity) {
            if (connected) {
                ((RssActivity) activity).showRefreshingRssSnackbar();
            } else {
                ((RssActivity) activity).showNoNetworkSnackbar();
            }
        }
    }

    private void populateListViewWithSavedContents() {
        if (savedAdapterContents != null) {
            headlineItemAdapter.setHeadlineItems(savedAdapterContents);
            headlineItemAdapter.notifyDataSetChanged();
        }
    }
}
