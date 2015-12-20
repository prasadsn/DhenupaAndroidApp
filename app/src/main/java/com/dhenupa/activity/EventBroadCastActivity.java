package com.dhenupa.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;

import com.dhenupa.model.Donor;
import com.dhenupa.model.db.DatabaseHelper;
import com.dhenupa.util.Utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EventBroadCastActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_broad_cast);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            int count = getIntent().getClipData().getItemCount();
            List<String> list = new ArrayList<String>();
            for(int i = 0; i < count; i++){
                list.add(Utility.getRealPathFromURI(this, getIntent().getClipData().getItemAt(i).getUri()));
            }
            ArrayList<Donor> donorList = (ArrayList<Donor>) new DatabaseHelper(this).getDonorListDao().queryForAll();
            ArrayList<Donor> modifiedList = new ArrayList<Donor>();
            for(Donor donor:donorList){
                if(donor.getEmailId() != null && Utility.isValidEmail(donor.getEmailId())){
                    modifiedList.add(donor);
                }
            }
            String[] emailIds = new String[modifiedList.size()];
            for(int i = 0; i < emailIds.length; i++)
                emailIds[i] = modifiedList.get(i).getEmailId();
//            email(this, new String[]{"prasadessen@gmail.com", "prasad.sn@synchronoss.com"}, null, "Dhenupa event", "Email content", list);
            email(this, emailIds, null, "", "", list);
            finish();
        }
    }
    public static void email(Context context, String[] emailTo, String emailCC,
                             String subject, String emailText, List<String> filePaths)
    {
        //need to "send multiple" to get more than one attachment
        final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                emailTo);
        //emailIntent.putExtra(android.content.Intent.EXTRA_CC,
        //        new String[]{emailCC});
        //emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);

        emailIntent.setType("message/rfc822");

        emailIntent.setPackage("com.google.android.gm");
        ArrayList<String> extraText = new ArrayList<String>();
        extraText.add(emailText);
        //emailIntent.putExtra(Intent.EXTRA_TEXT, extraText);
        //has to be an ArrayList
        ArrayList<Uri> uris = new ArrayList<Uri>();
        //convert from paths to Android friendly Parcelable Uri's
        for (String file : filePaths)
        {
            File fileIn = new File(file);
            Uri u = Uri.fromFile(fileIn);
            uris.add(u);
        }
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }
}
