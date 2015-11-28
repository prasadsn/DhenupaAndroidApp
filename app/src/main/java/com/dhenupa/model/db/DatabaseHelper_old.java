package com.dhenupa.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dhenupa.activity.R;
import com.dhenupa.model.Donor;
import com.dhenupa.model.DonorList;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by narpr05 on 6/20/2015.
 */

public class DatabaseHelper_old extends OrmLiteSqliteOpenHelper {

    private String  user_id = "userid";
    private String name = "name";
    private String address = "address";
    private String area = "area";
    private String city = "city";
    private String contactNumber = "contactNumber";
    private String donationType = "donationType";
    private String amountrs = "amount";
    private String emailId = "emailId";
    private String rashi= "rashi";
    private String nakshatra = "nakshatra";
    private String gothra = "gothra";
    private String job = "job";
    private String registeredDate = "registeredDate";
    private String dob = "dob";
    private String imagePath = "imagePath";
    private String table_name = "Donor_List";


    public DatabaseHelper_old(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        String CREATE_DODOR_TABLE = "CREATE TABLE " + table_name + " (" + user_id + " INTEGER AUTOINCREMENT, " + name + " VARCHAR(255));";
        sqLiteDatabase.execSQL(CREATE_DODOR_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table_name);

        onCreate(sqLiteDatabase);
    }

    void addContact(Donor donor) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(user_id, donor.getUserid()); // Contact Name
        values.put(name, donor.getName()); // Contact Phone

        // Inserting Row
        db.insert(table_name, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public List<Donor> getAllContacts() {
        List<Donor> contactList = new ArrayList<Donor>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + table_name;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Donor donor = new Donor();
                donor.setUserid(Integer.parseInt(cursor.getString(0)));
                donor.setName(cursor.getString(1));
                //donor.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(donor);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    /*private static final String DATABASE_NAME = "donorlist.db";
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the StockList table
    private Dao<DonorList, Integer> donorListDao = null;

    private RuntimeExceptionDao<DonorList, Integer> donorListRuntimeDao = null;

    public DatabaseHelper_old(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    *//**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     *//*
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper_old.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, DonorList.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper_old.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    *//**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     *//*
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
    }

    *//**
     * Returns the Database Access Object (DAO) for our StockList class. It will create it or just give the cached
     * value.
     *//*
    public Dao<DonorList, Integer> getDao() throws SQLException {
        if (donorListDao == null) {
            donorListDao = getDao(DonorList.class);
        }
        return donorListDao;
    }
    *//**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our StockList class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     *//*
    public RuntimeExceptionDao<DonorList, Integer> getDonorListDao() {
        if (donorListRuntimeDao == null) {
            donorListRuntimeDao = getRuntimeExceptionDao(DonorList.class);
        }
        return donorListRuntimeDao;
    }

    *//**
     * Close the database connections and clear any cached DAOs.
     *//*
    @Override
    public void close() {
        super.close();
        donorListDao = null;
        donorListRuntimeDao = null;

    }*/
}