package com.dhenupa.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dhenupa.adapter.CustomListAdapter;
import com.dhenupa.model.DonorList;
import com.dhenupa.model.db.DatabaseHelper;
import com.dhenupa.network.DhenupaRequestQue;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    private static final String LOG_TAG = "MainActivity";
    DatabaseHelper db;
    Cursor cursor;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(this);
        setContentView(R.layout.activity_donr_list_details);
        listView = (ListView) findViewById(R.id.listtest);
        //MainListFragment mlf = new MainListFragment();
       // getFragmentManager().beginTransaction().add(R.id.mainitemlist, mlf);
        syncList();
    }
    private Cursor getCursor(){
        SQLiteDatabase database = db.getReadableDatabase();
        String SqlQuery = "SELECT * FROM DonorList";
        Cursor cursor = database.rawQuery(SqlQuery, null);
        return cursor;
    }

    private void syncList(){
        //String url = "http://admin-dhenupa.rhcloud.com//DhenupaAdmin/MobileDhenupaServlet?";
        //String url = "http://169.254.49.105:8080/DhenupaAdmin/MobileDhenupaServlet?action=list";
        String url = "http://192.168.1.10:8080/DhenupaAdmin/MobileDhenupaServlet?action=list";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Toast.makeText(MainActivity.this, (String) response.get("result"), Toast.LENGTH_LONG).show();
                    try {
                        DonorList[] list = new Gson().fromJson(String.valueOf(response.getJSONArray("list")), DonorList[].class);
                        db.getDonorListDao().deleteBuilder().delete();
                        for (int i = 0; i < list.length; i++) {
                            db.getDonorListDao().create(list[i]);
                        }
                        Log.d(LOG_TAG, ""+list.length);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    VolleyLog.v("Response:%n %s", response.toString(4));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cursor = getCursor();

                CustomListAdapter adapter = new CustomListAdapter(MainActivity.this,cursor, true);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(MainActivity.this, ActivityDonerDetailsLayout.class);
                        startActivity(intent);

                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VollyError", error.toString());
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        // Add the request to the RequestQueue.
        DhenupaRequestQue.getInstance(this).getRequestQueue().add(req);

/*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          }
        });
*/

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
}
