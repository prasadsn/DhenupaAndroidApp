package com.dhenupa.activity;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dhenupa.network.DhenupaRequestQue;

public class AddDonorActivity extends Activity {


	private ImageView userPic;

	private static final String TAG = MainActivity.class.getName();
	private static final String COL_REG_DATE = "regDate";
	private static final String COL_USERID = "userid";
	private static final String COL_NAME = "name";
	private static final String COL_ADDRESS = "address";
	private static final String COL_AREA = "area";
	private static final String COL_CITY = "city";
	private static final String COL_CONTACT_NO = "contactNo";
	private static final String COL_DOB = "dob";
	private static final String COL_DONATION_TYPE = "donationType";
	private static final String COL_AMOUNT = "amount";
	private static final String COL_PHOTO = "photo";
	private static final String COL_EMAIL = "email";
	private static final String COL_RASHI = "rashi";
	private static final String COL_NAKSHATRA = "nakshatra";
	private static final String COL_GOTHRA = "gotra";
	private static final String COL_JOB = "job";
	private static final String COL_COMMENT = "comment";

	private EditText nameEditText, addrEditText, areaEditText, contactEditText, emailEditText, amountEditText, gotraEditText, jobEditText, commentText;

	private Spinner donationTypeSpinner, nakshatraSpinner, rashiSpinner;

	private DatePicker regDatePicker, celebDatePicker;

	private AutoCompleteTextView cityEditText;

	private String bitmapdata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_donor);
		cityEditText = (AutoCompleteTextView) findViewById(R.id.cities);
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
		commentText = (EditText) findViewById(R.id.comment);

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
		Bitmap bp = (Bitmap) data.getExtras().get("data");
		bp = getResizedBitmap(bp, getExternalFilesDir(null).getAbsolutePath() + "pic.jpg");
		// bp = getResizedBitmap(bp, 200);
		userPic.setImageBitmap(bp);
		bitmapdata = "data:image/jpeg;base64," + encodeTobase64(bp);
		Log.d(TAG, "bitmapData length = " + bitmapdata.length());
	}

	private void sendData() {
		// String url =
		// "http://192.168.0.100:8080/DhenupaAdmin/MobileDhenupaServlet?";
		String url = "http://admin-dhenupa.rhcloud.com//DhenupaAdmin/MobileDhenupaServlet?";

		HashMap<String, String> params = new HashMap<String, String>();
		params.put(COL_NAME, nameEditText.getText().toString());
		params.put(COL_ADDRESS, addrEditText.getText().toString());
		params.put(COL_AREA, areaEditText.getText().toString());
		params.put(COL_CITY, cityEditText.getText().toString());
		params.put(COL_CONTACT_NO, contactEditText.getText().toString());
		params.put(COL_EMAIL, emailEditText.getText().toString());
		params.put(COL_REG_DATE, regDatePicker.getYear() + "-" + (regDatePicker.getMonth() + 1) + "-" + regDatePicker.getDayOfMonth());
		params.put(COL_DOB, celebDatePicker.getYear() + "-" + (celebDatePicker.getMonth() + 1) + "-" + celebDatePicker.getDayOfMonth());
		params.put(COL_AMOUNT, amountEditText.getText().toString());
		params.put(COL_DONATION_TYPE, donationTypeSpinner.getSelectedItem().toString());
		params.put(COL_NAKSHATRA, nakshatraSpinner.getSelectedItem().toString());
		params.put(COL_RASHI, rashiSpinner.getSelectedItem().toString());
		params.put(COL_GOTHRA, gotraEditText.getText().toString());
		params.put(COL_JOB, jobEditText.getText().toString());
		params.put(COL_COMMENT, commentText.getText().toString());

		if (bitmapdata != null)
			params.put(COL_PHOTO, bitmapdata);

		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					Toast.makeText(AddDonorActivity.this, (String) response.get("result"), Toast.LENGTH_LONG).show();
					VolleyLog.v("Response:%n %s", response.toString(4));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(AddDonorActivity.this, error.toString(), Toast.LENGTH_LONG).show();
				VolleyLog.e("Error: ", error.getMessage());
			}
		});
		// Add the request to the RequestQueue.
		DhenupaRequestQue.getInstance(this).getRequestQueue().add(req);
	}

	public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
		int width = image.getWidth();
		int height = image.getHeight();

		float bitmapRatio = (float) width / (float) height;
		if (bitmapRatio > 0) {
			width = maxSize;
			height = (int) (width / bitmapRatio);
		} else {
			height = maxSize;
			width = (int) (height * bitmapRatio);
		}
		return Bitmap.createScaledBitmap(image, width, height, true);
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

	public static String encodeTobase64(Bitmap image) {
		Bitmap immagex = image;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

		Log.e("LOOK", imageEncoded);
		return imageEncoded;
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
			sendData();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
