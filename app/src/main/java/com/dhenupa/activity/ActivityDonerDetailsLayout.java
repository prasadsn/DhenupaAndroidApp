package com.dhenupa.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhenupa.application.DhenupaApplication;
import com.dhenupa.model.Donor;
import com.dhenupa.model.db.DatabaseHelper;
import com.dhenupa.network.DonorManager;
import com.dhenupa.network.PaymentManager;
import com.dhenupa.util.SMSUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ActivityDonerDetailsLayout extends Activity {
    DatabaseHelper db;
    TextView name, address, area, city, contact_no, celeb_dt, amount, nandr, gotra;
    ImageView pic;
    private String _id;
    private Donor donor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_details_layout);
        db = new DatabaseHelper(this);
        _id = getIntent().getStringExtra(DonorManager.COL_ID);
        ArrayList<Donor> list = (ArrayList<Donor>) db.getDonorListDao().queryForEq(DonorManager.COL_ID, _id);
        donor = list.get(0);

        name = (TextView) findViewById(R.id.namedate);
        address = (TextView) findViewById(R.id.addrdata);
        area = (TextView) findViewById(R.id.areadata);
        city = (TextView) findViewById(R.id.citydata);
        contact_no = (TextView) findViewById(R.id.phnum);
        celeb_dt = (TextView) findViewById(R.id.celb_dt);
        amount = (TextView) findViewById(R.id.rupees);
        nandr = (TextView) findViewById(R.id.nakshgo);
        gotra = (TextView) findViewById(R.id.gotradata);
        pic = (ImageView) findViewById(R.id.image);

        final String imgFileName = Environment.getExternalStorageDirectory() + File.separator + "Dhenupa"
                + File.separator + donor.getContactNumber() + "_" + donor.getName() + ".jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(imgFileName);
        if(bitmap!=null){
            pic.setImageBitmap(bitmap);
        }

        name.setText(donor.getName());
        address.setText(donor.getAddress());
        area.setText(donor.getArea());
        city.setText(donor.getCity());
        contact_no.setText(donor.getContactNumber());
        celeb_dt.setText(donor.getRegisteredDate());
        amount.setText(String.valueOf(donor.getAmount()));
        nandr.setText(donor.getNakshatra());
        gotra.setText(donor.getGothra());



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donor_details_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            String _id = getIntent().getStringExtra(DonorManager.COL_ID);
            List<Donor> list = db.getDonorListDao().queryForEq(DonorManager.COL_ID, new Integer(_id));
            Donor donor = list.get(0);
            if(donor.getStatus() == DhenupaApplication.STATUS_DONOR_ADDED){
                db.getDonorListDao().delete(donor);
                finish();
            } else {
                donor.setStatus(DhenupaApplication.STATUS_DONOR_DELETED);
                db.getDonorListDao().update(donor);
                new DonorManager(getApplicationContext()).deleteDonor(donor.getUserid());
                finish();
            }
            return true;
        }
        if(id == R.id.action_edit){

        }
        if(id == R.id.getpament){
            Intent intent = new Intent(ActivityDonerDetailsLayout.this, PaymentHistoryActivity.class);
            intent.putExtra(PaymentManager.COL_DONOERID, _id);
            startActivity(intent);
        }
        if(id == R.id.updated_payment){
            Intent intent = new Intent(ActivityDonerDetailsLayout.this, AddPaymentActivity.class);
            intent.putExtra(PaymentManager.COL_DONOERID, _id);
            startActivity(intent);
        }
        if(id == R.id.reminder){
            SMSUtil.sendSMS(donor.getContactNumber(), "Greetings! Please donate the accepted amount towards Dhenupa Trust Go Grasa. Thanks, Korlahalli Srikarachar");
        }
        if(id == R.id.call){
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(donor.getContactNumber()));
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }
}
