package com.dhenupa.activity.dummy;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.dhenupa.activity.R;
import com.dhenupa.model.DummyDataObjs;

import java.util.ArrayList;

public class DonrListDetails extends Activity {
    ListView listView;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donr_list_details);
        ArrayList<DummyDataObjs> list = new ArrayList<>();
        String[] name = {"Kousalya", "Supraja", "Poorva"};
        String[] add = {"CKS", "HMT", "New"};
        String[] dj = {"2015-01-01","2015-01-01","2015-01-01"};

        for (int i=0;i < name.length ; i++){
            DummyDataObjs dummyDataObjs = new DummyDataObjs(name[i],add[i],dj[i]);
            list.add(dummyDataObjs);
        }
        listView = (ListView) findViewById(R.id.listtest);
        //CustomListAdapter customListAdapter = new CustomListAdapter(DonrListDetails.this, list);
       // listView.setAdapter(customListAdapter);
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donr_list_details, menu);
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
