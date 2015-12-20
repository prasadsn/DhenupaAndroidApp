package com.dhenupa.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.dhenupa.application.DhenupaApplication;
import com.dhenupa.model.Donor;
import com.dhenupa.model.db.DatabaseHelper;
import com.dhenupa.network.DonorManager;
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
public class OfflineDonorSyncService extends IntentService {

    DatabaseHelper databaseHelper;
    public OfflineDonorSyncService() {
        super("OfflineDonorSyncService");
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
            deleteSync();
        }
    }

    private void addSync(){
        QueryBuilder<Donor, Integer> queryBuilder = databaseHelper.getDonorListDao().queryBuilder();
// get the WHERE object to build our query
        Where<Donor, Integer> where = queryBuilder.where();
        try {
            where.eq("status", new Integer(DhenupaApplication.STATUS_DONOR_ADDED));
// and
            where.or();

            where.eq("status", new Integer(DhenupaApplication.STATUS_DONOR_UPDATED));

            PreparedQuery<Donor> preparedQuery = queryBuilder.prepare();
            List<Donor> donorList = databaseHelper.getDonorListDao().query(preparedQuery);
            for(Donor donor : donorList){
                new DonorManager(this).addNewDonor(donor);
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

    private void deleteSync(){
        List<Donor> list = databaseHelper.getDonorListDao().queryForEq("status", new Integer(DhenupaApplication.STATUS_DONOR_DELETED));
        for(Donor donor : list){
            new DonorManager(this).deleteDonor(donor.getUserid());
        }
    }
}
