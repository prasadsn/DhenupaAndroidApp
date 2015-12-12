package com.dhenupa.network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dhenupa.application.DhenupaApplication;
import com.dhenupa.model.Donor;
import com.dhenupa.model.db.DatabaseHelper;
import com.dhenupa.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by prsn0001 on 12/12/2015.
 */
public class DonorManager implements Response.Listener<JSONObject>, Response.ErrorListener {


    private static final String COL_REG_DATE = "regDate";
    private static final String COL_USERID = "userid";
    private static final String COL_NAME = "name";
    private static final String COL_ADDRESS = "address";
    private static final String COL_AREA = "area";
    private static final String COL_CITY = "city";
    private static final String COL_CONTACT_NO = "contactNo";
    private static final String COL_DOB = "dob";
    private static final String COL_DONATION_TYPE = "donationType";
    private static final String COL_AMOUNT = "amount";
    private static final String COL_PHOTO = "photo";
    private static final String COL_EMAIL = "email";
    private static final String COL_RASHI = "rashi";
    private static final String COL_NAKSHATRA = "nakshatra";
    private static final String COL_GOTHRA = "gotra";
    private static final String COL_JOB = "job";
    private static final String COL_COMMENT = "comment";
    private final Context context;
    private Donor donor;
    private DatabaseHelper dbHelper;

    // String url =
    // "http://192.168.0.100:8080/DhenupaAdmin/MobileDhenupaServlet?";
    private static final String url = DhenupaRequestQue.SERVER_URL + "/MobileDhenupaServlet?";

    public DonorManager(Donor donor, Context context) {
        this.donor = donor;
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    private void addToServer(HashMap<String, String> params){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), this, this);
        DhenupaRequestQue.getInstance(context).getRequestQueue().add(req);
    }

    public void addNewDonor(){
        if(Utility.isInternetOn(context))
            addToServer(getRequestParams());
        else
            addToDb(DhenupaApplication.STATUS_DONOR_ADDED);
    }

    private void addToDb(int status){
        donor.setStatus(status);
        dbHelper.getDonorListDao().create(donor);
    }
    @Override
    public void onResponse(JSONObject response) {
        try {
            VolleyLog.v("Response:%n %s", response.toString(4));
            String userid = (String) response.get("userid");
            donor.setUserid(new Integer(userid).intValue());
            addToDb(DhenupaApplication.STATUS_DONOR_SYCNED);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        VolleyLog.e("Error: ", error.getMessage());
        addToDb(DhenupaApplication.STATUS_DONOR_ADDED);
    }

    private HashMap<String, String> getRequestParams(){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(COL_NAME, donor.getName());
        params.put(COL_ADDRESS, donor.getAddress());
        params.put(COL_AREA, donor.getArea());
        params.put(COL_CITY, donor.getCity());
        params.put(COL_CONTACT_NO, donor.getContactNumber());
        params.put(COL_EMAIL, donor.getEmailId());
        params.put(COL_REG_DATE, donor.getRegisteredDate());
        params.put(COL_DOB, donor.getDob());
        params.put(COL_AMOUNT, String.valueOf(donor.getAmount()));
        params.put(COL_DONATION_TYPE, donor.getDonationType());
        params.put(COL_NAKSHATRA, donor.getNakshatra());
        params.put(COL_RASHI, donor.getRashi());
        params.put(COL_GOTHRA, donor.getGothra());
        params.put(COL_JOB, donor.getJob());
        params.put(COL_COMMENT, donor.getComment());

        return  params;
    }
}