package com.pacerapps.wsjrss;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pacerapps.wsjrss.util.ConnectivityUtils;

import static com.pacerapps.wsjrss.util.Constants.TAG;

public class RssActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: FAB clicked!!!");

        RssHeadlinesFragment fragment = (RssHeadlinesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_rss_headlines);

        if (fragment != null && fragment.isVisible()) {
            fragment.downloadHeadlines();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

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

    public void showNoNetworkSnackbar() {
        Snackbar.make(fab, R.string.network_unavailable, Snackbar.LENGTH_LONG).show();
    }

    public void showRefreshingRssSnackbar() {
        Snackbar.make(fab, R.string.refreshing_feeds, Snackbar.LENGTH_SHORT).show();
    }
}
