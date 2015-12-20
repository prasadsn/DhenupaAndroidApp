package com.dhenupa.service;

import android.app.IntentService;
import android.content.Intent;

import com.dhenupa.application.DhenupaApplication;
import com.dhenupa.model.Payment;
import com.dhenupa.model.db.DatabaseHelper;
import com.dhenupa.network.PaymentManager;
import com.dhenupa.util.Utility;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class OfflinePaymentSyncService extends IntentService {

    DatabaseHelper databaseHelper;
    public OfflinePaymentSyncService() {
        super("OfflinePaymentSyncService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        databaseHelper = new DatabaseHelper(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(Utility.isInternetOn(this)) {
            addSync();
        }
    }

    private void addSync(){
        QueryBuilder<Payment, Integer> queryBuilder = databaseHelper.getPaymentRuntimeDao().queryBuilder();
// get the WHERE object to build our query
        Where<Payment, Integer> where = queryBuilder.where();
        try {
            where.eq("status", new Integer(DhenupaApplication.STATUS_DONOR_ADDED));

            PreparedQuery<Payment> preparedQuery = queryBuilder.prepare();
            List<Payment> paymenntList = databaseHelper.getPaymentRuntimeDao().query(preparedQuery);
            for(Payment payment : paymenntList){
                new PaymentManager(this).addNewPayment(payment);
                //Sleep is added to maintain a different Timestamp between multiple entries
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
