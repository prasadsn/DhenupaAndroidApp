package com.dhenupa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.dhenupa.model.Donor;
import com.dhenupa.model.Payment;
import com.dhenupa.model.db.DatabaseHelper;
import com.dhenupa.network.DonorManager;
import com.dhenupa.network.PaymentManager;
import com.dhenupa.util.SMSUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class AddPaymentActivity extends Activity {

    DatePicker paymentDate = null;
    RadioGroup paymentMode = null;
    EditText paymentAmount = null;
    private Integer donorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        donorId = new Integer(getIntent().getStringExtra(PaymentManager.COL_DONOERID));
        paymentDate = (DatePicker) findViewById(R.id.registered_Date);
        paymentMode = (RadioGroup) findViewById(R.id.radiogrp);
        paymentAmount = (EditText) findViewById(R.id.amountrs);
        donorId = new Integer(getIntent().getStringExtra(PaymentManager.COL_DONOERID));
        paymentMode.check(R.id.cashmode);
        findViewById(R.id.paymentsubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, paymentDate.getYear());
                calendar.set(Calendar.MONTH, paymentDate.getMonth());
                calendar.set(Calendar.DATE, paymentDate.getDayOfMonth());

                Date date = new Date(calendar.getTimeInMillis());
                Payment payment = new Payment();
                payment.setPayment_date(date.toString());
                payment.setPayment_mode(getPaymentMode(paymentMode.getCheckedRadioButtonId()));
                payment.setAmount(new Integer(paymentAmount.getText().toString()));
                payment.setDonorId(donorId);
                new PaymentManager(AddPaymentActivity.this).addNewPayment(payment);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(AddPaymentActivity.this, PaymentHistoryActivity.class);
                intent.putExtra(PaymentManager.COL_DONOERID, donorId.toString());
                startActivity(intent);

                ArrayList<Donor> list = (ArrayList<Donor>) new DatabaseHelper(AddPaymentActivity.this).getDonorListDao().queryForEq(DonorManager.COL_ID, donorId);
                Donor donor = list.get(0);
                SMSUtil.sendSMS(donor.getContactNumber(), "This is the acknowledge the receipt of payment of Rs."+ payment.getAmount() + "towards Dhenupa trust Go Grasa. Thanks, Korlahalli Srikarachar");

                finish();
            }
        });
    }

    private String getPaymentMode(int index){
        switch (index){
            case R.id.cashmode:
                return getResources().getString(R.string.payment_mode_cash);
            case R.id.Cheque:
                return getResources().getString(R.string.payment_mode_cheque);
            default:
                return getResources().getString(R.string.payment_mode_online);
        }
    }
}
