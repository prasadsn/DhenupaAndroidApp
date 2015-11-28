package com.dhenupa.model.db;


import com.dhenupa.model.DonorList;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by narpr05 on 6/20/2015.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[]{DonorList.class};

    public static void main(String[] args) throws SQLException, IOException {
        //writeConfigFile("C:/narpr05/AndroidStudioProjects/DhenupaAndroidApp/app/src/main/res/raw/ormlite_config.txt", classes);
        writeConfigFile("ormlite_config.txt", classes);
    }
}