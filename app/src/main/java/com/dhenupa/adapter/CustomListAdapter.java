package com.dhenupa.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dhenupa.activity.R;
import com.dhenupa.network.DhenupaRequestQue;
import com.dhenupa.network.DonorManager;
import com.dhenupa.util.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by prasad on 18-10-2015.
 */
public class CustomListAdapter extends CursorAdapter {
    Context mContext;
    LayoutInflater inflater;

    public CustomListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }
   /* private List<DummyDataObjs> mainDataList = null;
    private ArrayList<DummyDataObjs> arraylist;*/

    public static class ViewHolder {
        public String _id;
        protected String userId;
        protected TextView name;
        protected TextView number;
        protected TextView dob;
        protected ImageView image;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View convertView = inflater.inflate(R.layout.donor_list_items, parent, false);

            holder.name = (TextView) convertView.findViewById(R.id.nameid);
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.dob = (TextView) convertView.findViewById(R.id.doj);
            holder.image = (ImageView) convertView.findViewById(R.id.networkImageView);

            convertView.setTag(holder);
            convertView.setTag(R.id.nameid, holder.name);
            convertView.setTag(R.id.number, holder.number);
            convertView.setTag(R.id.doj, holder.number);
            convertView.setTag(R.id.networkImageView, holder.image);
            holder = (ViewHolder) convertView.getTag();
        return convertView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(cursor.getString(cursor.getColumnIndex(DonorManager.COL_NAME)));
        holder.number.setText(cursor.getString(cursor.getColumnIndex("contactNumber")));
        holder.dob.setText(cursor.getString(cursor.getColumnIndex(DonorManager.COL_DOB)));

        String userId = cursor.getString(cursor.getColumnIndex(DonorManager.COL_USERID));
        holder.userId = userId;
        holder._id = cursor.getString(cursor.getColumnIndex(DonorManager.COL_ID));
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Dhenupa");
        if(!file.exists())
            file.mkdir();
        final String imgFileName = Environment.getExternalStorageDirectory() + File.separator + "Dhenupa"
                + File.separator + cursor.getString(cursor.getColumnIndex("contactNumber")) + "_" + cursor.getString(cursor.getColumnIndex("name")) + ".jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(imgFileName);
        if(bitmap!=null){
            holder.image.setImageBitmap(bitmap);
            return;
        }

        String imgUrl = DhenupaRequestQue.SERVER_URL + "/MobileDhenupaServlet?action=getPic&userid="+ userId;
        //holder.image.setImageUrl(imgUrl, DhenupaRequestQue.getInstance(mContext).getImageLoader());
        //holder.image.setImageBitmap(getBitmapFromURL(imgUrl));
        StringRequest request = new StringRequest(imgUrl, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                int indexBase64 = 0;
                if( (indexBase64 = response.indexOf("base64,")) > 0)
                    response = response.substring(indexBase64 + "base64,".length());
                byte[] decodedString = Base64.decode(response.getBytes(), Base64.DEFAULT);
                //decodedString = response.getBytes();
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.image.setImageBitmap(bitmap);
                if(bitmap != null)
                    Utility.savePic(bitmap, imgFileName);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        DhenupaRequestQue.getInstance(context).getRequestQueue().add(request);
    }


}
