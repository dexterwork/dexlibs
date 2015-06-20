package studio.dexter.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import studio.dexter.base.BaseSqlite;
import studio.dexter.bundles.DayExpendBundle;
import studio.dexter.pub.KeyStrings;

/**
 * 日支出資料庫
 * Created by dexter on 2015/4/10.
 */
public class DayExpendSql extends BaseSqlite {

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     */
    public DayExpendSql(Context context) {
        super(context);
        this.table = KeyStrings.TABLE_DAY_EXPEND;
    }

    //TODO
    public ArrayList<DayExpendBundle> getDayExpendBundle(String date) {
        ArrayList<DayExpendBundle> list = new ArrayList<DayExpendBundle>();
        Cursor cursor = db.query(table, getAllColumns(), KeyStrings.EXPEND_DATE + " = ?", new String[]{date}, null, null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return list;
        }
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            DayExpendBundle bundle = new DayExpendBundle();
            bundle.setIndex(cursor.getInt(cursor.getColumnIndex(KeyStrings.INDEX)));
            bundle.setAccount(cursor.getString(cursor.getColumnIndex(KeyStrings.ACCOUNT)));
            bundle.setExpendCategory(cursor.getString(cursor.getColumnIndex(KeyStrings.EXPEND_CATEGORY)));
            bundle.setExpendBranch(cursor.getString(cursor.getColumnIndex(KeyStrings.EXPEND_BRANCH)));
            bundle.setExpendDate(cursor.getString(cursor.getColumnIndex(KeyStrings.EXPEND_DATE)));
            bundle.setRecordData(cursor.getString(cursor.getColumnIndex(KeyStrings.RECORD_DATE)));
            bundle.setMoney(cursor.getFloat(cursor.getColumnIndex(KeyStrings.MONEY)));
            bundle.setExpendType(cursor.getString(cursor.getColumnIndex(KeyStrings.EXPEND_TYPE)));
            bundle.setProject(cursor.getString(cursor.getColumnIndex(KeyStrings.PROJECT)));
            bundle.setNote(cursor.getString(cursor.getColumnIndex(KeyStrings.NOTE)));
            bundle.setStore(cursor.getString(cursor.getColumnIndex(KeyStrings.STORE)));
            list.add(bundle);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    private String[] getAllColumns() {
        return new String[]{KeyStrings.INDEX, KeyStrings.ACCOUNT, KeyStrings.EXPEND_CATEGORY, KeyStrings.EXPEND_BRANCH
                , KeyStrings.EXPEND_DATE, KeyStrings.RECORD_DATE, KeyStrings.MONEY, KeyStrings.EXPEND_TYPE, KeyStrings.STORE
                , KeyStrings.PROJECT, KeyStrings.NOTE
        };
    }

    public void addDayExpend(ArrayList<DayExpendBundle> bundles) {
        for (DayExpendBundle b : bundles) addDayExpend(b);
    }

    public void addDayExpend(DayExpendBundle bundle) {
        if (!db.isOpen() || db.isReadOnly()) openWritable();
        ContentValues cv = new ContentValues();
        cv.put(KeyStrings.INDEX, bundle.getIndex());
        cv.put(KeyStrings.ACCOUNT, bundle.getAccount());
        cv.put(KeyStrings.EXPEND_CATEGORY, bundle.getExpendCategory());
        cv.put(KeyStrings.EXPEND_BRANCH, bundle.getExpendBranch());
        cv.put(KeyStrings.EXPEND_DATE, bundle.getExpendDate());
        cv.put(KeyStrings.RECORD_DATE, bundle.getRecordData());
        cv.put(KeyStrings.MONEY, bundle.getMoney());
        cv.put(KeyStrings.EXPEND_TYPE, bundle.getExpendType());
        cv.put(KeyStrings.STORE, bundle.getStore());
        cv.put(KeyStrings.PROJECT, bundle.getProject());
        cv.put(KeyStrings.NOTE, bundle.getNote());
        db.insert(table, null, cv);
    }


}
