package com.dhenupa.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.toolbox.NetworkImageView;
import com.dhenupa.adapter.CustomListAdapter;
import com.dhenupa.model.db.DatabaseHelper;
import com.dhenupa.service.SyncService;

public class DonorListActivity extends Activity implements AdapterView.OnItemClickListener  {

    public static final String ACTION_SYNC_COMPLETED = "com.dhenupa.ACTION_SYNC_COMPLETED";
    private DatabaseHelper db;
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);
        db = new DatabaseHelper(this);
        setContentView(R.layout.activity_donr_list_details);
        initSync();
        listView = (ListView) findViewById(R.id.listtest);
        adapter = new CustomListAdapter(this, getCursor(), true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donor_list, menu);
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
    private Cursor getCursor(){
        SQLiteDatabase database = db.getReadableDatabase();
        String SqlQuery = "SELECT * FROM DonorList";
        Cursor cursor = database.rawQuery(SqlQuery, null);
        return cursor;
    }

    private void initSync(){
        IntentFilter filter = new IntentFilter(ACTION_SYNC_COMPLETED);
        registerReceiver(new SyncBroadcastReceiver(), filter);

        Intent intent = new Intent(this, SyncService.class);
        startService(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(DonorListActivity.this, ActivityDonerDetailsLayout.class);
        startActivity(intent);

    }

    private class SyncBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.changeCursor(getCursor());
            adapter.notifyDataSetChanged();
        }
    }
}