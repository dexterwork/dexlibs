package studio.dexter.sqlite;

import android.content.Context;

import java.io.IOException;

/**
 * Created by Dexter on 2015/7/26.
 */
public class SqlManager {
    BasicSql sql;

    /**
     * create database if does not exist.
     *
     * @param context
     * @param assetsFolderName
     * @return
     */
    public void initDatabase(Context context, String assetsFolderName) {
        initDatabase(context, SqlKeys.DB_NAME, assetsFolderName);
    }

    /**
     * create database if does not exist.
     *
     * @param context
     * @param dbName
     * @param assetsFolderName
     */
    public void initDatabase(Context context, String dbName, String assetsFolderName) {
        try {
            String[] tableFileList = context.getAssets().list(assetsFolderName);
            if (tableFileList != null && tableFileList.length > 0) {
                for (int i = 0; i < tableFileList.length; i++) {
                    tableFileList[i] = assetsFolderName + "/" + tableFileList[i];
                }
                sql = new BasicSql(context, dbName, tableFileList);
                sql.getReadableDatabase();
                sql.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * create databases if does not exist.
     *
     * @param context
     * @param dbName
     * @param assetsFolderName
     */
    public void initDatabase(Context context, String[] dbName, String[] assetsFolderName) {
        if (dbName.length != assetsFolderName.length)
            throw new RuntimeException("database quantity and asset table folder no match.");
        for (int index = 0; index < dbName.length; index++) {
            initDatabase(context, dbName[index], assetsFolderName[index]);
        }
    }
}
