package dmcfarland001.glucose.greenglucose.moneyplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Daniel on 6/4/2015.
 *
 *
 * Not used. Scrap
 *
 */
public class TickerSQLTable {

    public static final String KEY_ROWID = "_idle";
    public static final String KEY_BASE = "basele";
    public static final String KEY_ADD = "addle";


    private static final String DATABASE_NAME = "TickerSaveDatabse";
    private static final String DATABASE_TABLE = "TickeSaveTable";
    private static final int DATABASE_VERSION = 1;


    private DbHelper dbHelper;
    private Context dbContext;
    private SQLiteDatabase dbDatabase;


    public static final String CREATE_FIRST_TABLE = "CREATE TABLE "
            + DATABASE_TABLE + " (" + KEY_ROWID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_BASE
            + " TEXT NOT NULL, " + KEY_ADD
            + " TEXT NOT NULL " + ")";

///    public static final String CREATE_FIRST_TABLE = "CREATE TABLE "
//            + DATABASE_TABLE + " (" + KEY_ROWID
  //          + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NOTE
  //          + " TEXT NOT NULL, " + KEY_VALUE + " TEXT NOT NULL, "
   //         + KEY_CREATED_AT + " DATETIME " + ")";

    public static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_FIRST_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);

        }

    }

    public TickerSQLTable(Context c) {
        dbContext = c;
    }

    // going to set up the database
    public TickerSQLTable open() throws SQLException {

        dbHelper = new DbHelper(dbContext);
        dbDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbDatabase.close();
    }

    public void updateEntry(long id, String base, String add) {


        ContentValues cvUpdate = new ContentValues();

        cvUpdate.put(KEY_BASE, base);

        Log.d("SQLMoneyIn", "updateEntry 1");

        cvUpdate.put(KEY_ADD, add);

        dbDatabase.update(DATABASE_TABLE, cvUpdate, KEY_ROWID + "=" + id, null);

    }

    // // Create an entry by a given date from the date picker.
    public long createEntry(String base, String id) {

        ContentValues cv = new ContentValues();

        cv.put(KEY_BASE, base);
        cv.put(KEY_ADD, id);

        return dbDatabase.insert(DATABASE_TABLE, null, cv);

    }

    public String getBase() {

        int iBase;

        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_BASE}, null, null, null, null, null);

        String str = "000.00";

        if (c.getCount() > 0) {

            iBase = c.getColumnIndex(KEY_BASE);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
                str = c.getString(iBase);

        }

        c.close();
        return str;

    }

    public String getAdder() {

        int iAdd;

        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_ADD}, null, null, null, null, null);

        String str = "";

        if (c.getCount() > 0) {

            iAdd = c.getColumnIndex(KEY_ADD);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
                str = c.getString(iAdd);

        }

        c.close();
        return str;

    }
}
