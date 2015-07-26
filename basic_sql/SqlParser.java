package studio.dexter.basic_sql;

import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.TreeMap;

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
    public TreeMap<String, TreeMap<String, String>> getTables(Context context, String[] filename) {
        TreeMap<String, TreeMap<String, String>> tables = new TreeMap<String, TreeMap<String, String>>();
        for (String name : filename) {
            TreeMap<String, String> map = parserFileFromAssets(context, name);
            tables.put(map.get(Keys.TABLE_NAME), map);
        }
        return tables;
    }


    private TreeMap<String, String> parserFileFromAssets(Context context, String filename) {
//        file format:
//        xxx,xxx
//        xxx, xxx
//        ...
        TreeMap<String, String> map = new TreeMap<String, String>();
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
                if (TextUtils.isEmpty(str)) continue;
                temp = str.split(",");
                temp[0] = temp[0].trim();
                temp[1] = filterNote(temp[1]);
                temp[1] = temp[1].trim();
                temp[1] = toUpperWithCheck(temp[0], temp[1]);
                map.put(temp[0], temp[1]);
            }

            map = addReservedFields(map);

            if (showLog)
                for (String str : map.keySet()) {
                    MLog.i(BasicSql.class, str + ":" + map.get(str));
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
        if (!map.keySet().contains(Keys.EMPTY)) return map;
        int addQuantity = Integer.valueOf(map.get(Keys.EMPTY).trim());
        String field = "field";
        if (map.keySet().contains(Keys.FIELD)) field = map.get(Keys.FIELD);
        for (int i = 0; i < addQuantity; i++) {
            map.put(field.toLowerCase() + "_" + i, "TEXT");
        }
        return map;
    }


    /**
     * \
     *
     * @param tableMap
     * @return database execute create table command string.
     */
    public String parserCreateTableExecToString(TreeMap<String, String> tableMap) {
        if (tableMap == null)
            throw new RuntimeException("non table map:BasicSql.getNewTableField() /or key/value is error.");
        if (!tableMap.containsKey(Keys.TABLE_NAME))
            throw new RuntimeException("non table name:BasicSql.getNewTableField() /or key/value is error.");
        String tableName = tableMap.get(Keys.TABLE_NAME);
        if (tableMap.containsKey(Keys._ID)) tableMap.remove(Keys._ID);
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
        if (key.equals(Keys._ID)) return str;
        if (key.equals(Keys.DB)) return str;
        if (key.equals(Keys.TABLE_NAME)) return str;
        if (key.equals(Keys.EMPTY)) return str;
        if (key.equals(Keys.FIELD)) return str;
        return str.toUpperCase();
    }

    private boolean dontAddMapCheck(String key) {
        if (key.equals(Keys._ID)) return true;
        if (key.equals(Keys.TABLE_NAME)) return true;
        if (key.equals(Keys.DB)) return true;
        if (key.equals(Keys.EMPTY)) return true;
        if (key.equals(Keys.FIELD)) return true;
        return false;
    }
}
