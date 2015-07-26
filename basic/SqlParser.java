package studio.dexter.basic;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import studio.dexter.container.Keys;
import studio.dexter.tools.MLog;

/**
 * Created by Dexter on 2015/7/25.
 */
public class SqlParser {

    /**
     * @param context
     * @param filename the file in assets folder.
     * @return HashMap(TableName, FieldName and Typed)
     */
    public HashMap<String, HashMap<String, String>> getTables(Context context, ArrayList<String> filename) {
        HashMap<String, HashMap<String, String>> tables = new HashMap<String, HashMap<String, String>>();
        for (String name : filename) {
            HashMap<String, String> map = parserFileFromAssets(context, name);
            tables.put(map.get(Keys.TABLE_NAME), map);
        }
        return tables;
    }


    private HashMap<String, String> parserFileFromAssets(Context context, String filename) {
//        file format:
//        xxx,xxx
//        xxx, xxx
//        ...
        HashMap<String, String> map = new HashMap<String, String>();
        InputStream input = null;
        boolean showLog = true;
        try {
            input = context.getAssets().open(filename);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();
            String getStrings = new String(buffer);

            getStrings = getStrings.replace("\r\n", ":");
            String[] array = getStrings.split(":");

            String[] temp = null;
            for (String str : array) {
                temp = str.split(",");
                temp[0] = temp[0].trim();
                temp[1] = temp[1].trim();
                temp[1] = toUpperWithCheck(temp[0], temp[1]);
                map.put(temp[0], temp[1]);
            }

            if (showLog)
                for (String str : map.keySet()) {
                    MLog.i(BasicSql.class, str + ":" + map.get(str));
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * \
     *
     * @param tableMap
     * @return database execute create table command string.
     */
    public String parserCreateTableExecToString(HashMap<String, String> tableMap) {
        if (tableMap == null)
            throw new RuntimeException("non table map:BasicSql.getNewTableField()");
        if (!tableMap.containsKey(Keys.TABLE_NAME))
            throw new RuntimeException("non table name:BasicSql.getNewTableField()");
        String tableName = tableMap.get(Keys.TABLE_NAME);
        if (tableMap.containsKey(Keys._ID)) tableMap.remove(Keys._ID);
        String createTable = "create table " + tableName + "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,";

        Iterator it = tableMap.keySet().iterator();
        String key = "";
        while (it.hasNext()) {
            key = (String) it.next();
            if (key.equals(Keys.TABLE_NAME) || key.equals(Keys.DB)) continue;
            createTable += key + " " + tableMap.get(key);
            if (it.hasNext()) createTable += ",";
        }

        if (createTable.substring(createTable.length() - 1).equals(","))
            createTable = createTable.substring(0, createTable.length() - 1);


        MLog.d(this, createTable + ");");
        return createTable + ");";
    }

    private String toUpperWithCheck(String key, String str) {
        if (key.equals(Keys._ID)) return str;
        if (key.equals(Keys.DB)) return str;
        if (key.equals(Keys.TABLE_NAME)) return str;
        return str.toUpperCase();
    }
}
