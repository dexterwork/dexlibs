package studio.dexter.sqlite;

import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.TreeMap;

import studio.dexter.tools.MLog;

/**
 * Created by Dexter on 2015/7/25.
 */
public class SqlParser {
    private final boolean SHOW_CREATE_LOG = false;

    /**
     * @param context
     * @param filename the file in assets folder.
     * @return HashMap(TableName, FieldName and Typed)
     */
    public TreeMap<String, TreeMap<String, String>> getTables(Context context, String[] filename) {
        TreeMap<String, TreeMap<String, String>> tables = new TreeMap<String, TreeMap<String, String>>();
        for (String name : filename) {
            TreeMap<String, String> map = parserFileFromAssets(context, name);
            tables.put(map.get(SqlKeys.TABLE_NAME), map);
        }
        return tables;
    }


    private TreeMap<String, String> parserFileFromAssets(Context context, String filename) {
        TreeMap<String, String> map = new TreeMap<String, String>();
        InputStream input = null;
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
                if (TextUtils.isEmpty(str)) continue;
                temp = str.split(",");
                temp[0] = temp[0].trim();
                temp[1] = filterNote(temp[1]);
                temp[1] = temp[1].trim();
                temp[1] = toUpperWithCheck(temp[0], temp[1]);
                map.put(temp[0], temp[1]);
            }

            map = addReservedFields(map);

            if (SHOW_CREATE_LOG) {
                String strLog = "";
                for (String str : map.keySet()) {
                    strLog += str + ":" + map.get(str) + "\n";
                }
                MLog.i(BasicSql.class, strLog);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private String filterNote(String str) {
        if (!str.contains("/")) return str;
        return str.substring(0, str.indexOf("/") - 1);
    }

    /**
     * 增加保留欄位
     *
     * @param map
     * @return
     */
    private TreeMap<String, String> addReservedFields(TreeMap<String, String> map) throws RuntimeException {
        if (!map.keySet().contains(SqlKeys.EMPTY)) return map;
        int addQuantity = Integer.valueOf(map.get(SqlKeys.EMPTY).trim());
        String field = "field";
        if (map.keySet().contains(SqlKeys.FIELD)) field = map.get(SqlKeys.FIELD);
        for (int i = 0; i < addQuantity; i++) {
            map.put(field.toLowerCase() + "_" + i, "TEXT");
        }
        return map;
    }


    /**
     * @param tableMap
     * @return database execute create table command string.
     */
    public String parserCreateTableExecToString(TreeMap<String, String> tableMap) {
        if (tableMap == null)
            throw new RuntimeException("non table map:BasicSql.getNewTableField() /or key/value is error.");
        if (!tableMap.containsKey(SqlKeys.TABLE_NAME))
            throw new RuntimeException("non table name:BasicSql.getNewTableField() /or key/value is error.");
        String tableName = tableMap.get(SqlKeys.TABLE_NAME);
        if (tableMap.containsKey(SqlKeys._ID)) tableMap.remove(SqlKeys._ID);
        String createTable = "create table " + tableName + "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,";

        Iterator it = tableMap.keySet().iterator();
        String key = "";
        while (it.hasNext()) {
            key = (String) it.next();
            if (dontAddMapCheck(key)) continue;
            createTable += key + " " + tableMap.get(key);
            if (it.hasNext()) createTable += ",";
        }

        if (createTable.substring(createTable.length() - 1).equals(","))
            createTable = createTable.substring(0, createTable.length() - 1);


        MLog.d(this, createTable + ");");
        return createTable + ");";
    }

    private String toUpperWithCheck(String key, String str) {
        if (key.equals(SqlKeys._ID)) return str;
        if (key.equals(SqlKeys.DB)) return str;
        if (key.equals(SqlKeys.TABLE_NAME)) return str;
        if (key.equals(SqlKeys.EMPTY)) return str;
        if (key.equals(SqlKeys.FIELD)) return str;
        return str.toUpperCase();
    }

    private boolean dontAddMapCheck(String key) {
        if (key.equals(SqlKeys._ID)) return true;
        if (key.equals(SqlKeys.TABLE_NAME)) return true;
        if (key.equals(SqlKeys.DB)) return true;
        if (key.equals(SqlKeys.EMPTY)) return true;
        if (key.equals(SqlKeys.FIELD)) return true;
        return false;
    }
}
