package com.dhenupa.activity;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dhenupa.application.DhenupaApplication;
import com.dhenupa.model.Donor;
import com.dhenupa.model.db.DatabaseHelper;
import com.dhenupa.util.SMSUtil;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

public class MessageToAllActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_to_all);
        findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = ((EditText) findViewById(R.id.messageEditText)).getText().toString();
                DatabaseHelper databaseHelper = new DatabaseHelper(MessageToAllActivity.this);
                QueryBuilder<Donor, Integer> queryBuilder = databaseHelper.getDonorListDao().queryBuilder();
                Where<Donor, Integer> where = queryBuilder.where();
                try {
                    where.eq("status", new Integer(DhenupaApplication.STATUS_DONOR_ADDED));
// and
                    where.or();

                    where.eq("status", new Integer(DhenupaApplication.STATUS_DONOR_UPDATED));

                    where.or();

                    where.eq("status", new Integer(DhenupaApplication.STATUS_DONOR_SYCNED));
                    PreparedQuery<Donor> preparedQuery = queryBuilder.prepare();
                    List<Donor> donorList = databaseHelper.getDonorListDao().query(preparedQuery);
                    for(Donor donor : donorList) {
                        Log.d(donor.getContactNumber(), message);
                        SMSUtil.sendSMS(donor.getContactNumber(), message);
                        try {
                             Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }catch (SQLException e ){

                }
                finish();
            }
        });
    }

}
