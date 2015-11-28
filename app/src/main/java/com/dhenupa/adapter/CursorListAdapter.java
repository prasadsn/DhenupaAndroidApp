package com.dhenupa.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhenupa.activity.R;

/**
 * Created by prasad on 18-10-2015.
 */
public class CursorListAdapter extends CursorAdapter {
    Context context;
    Cursor cursor;
    public CursorListAdapter(Context context, Cursor c) {
        super(context, c);
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.donor_list_items, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
        TextView name = (TextView) view.findViewById(R.id.nameid);
        TextView location = (TextView) view.findViewById(R.id.location);
        TextView doj = (TextView) view.findViewById(R.id.doj);

        String dname = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
        String ddoj = cursor.getString(cursor.getColumnIndexOrThrow("dob"));

        name.setText(dname);
        name.setText(address);
        name.setText(ddoj);

    }
}
