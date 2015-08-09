package studio.dexter.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.TreeMap;

/**
 * Created by Dexter on 2015/7/25.
 */
public class BasicSql extends SQLiteOpenHelper {
    protected TreeMap<String, TreeMap<String, String>> tables;
    protected Context context;

    public BasicSql(Context context, String dbName, String tableFilesName) {
        super(context, dbName, null, SqlKeys.DB_VERSION);
        this.context = context;
        tables = getTableMaps(context, tableFilesName);
    }

    public BasicSql(Context context, String dbName, String[] tableFilesName) {
        super(context, dbName, null, SqlKeys.DB_VERSION);
        this.context = context;
        tables = getTableMaps(context, tableFilesName);
    }

    private TreeMap<String, TreeMap<String, String>> getTableMaps(Context ctx, String tableFileName) {
        SqlParser sqlParser = new SqlParser();
        return sqlParser.getTables(ctx, tableFileName);
    }

    private TreeMap<String, TreeMap<String, String>> getTableMaps(Context ctx, String[] tableFileName) {
        SqlParser sqlParser = new SqlParser();
        return sqlParser.getTables(ctx, tableFileName);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        SqlParser sqlParser = new SqlParser();
        for (TreeMap<String, String> map : tables.values())
            db.execSQL(sqlParser.parserCreateTableExecToString(map));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
