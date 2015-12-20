package com.dhenupa.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by prsn0001 on 12/12/2015.
 */
public class DonorManager {

    public static final String COL_ID = "_id";
    public static final String COL_REG_DATE = "regDate";
    public static final String COL_USERID = "userid";
    public static final String COL_NAME = "name";
    public static final String COL_ADDRESS = "address";
    public static final String COL_AREA = "area";
    public static final String COL_CITY = "city";
    public static final String COL_CONTACT_NO = "contactNo";
    public static final String COL_DOB = "dob";
    public static final String COL_DONATION_TYPE = "donationType";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_PHOTO = "photo";
    public static final String COL_EMAIL = "email";
    public static final String COL_RASHI = "rashi";
    public static final String COL_NAKSHATRA = "nakshatra";
    public static final String COL_GOTHRA = "gotra";
    public static final String COL_JOB = "job";
    public static final String COL_COMMENT = "comments";
    //public static final String COL_LAST_MODIFIED = "lastModified";
    private final Context context;
    private DatabaseHelper dbHelper;

    // String addUrl =
    // "http://192.168.0.100:8080/DhenupaAdmin/MobileDhenupaServlet?";
    private static final String addUrl = DhenupaRequestQue.SERVER_URL + "/MobileDhenupaServlet?action=update";
    private static final String deleteUrl = DhenupaRequestQue.SERVER_URL + "/MobileDhenupaServlet?action=delete&userId=";

    public DonorManager(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    private void addToServer(final Donor donor){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, addUrl, new JSONObject(getRequestParams(donor)), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    VolleyLog.v("Response:%n %s", response.toString(4));
                    Integer userid = (Integer) response.get(COL_USERID);
                    donor.setUserid(userid.intValue());
                    addToDb(donor, DhenupaApplication.STATUS_DONOR_SYCNED);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                addToDb(donor, DhenupaApplication.STATUS_DONOR_ADDED);
            }
        });
        DhenupaRequestQue.getInstance(context).getRequestQueue().add(req);
    }

    private void deleteFromServer(final Integer userId){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, deleteUrl + userId.intValue(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                deleteFromDb(userId);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        DhenupaRequestQue.getInstance(context).getRequestQueue().add(req);
    }

    public void addNewDonor(Donor donor){
        if(Utility.isInternetOn(context))
            addToServer(donor);
        else
            addToDb(donor, DhenupaApplication.STATUS_DONOR_ADDED);
    }

    public void deleteDonor(Integer userId){
        if(Utility.isInternetOn(context))
            deleteFromServer(userId);
    }

    private void deleteFromDb(Integer userId){
        List<Donor> list = dbHelper.getDonorListDao().queryForEq(COL_USERID, userId);
        dbHelper.getDonorListDao().delete(list.get(0));
    }

    private void addToDb(Donor donor,int status){
        donor.setStatus(status);
        ArrayList<Donor> donorList = (ArrayList<Donor>) dbHelper.getDonorListDao().queryForEq("contactNumber", donor.getContactNumber());
        if(donorList !=null && donorList.size()>0)
            dbHelper.getDonorListDao().update(donor);
        else
            dbHelper.getDonorListDao().create(donor);
    }

    private HashMap<String, String> getRequestParams(Donor donor){
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
        params.put(COL_COMMENT, donor.getComments());

        final String imgFileName = Environment.getExternalStorageDirectory() + File.separator + "Dhenupa"
                + File.separator + donor.getContactNumber() + "_" + donor.getName() + ".jpg";
        if(new File(imgFileName).exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFileName);
            String bitmapdata = "data:image/jpeg;base64," + encodeTobase64(bitmap);
            params.put(COL_PHOTO, bitmapdata);
        }
        return  params;
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap imagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }

}