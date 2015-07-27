package studio.dexter.basic_sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.TreeMap;

import studio.dexter.basic_sql.SqlParser;

/**
 * Created by Dexter on 2015/7/25.
 */
public class BasicSql extends SQLiteOpenHelper {
    protected TreeMap<String, TreeMap<String, String>> tables;
    protected Context context;

    public BasicSql(Context context, String dbName, String[] tableFilesName, int version) {
        super(context, dbName, null, version);
        this.context = context;
        tables = getTableMaps(context, tableFilesName);
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
