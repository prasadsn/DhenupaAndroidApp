package com.dhenupa.util;

import android.telephony.SmsManager;

/**
 * Created by prsn0001 on 11/29/2015.
 */
public class SMSUtil {

    public static void sendSMS(String phoneNo, String message){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
    }
}
