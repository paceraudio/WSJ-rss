package com.pacerapps.wsjrss;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.pacerapps.wsjrss.rss_download.RssHeadlinesManager;

import static com.pacerapps.wsjrss.util.Constants.*;

public class RssActivity extends AppCompatActivity {

    //RssHeadlinesFragment rssHeadlinesFragment;
    RssHeadlinesManager rssHeadlinesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: FAB clicked!!!");

                RssHeadlinesFragment fragment = (RssHeadlinesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_rss_headlines);
                if (fragment != null && fragment.isVisible()) {

                    //RssHeadlinesManager.getInstance().downloadRssHeadlines(rssHeadlinesFragment.getDownloadingProgressBar(), rssHeadlinesFragment.getRssArrayAdapter());
                    fragment.downloadHeadlines();
                    Snackbar.make(view, R.string.refreshing_feeds, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rss, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
