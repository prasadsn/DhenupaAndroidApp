package com.dhenupa.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dhenupa.model.DonorList;
import com.dhenupa.model.db.DatabaseHelper;

import java.util.ArrayList;


public class ActivityDonerDetailsLayout extends Activity {
    DatabaseHelper db;
    TextView name, address, area, city, contact_no, celeb_dt, amount, nandr, gotra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_details_layout);
        db = new DatabaseHelper(this);
        String userid = getIntent().getStringExtra("userid");
        ArrayList<DonorList> list = (ArrayList<DonorList>) db.getDonorListDao().queryForEq("userid", userid);
        DonorList donor = list.get(0);

        name = (TextView) findViewById(R.id.namedate);
        address = (TextView) findViewById(R.id.addrdata);
        area = (TextView) findViewById(R.id.areadata);
        city = (TextView) findViewById(R.id.citydata);
        contact_no = (TextView) findViewById(R.id.phnum);
        celeb_dt = (TextView) findViewById(R.id.celeb_date);
        amount = (TextView) findViewById(R.id.rupees);
        nandr = (TextView) findViewById(R.id.nakshgo);
        gotra = (TextView) findViewById(R.id.gotradata);

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
