package com.dhenupa.activity;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dhenupa.util.SMSUtil;

public class MainActivity extends Activity implements View.OnClickListener{

    private static final String LOG_TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.show_list).setOnClickListener(this);
        findViewById(R.id.add_new).setOnClickListener(this);
        findViewById(R.id.filter).setOnClickListener(this);
        findViewById(R.id.send_message_to_all).setOnClickListener(this);
        findViewById(R.id.celeberations).setOnClickListener(this);
        //MainListFragment mlf = new MainListFragment();
       // getFragmentManager().beginTransaction().add(R.id.mainitemlist, mlf);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_list:
                Intent intent = new Intent(MainActivity.this, DonorListActivity.class);
                startActivity(intent);
                break;
            case R.id.add_new:
                Intent intent1 = new Intent(MainActivity.this, AddDonorActivity.class);
                startActivity(intent1);
                break;
            case R.id.send_message_to_all:
                Intent intent2 = new Intent(MainActivity.this, MessageToAllActivity.class);
                startActivity(intent2);
                break;
            case R.id.filter:

                break;
            case R.id.celeberations:
                Intent intent3 = new Intent(MainActivity.this, EventBroadCastActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
