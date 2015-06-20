package studio.dexter.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import studio.dexter.bundles.DBCreateBundle;
import studio.dexter.bundles.DayExpendBundle;
import studio.dexter.bundles.ExpendBranchBundle;
import studio.dexter.bundles.ExpendCategoryBundle;
import studio.dexter.enums.DBString;
import studio.dexter.pub.KeyStrings;
import studio.dexter.settings.SystemSetting;
import studio.dexter.tools.DBStringFactory;
import studio.dexter.tools.MLog;

/**
 * Created by dexter on 2015/4/11.
 */
public class BaseSqlite extends SQLiteOpenHelper {
    protected SQLiteDatabase db;
    protected String table;

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     */
    protected BaseSqlite(Context context) {
        super(context, KeyStrings.DB_NAME, null, SystemSetting.DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        createExpendCategoryTable(db);
        createExpendBranchTable(db);
        createDayExpendTable(db);
    }

    private void createExpendBranchTable(SQLiteDatabase db) {
        ExpendBranchBundle bundle = new ExpendBranchBundle();
        createTable(db, bundle.getDBStringBundle());
    }

    private void createExpendCategoryTable(SQLiteDatabase db) {
        ExpendCategoryBundle bundle = new ExpendCategoryBundle();
        createTable(db, bundle.getDBStringBundle());
    }

    private void createDayExpendTable(SQLiteDatabase db) {
        DayExpendBundle dayExpendBundle = new DayExpendBundle();
        createTable(db, dayExpendBundle.getDBStringBundle());
    }

    private void createTable(SQLiteDatabase db, ArrayList<DBCreateBundle> list) {
        String dbString = DBStringFactory.getDBCreateString(list);
        db.execSQL(dbString);
    }


    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openWritable() {
        db = getWritableDatabase();
    }

    public void openReadable() {
        db = getReadableDatabase();
    }


    public void close() {
        db.close();
    }

    public void setTableName(ArrayList<DBCreateBundle> list) {
        for (DBCreateBundle b : list) {
            if (b.getType() == DBString.TABLE) {
                this.table = b.getName();
                return;
            }
        }
    }
}
