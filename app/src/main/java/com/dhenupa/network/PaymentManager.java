package com.dhenupa.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dhenupa.application.DhenupaApplication;
import com.dhenupa.model.Payment;
import com.dhenupa.model.db.DatabaseHelper;
import com.dhenupa.util.Utility;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by prsn0001 on 12/12/2015.
 */
public class PaymentManager {

    public static final String COL_ID = "_id";
    public static final String COL_USERID = "userid";
    public static final String COL_DONOERID = "donorId";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_PAYMENT_ID = "payment_id";
    public static final String COL_PAYMENT_DATE = "payment_date";
    public static final String COL_PAYMENT_MODE = "payment_mode";
    public static final String COL_LAST_MODIFIED = "lastModified";
    public static final String STATUS = "status";

    private final Context context;
    private DatabaseHelper dbHelper;

    // String addUrl =
    // "http://192.168.0.100:8080/DhenupaAdmin/MobileDhenupaServlet?";
    private static final String addUrl = DhenupaRequestQue.SERVER_URL + "/MobileDhenupaServlet?action=addPayment";

    public PaymentManager(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    private void addToServer(final Payment payment){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, addUrl, new JSONObject(getRequestParams(payment)), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    VolleyLog.v("Response:%n %s", response.toString(4));
                    Integer payment_id = (Integer) response.get(COL_PAYMENT_ID);
                    payment.setPayment_id(payment_id);
                    addToDb(payment, DhenupaApplication.STATUS_DONOR_SYCNED);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                addToDb(payment, DhenupaApplication.STATUS_DONOR_ADDED);
            }
        });
        DhenupaRequestQue.getInstance(context).getRequestQueue().add(req);
    }

    /*private void deleteFromServer(final Integer userId){
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
*/
    public void addNewPayment(Payment payment){
        if(Utility.isInternetOn(context))
            addToServer(payment);
        else
            addToDb(payment, DhenupaApplication.STATUS_DONOR_ADDED);
    }

    /*public void deleteDonor(Integer userId){
        if(Utility.isInternetOn(context))
            deleteFromServer(userId);
    }*/

    /*private void deleteFromDb(Integer userId){
        List<Donor> list = dbHelper.getDonorListDao().queryForEq(COL_USERID, userId);
        dbHelper.getDonorListDao().delete(list.get(0));
    }*/
    private void addToDb(Payment payment,int status){

        QueryBuilder<Payment, Integer> queryBuilder = dbHelper.getPaymentRuntimeDao().queryBuilder();
        Where<Payment, Integer> where = queryBuilder.where();
        try {
            where.eq(COL_PAYMENT_DATE, payment.getPayment_date());

            where.and();

            where.eq(COL_AMOUNT, payment.getAmount());

            PreparedQuery<Payment> preparedQuery = queryBuilder.prepare();
            List<Payment> paymentList = dbHelper.getPaymentRuntimeDao().query(preparedQuery);

            payment.setStatus(status);
            if(paymentList !=null && paymentList.size()>0)
                dbHelper.getPaymentRuntimeDao().update(payment);
            else
                dbHelper.getPaymentRuntimeDao().create(payment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
;
    }

    private HashMap<String, String> getRequestParams(Payment payment){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(COL_PAYMENT_DATE, payment.getPayment_date());
        params.put(COL_PAYMENT_MODE, payment.getPayment_mode());
        params.put(COL_USERID, new Integer(payment.getUserid()).toString());
        params.put(COL_AMOUNT, String.valueOf(payment.getAmount()));
        return  params;
    }
}