package com.dhenupa.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dhenupa.model.Payment;
import com.dhenupa.model.db.DatabaseHelper;
import com.dhenupa.network.PaymentManager;

import java.util.ArrayList;

public class PaymentHistoryActivity extends Activity {

    int donorId;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_payment_history);
        databaseHelper = new DatabaseHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        donorId = new Integer(getIntent().getStringExtra(PaymentManager.COL_DONOERID));
        ArrayList<Payment> list = (ArrayList<Payment>) databaseHelper.getPaymentRuntimeDao().queryForEq(PaymentManager.COL_DONOERID, donorId);
        ((ListView) findViewById(R.id.paymentlist)).setAdapter(new PaymentHistoryAdapter(list));
    }

    private class PaymentHistoryAdapter extends BaseAdapter {

        private ArrayList<Payment> paymentList;
        private PaymentHistoryAdapter(ArrayList<Payment> paymentList){
            this.paymentList = paymentList;
        }
        @Override
        public int getCount() {
            return paymentList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.item_payment_history, null);
            LinearLayout layout = (LinearLayout) convertView;
            ((TextView)layout.getChildAt(0)).setText(paymentList.get(position).getPayment_date());
            ((TextView)layout.getChildAt(1)).setText(new Integer(paymentList.get(position).getAmount()).toString());
            ((TextView)layout.getChildAt(2)).setText(paymentList.get(position).getPayment_mode());
            return layout;
        }
    }
}
