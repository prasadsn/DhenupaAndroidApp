package com.dhenupa.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.dhenupa.adapter.CustomListAdapter;
import com.dhenupa.application.DhenupaApplication;
import com.dhenupa.model.db.DatabaseHelper;
import com.dhenupa.network.DonorManager;
import com.dhenupa.service.SyncService;

public class DonorListActivity extends Activity implements AdapterView.OnItemClickListener  {

    public static final String ACTION_SYNC_COMPLETED = "com.dhenupa.ACTION_SYNC_COMPLETED";
    private DatabaseHelper db;
    private ListView listView;
    private CustomListAdapter adapter;
    private EditText search;
    private SyncBroadcastReceiver reciever;
    String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);
        db = new DatabaseHelper(this);
        setContentView(R.layout.activity_donr_list_details);
        initSync();
        listView = (ListView) findViewById(R.id.listtest);
        search = (EditText) findViewById(R.id.search);
        reciever = new SyncBroadcastReceiver();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //DonorListActivity.this.adapter.getFilter().filter(s);
                adapter = new CustomListAdapter(DonorListActivity.this, getCursorforSearch(s.toString()),true);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter = new CustomListAdapter(this, getCursor(), true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        IntentFilter filter = new IntentFilter(ACTION_SYNC_COMPLETED);
        registerReceiver(reciever, filter);
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
        String SqlQuery = "SELECT * FROM Donor where status !=" + DhenupaApplication.STATUS_DONOR_DELETED;
        Cursor cursor = database.rawQuery(SqlQuery, null);
        Log.d("Size", cursor.getCount() + "");
        return cursor;
    }

    private Cursor getCursorforSearch(String s){
        SQLiteDatabase database = db.getReadableDatabase();
        String SqlQuery = "SELECT * FROM Donor where name LIKE '" + s +"%';";
        Cursor cursor = database.rawQuery(SqlQuery, null);
        Log.d("Size", cursor.getCount() + "");
        return cursor;
    }

    private void initSync(){
        Intent intent = new Intent(this, SyncService.class);
        startService(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CustomListAdapter.ViewHolder viewHolder = (CustomListAdapter.ViewHolder) view.getTag();
        Intent intent = new Intent(DonorListActivity.this, ActivityDonerDetailsLayout.class);
        intent.putExtra(DonorManager.COL_ID, viewHolder._id);
        startActivity(intent);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(reciever);
    }

    private class SyncBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.changeCursor(getCursor());
            adapter.notifyDataSetChanged();
        }
    }

}
