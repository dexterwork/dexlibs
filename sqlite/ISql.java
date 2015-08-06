package studio.dexter.basic_sql;

import android.content.Context;

/**
 * Created by Dexter on 2015/7/29.
 */
public interface ISql {

    interface ISqlManager {
        /**
         * create database if does not exist.
         *
         * @param context
         * @param dbName
         * @param assetsFolderName
         * @return
         */
        void createDatabase(Context context, String dbName, String assetsFolderName);

        /**
         * create databases if does not exist.
         *
         * @param context
         * @param dbName
         * @param assetsFolderName
         */
        void createDatabase(Context context, String[] dbName, String[] assetsFolderName);
    }
}
