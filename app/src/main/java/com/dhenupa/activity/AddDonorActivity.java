package com.dhenupa.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.dhenupa.application.DhenupaApplication;
import com.dhenupa.model.Donor;
import com.dhenupa.model.db.DatabaseHelper;
import com.dhenupa.network.DonorManager;
import com.dhenupa.util.Utility;

public class AddDonorActivity extends Activity {

	DatabaseHelper db;

	private ImageView userPic;

	private static final String TAG = AddDonorActivity.class.getName();

	private EditText nameEditText, addrEditText, areaEditText, contactEditText, emailEditText, amountEditText, gotraEditText, jobEditText, commentText;

	private Spinner donationTypeSpinner, nakshatraSpinner, rashiSpinner;

	private DatePicker regDatePicker, celebDatePicker;

	private AutoCompleteTextView cityEditText;
	private Bitmap bp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_donor);
		cityEditText = (AutoCompleteTextView) findViewById(R.id.cities);
		db = new DatabaseHelper(this);
		// Get the string array
		String[] countries = getResources().getStringArray(R.array.cities);
		// Create the adapter and set it to the AutoCompleteTextView
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
		cityEditText.setAdapter(adapter);

		nameEditText = (EditText) findViewById(R.id.name);
		addrEditText = (EditText) findViewById(R.id.address);
		areaEditText = (EditText) findViewById(R.id.area);
		contactEditText = (EditText) findViewById(R.id.contact);
		emailEditText = (EditText) findViewById(R.id.email);
		amountEditText = (EditText) findViewById(R.id.amount);
		gotraEditText = (EditText) findViewById(R.id.gotra);
		jobEditText = (EditText) findViewById(R.id.job);
		commentText = (EditText) findViewById(R.id.comments);

		donationTypeSpinner = (Spinner) findViewById(R.id.amount_type);
		nakshatraSpinner = (Spinner) findViewById(R.id.nakshatra);
		rashiSpinner = (Spinner) findViewById(R.id.rashi);

		regDatePicker = (DatePicker) findViewById(R.id.registered_Date);
		celebDatePicker = (DatePicker) findViewById(R.id.celeb_date);

		userPic = (ImageView) findViewById(R.id.imageView1);
		userPic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 0);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		bp = (Bitmap) data.getExtras().get("data");
		bp = getResizedBitmap(bp, getExternalFilesDir(null).getAbsolutePath() + "pic.jpg");
		// bp = getResizedBitmap(bp, 200);
		userPic.setImageBitmap(bp);
	}

	public Bitmap getResizedBitmap(Bitmap bmpPic, String finalPath) {
		int MAX_IMAGE_SIZE = 100 * 1024; // max final file size
		int compressQuality = 104; // quality decreasing by 5 every loop. (start
		// from 99)
		int streamLength = MAX_IMAGE_SIZE;
		while (streamLength >= MAX_IMAGE_SIZE) {
			ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
			compressQuality -= 5;
			Log.d(TAG, "Quality: " + compressQuality);
			bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
			byte[] bmpPicByteArray = bmpStream.toByteArray();
			streamLength = bmpPicByteArray.length;
			Log.d(TAG, "Size: " + streamLength);
		}
		try {
			FileOutputStream bmpFile = new FileOutputStream(finalPath);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bos);
			byte[] data = bos.toByteArray();
			bmpPic = BitmapFactory.decodeByteArray(data, 0, data.length);
			bmpFile.flush();
			bmpFile.close();
		} catch (Exception e) {
			Log.e(TAG, "Error on saving file");
		}
		return bmpPic;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_submit) {
			Donor donor = CreateDonor();
			if(bp !=null ){
				final String imgFileName = Environment.getExternalStorageDirectory() + File.separator + "Dhenupa"
						+ File.separator + donor.getContactNumber() + "_" + donor.getName() + ".jpg";
				savePic(imgFileName);
			}
			new DonorManager(getApplicationContext()).addNewDonor(donor);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void savePic(String imgFileName){

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] decodedString = baos.toByteArray();
		//decodedString = response.getBytes();
		Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		if(bitmap != null)
			Utility.savePic(bitmap, imgFileName);

	}

	private Donor CreateDonor(){
		Donor donor = new Donor();
		donor.setName(nameEditText.getText().toString());
		donor.setAddress(addrEditText.getText().toString());
		donor.setArea(areaEditText.getText().toString());
		donor.setCity(cityEditText.getText().toString());
		donor.setContactNumber(contactEditText.getText().toString());
		donor.setEmailId(emailEditText.getText().toString());
		donor.setRegisteredDate(regDatePicker.getYear() + "-" + (regDatePicker.getMonth() + 1) + "-" + regDatePicker.getDayOfMonth());
		donor.setDob(celebDatePicker.getYear() + "-" + (celebDatePicker.getMonth() + 1) + "-" + celebDatePicker.getDayOfMonth());
		donor.setAmount(new Integer(amountEditText.getText().toString()).intValue());
		donor.setDonationType(donationTypeSpinner.getSelectedItem().toString());
		donor.setNakshatra(nakshatraSpinner.getSelectedItem().toString());
		donor.setRashi(rashiSpinner.getSelectedItem().toString());
		donor.setGothra(gotraEditText.getText().toString());
		donor.setJob(jobEditText.getText().toString());
		donor.setComments(commentText.getText().toString());
		donor.setStatus(DhenupaApplication.STATUS_DONOR_ADDED);

		return donor;
	}
}
