package com.dhenupa.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhenupa.activity.R;

import java.util.ArrayList;
import java.util.List;

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

    static class ViewHolder {
        protected TextView name;
        protected TextView number;
        protected TextView email;
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
            holder.number = (TextView) convertView.findViewById(R.id.location);
            holder.email = (TextView) convertView.findViewById(R.id.doj);

            convertView.setTag(holder);
            convertView.setTag(R.id.imageView1,holder.image);
            convertView.setTag(R.id.nameid, holder.name);
            convertView.setTag(R.id.location, holder.number);
            convertView.setTag(R.id.doj, holder.number);
            holder = (ViewHolder) convertView.getTag();
        return convertView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(cursor.getString(cursor.getColumnIndex("name")));
        holder.number.setText(cursor.getString(cursor.getColumnIndex("address")));
        holder.email.setText(cursor.getString(cursor.getColumnIndex("dob")));

    }
}
