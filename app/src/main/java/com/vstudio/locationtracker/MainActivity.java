package com.vstudio.locationtracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.vstudio.locationtracker.fragment.BaseFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureToolbar();
        openAptFragment();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String title = "";
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment != null) {
            title = fragment.getTitle();
        }
        setTitle(title);

        return super.onPrepareOptionsMenu(menu);
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

    @Override
    public void setTitle(CharSequence title) {
        TextView titleView = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.title);
        titleView.setText(title);
    }

    private void configureToolbar() {
        toolbar = (Toolbar) findViewById(R.id.appToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        getSupportActionBar().setCustomView(R.layout.toolbar_view);
    }

    private void openAptFragment() {
        BaseFragment fragment = new BaseFragment();


        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
