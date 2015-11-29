package com.dhenupa.service;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dhenupa.activity.DonorListActivity;
import com.dhenupa.model.Donor;
import com.dhenupa.model.db.DatabaseHelper;
import com.dhenupa.network.DhenupaRequestQue;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SyncService extends IntentService {

    DatabaseHelper db;
    Cursor cursor;

    private static final String LOG_TAG = "SyncService";

    @Override
    public void onCreate() {
        super.onCreate();
        db = new DatabaseHelper(this);

    }

    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        syncList();
    }

    private void syncList(){
        String url = DhenupaRequestQue.SERVER_URL + "/MobileDhenupaServlet?action=list";
        //String url = DhenupaRequestQue.SERVER_URL + "/DhenupaAdmin/MobileDhenupaServlet?action=list";
        //String url = "http://169.254.49.105:8080/DhenupaAdmin/MobileDhenupaServlet?action=list";
        //String url = "http://192.168.1.10:8080/DhenupaAdmin/MobileDhenupaServlet?";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Toast.makeText(MainActivity.this, (String) response.get("result"), Toast.LENGTH_LONG).show();
                    Donor[] list = new Gson().fromJson(String.valueOf(response.getJSONArray("list")), Donor[].class);
                    //db.getDonorListDao().deleteBuilder().delete();
                    for (int i = 0; i < list.length; i++) {
                        ArrayList<Donor> donor = (ArrayList<Donor>) db.getDonorListDao().queryForEq("userid", list[i].getUserid());
                        if(donor !=null && donor.size()>0)
                            db.getDonorListDao().update(list[i]);
                        else
                            db.getDonorListDao().create(list[i]);
                    }
                    Log.d(LOG_TAG, "" + list.length);
                    VolleyLog.v("Response:%n %s", response.toString(4));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(DonorListActivity.ACTION_SYNC_COMPLETED);
                sendBroadcast(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VollyError", error.toString());
            }
        });
        // Add the request to the RequestQueue.
        DhenupaRequestQue.getInstance(this).getRequestQueue().add(req);
    }
}
