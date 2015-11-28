package com.dhenupa.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dhenupa.activity.R;
import com.dhenupa.model.DonorList;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by narpr05 on 6/20/2015.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "donorlist.db";
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the StockList table
    private Dao<DonorList, Integer> donorListDao = null;

    private RuntimeExceptionDao<DonorList, Integer> donorListRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, DonorList.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
    }

    /**
     * Returns the Database Access Object (DAO) for our StockList class. It will create it or just give the cached
     * value.
     */
    public Dao<DonorList, Integer> getDao() throws SQLException {
        if (donorListDao == null) {
            donorListDao = getDao(DonorList.class);
        }
        return donorListDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our StockList class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<DonorList, Integer> getDonorListDao() {
        if (donorListRuntimeDao == null) {
            donorListRuntimeDao = getRuntimeExceptionDao(DonorList.class);
        }
        return donorListRuntimeDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        donorListDao = null;
        donorListRuntimeDao = null;
    }}