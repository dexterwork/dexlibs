package studio.dexter.basic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dexter on 2015/7/25.
 */
public class BasicSql extends SQLiteOpenHelper {
    protected HashMap<String, HashMap<String, String>> tables;
    protected Context context;

    public BasicSql(Context context, String dbName, ArrayList<String> tableFilesName, int version) {
        super(context, dbName, null, version);
        this.context = context;
        tables = getTableMaps(context, tableFilesName);
    }

    private HashMap<String, HashMap<String, String>> getTableMaps(Context ctx, ArrayList<String> tableFileName) {
        SqlParser sqlParser = new SqlParser();
        return sqlParser.getTables(ctx, tableFileName);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        SqlParser sqlParser = new SqlParser();
        for (HashMap<String, String> map : tables.values())
            db.execSQL(sqlParser.parserCreateTableExecToString(map));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
