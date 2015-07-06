package dmcfarland001.glucose.greenglucose.moneyplanner;

import hirondelle.date4j.DateTime;

import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * @author
 * @copywright Glucose Software
 * <p/>
 * Daniel McFarland DMcFarland001@gmail.com "Green Glucose Inc."
 * "Green Glucose" This is an Android application that uses
 * statistics to map out a future bank account. This information is
 * meant only for informational purposes and no responsibility's are
 * on me the author of this application. I take no responsibility
 * and have no liability for any information displayed on or in this
 * application. Only intention for this application is to guess
 * information.
 */


/**
 * List of all calls.
 * <p>
 * String getData()
 */


public class SQLMoneyIn {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NOTE = "note";
    public static final String KEY_VALUE = "value";
    private static final String KEY_CREATED_AT = "created_at";

    private static final String DATABASE_NAME = "FirstWalletValuesIn";
    private static final String DATABASE_TABLE = "FirstMoneyTableIn";
    private static final int DATABASE_VERSION = 1;


    private DbHelper dbHelper;
    private Context dbContext;
    private SQLiteDatabase dbDatabase;


    public static final String CREATE_FIRST_TABLE = "CREATE TABLE "
            + DATABASE_TABLE + " (" + KEY_ROWID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NOTE
            + " TEXT NOT NULL, " + KEY_VALUE + " TEXT NOT NULL, "
            + KEY_CREATED_AT + " DATETIME " + ")";


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

    public SQLMoneyIn(Context c) {
        dbContext = c;
    }

    // going to set up the database
    public SQLMoneyIn open() throws SQLException {

        dbHelper = new DbHelper(dbContext);
        dbDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbDatabase.close();
    }


    public void deleteDataBase() {
        dbContext.deleteDatabase(DATABASE_NAME);
    }


    /*

            Used in the raw in table. About->table_in_all


    */
    public String getData() {

        String[] columns = new String[]{KEY_ROWID, KEY_NOTE, KEY_VALUE,
                KEY_CREATED_AT};

        Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
                null, null);

        String result = "";

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iNote = c.getColumnIndex(KEY_NOTE);
        int iValue = c.getColumnIndex(KEY_VALUE);
        int iDateTime = c.getColumnIndex(KEY_CREATED_AT);

        String temp, tmp, temp_m, temp_d, temp_y, dateForm;

        DateTime yo;

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            // YYYY-MM-DD as stored in database.

            temp = c.getString(iDateTime);

            tmp = temp.replace("-", " ");

            temp_y = tmp.substring(0, 4);
            temp_m = tmp.substring(5, 7);
            temp_d = tmp.substring(8, 10);


            // YYYY-MM-DD   Format for view.

            yo = DateTime.forDateOnly(Integer.valueOf(temp_y),
                    Integer.valueOf(temp_m), Integer.valueOf(temp_d));

            dateForm = yo.format("MM-DD-YYYY");

            result = result + c.getString(iRow) + "\t" + c.getString(iNote)
                    + "\t\t" + c.getString(iValue) + "\t\t" + dateForm + "\n";
        }

        c.close();

        return result;
    }


    /*

            Gets,
                    ID number  &  Notes

            No Sort

            SQL Numbers View In
    */
    public String getNotes() {

        // Tidy this up. no call for value or date.
        String[] columns = new String[]{KEY_ROWID, KEY_NOTE, KEY_VALUE,
                KEY_CREATED_AT};


        Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
                null, null);

        String result = "";


        int iRow = c.getColumnIndex(KEY_ROWID);
        int iNote = c.getColumnIndex(KEY_NOTE);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            result = result + c.getString(iRow) + " " + c.getString(iNote)
                    + "\n";

        }

        c.close();

        return result;
    }


    /*
            Gets a list of all the values.

            No Sort


    */
    public String getValues() {

        String[] columns = new String[]{KEY_ROWID, KEY_NOTE, KEY_VALUE,
                KEY_CREATED_AT};


        Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
                null, null);


        String result = "";


        int iValue = c.getColumnIndex(KEY_VALUE);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            result = result + c.getString(iValue) + "\n";
        }


        c.close();

        return result;
    }

    /*
            Gets a list of all the dates

            No sort



    */
    public String getDates() {


        String[] columns = new String[]{KEY_ROWID, KEY_NOTE, KEY_VALUE,
                KEY_CREATED_AT};


        Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
                null, null);


        String result = "";


        int iDateTime = c.getColumnIndex(KEY_CREATED_AT);


        String temp, tmp, temp_m, temp_d, temp_y, dateForm;


        DateTime yo;

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            temp = c.getString(iDateTime);

            tmp = temp.replace("-", " ");

            temp_y = tmp.substring(0, 4);
            temp_m = tmp.substring(5, 7);
            temp_d = tmp.substring(8, 10);


            yo = DateTime.forDateOnly(Integer.valueOf(temp_y),
                    Integer.valueOf(temp_m), Integer.valueOf(temp_d));

            dateForm = yo.format("MM-DD-YYYY");

            result = result + dateForm + "\n";
        }

        c.close();

        return result;
    }
/*

            Takes in an ID number

            Returns a note for the ID


    */

    public String getNote(long l) {

        Log.d("SQLMoneyIn", "getNote getNote");

        String[] columns = new String[]{KEY_ROWID, KEY_NOTE, KEY_VALUE,
                KEY_CREATED_AT};

        Cursor c = dbDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
                + l, null, null, null, null);

        if (c != null) {

            c.moveToFirst();

            // I believe the 1 is for the index of the datbase call. Note = 1.
            String note = c.getString(1);

            c.close();

            return note;
        }

        return null;
    }



/*

            Takes in an ID number

            Returns a specific value.


    */

    public String getValue(long l) {

        Log.d("SQLMoneyIn", "getValue getValue");


        String[] columns = new String[]{KEY_ROWID, KEY_NOTE, KEY_VALUE,
                KEY_CREATED_AT};


        Cursor c = dbDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
                + l, null, null, null, null);


        if (c != null) {

            c.moveToFirst();

            // 2 is for the index of the database call. Value = 2.
            String value = c.getString(2);

            c.close();

            return value;
        }

        return null;
    }


    /*
                Takes in an ID number

                Returns a date.



        */
    public String getDate(long id_l_i) {


        String[] columns = new String[]{KEY_ROWID, KEY_NOTE, KEY_VALUE,
                KEY_CREATED_AT};


        Cursor c = dbDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
                + id_l_i, null, null, null, null);


        if (c != null) {

            c.moveToFirst();

            String date = c.getString(3);

            c.close();

            return date;

        }

        return null;
    }


    /*
            Takes in
                        ID, Note, Value, Date

            Updates

        */
    public void updateEntry(long l, String note, String value, String date) {


        ContentValues cvUpdate = new ContentValues();

        cvUpdate.put(KEY_NOTE, note);

        Log.d("SQLMoneyIn", "updateEntry 1");

        cvUpdate.put(KEY_VALUE, value);

        Log.d("SQLMoneyIn", "updateEntry 2");

        cvUpdate.put(KEY_CREATED_AT, date);


        dbDatabase.update(DATABASE_TABLE, cvUpdate, KEY_ROWID + "=" + l, null);

    }

    public void DeleteEntry(long ID) {


        try {

            dbDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + ID, null);

            // Define 'where' part of query.
            //String selection = KEY_ROWID + "=" + ID;

            // Specify arguments in placeholder order.
            //String[] selectionArgs = {String.valueOf(ID)};

            // Issue SQL statement.
            //dbDatabase.delete(DATABASE_TABLE, selection, selectionArgs);


        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            // dbDatabase.close();

        }

    }


    /**
     * get date-time
     */
    public String currentDateTime() {
        DateTime now = DateTime.now(TimeZone.getDefault());
        String result = now.format("YYYY-MM-DD");
        return result;
    }



    /*
            traverses all notes but since its setting them to a SET of strings

            doubles are automatically removed.


            returns a <set> list of notes with no doubles.

            No sort.

            returns a <set>
    */

    // Notes in a Set.
    public Set<String> getAllNotesIn() {

        Set<String> str = new HashSet<>();

        int iNote;

        String[] columns = new String[]{KEY_ROWID, KEY_NOTE, KEY_VALUE,
                KEY_CREATED_AT};

        Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
                null, null);

        if (c.getCount() > 0) {


            iNote = c.getColumnIndex(KEY_NOTE);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                str.add(c.getString(iNote));

            }

        }

        c.close();

        return str;
    }


    /*
            Updated calls. This is more specific. - Format to this.

            Sorts the notes by 'note' in Ascending

            Returns a String Array []

    */
    // Notes in a String[].
    public String[] SetgetAllNotesIn() {

        int i = -1;

        int iNote;


        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_NOTE}, null, null, "note", null, "note ASC");


        String[] str = new String[c.getCount()];


        if (c.getCount() > 0) {

            // Potentially instead of KEY_NOTE could use 1 for an index.
            iNote = c.getColumnIndex(KEY_NOTE);


            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {


                str[++i] = c.getString(iNote);

            }

        }
        c.close();
        return str;
    }

    /*

            Sorts the notes by 'date' in Ascending

            Returns a String Array []

            All notes.

    */
    public String[] orderByDateNotes() {

        int i = -1;

        int iNote;

        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_NOTE}, null, null, null, null, "created_at ASC");


        String[] note = new String[c.getCount()];


        if (c.getCount() > 0) {

            iNote = c.getColumnIndex(KEY_NOTE);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
                note[++i] = c.getString(iNote);

            c.close();
            return note;
        }
        c.close();
        return null;
    }


    /*

                Sorts the values by 'date' in Ascending

                Returns a String Array []

                All values.

        */
    public String[] orderByDateValues() {

        int i = -1;

        int iValue;

        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_VALUE},
                null, null, null, null, "created_at ASC");


        String[] value = new String[c.getCount()];


        if (c.getCount() > 0) {

            iValue = c.getColumnIndex(KEY_VALUE);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
                value[++i] = c.getString(iValue);

            c.close();
            return value;
        }

        c.close();
        return null;
    }


    public String[] orderByDateDate() {

        int i = -1;

        int iDate;


        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_CREATED_AT}, null, null, null, null, "created_at ASC");


        String[] date = new String[c.getCount()];


        if (c.getCount() > 0) {

            iDate = c.getColumnIndex(KEY_CREATED_AT);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
                date[++i] = c.getString(iDate);

            c.close();
            return date;
        }

        c.close();

        return null;
    }


    // // Create an entry by a given date from the date picker.
    public long createEntry_date(String note, String value, String date) {

        ContentValues cv = new ContentValues();

        cv.put(KEY_NOTE, note);
        cv.put(KEY_VALUE, value);
        cv.put(KEY_CREATED_AT, date);

        return dbDatabase.insert(DATABASE_TABLE, null, cv);

    }


    // String for list
    public String groupNameOrderDateNote() {

        int iNote;

        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_NOTE}, null, null, null, null, "note, created_at DESC");

        String result = "";

        if (c.getCount() > 0) {

            iNote = c.getColumnIndex(KEY_NOTE);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                result = result + c.getString(iNote) + "\n";
            }

            c.close();
            return result;
        }
        c.close();
        return null;
    }


    // String for list
    public String groupNameOrderDateValue() {

        int iDate;

        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_VALUE}, null, null, null, null, "note, created_at DESC");

        String result = "";

        if (c.getCount() > 0) {

            iDate = c.getColumnIndex(KEY_VALUE);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                result = result + c.getString(iDate) + "\n";
            }

            c.close();
            return result;
        }
        c.close();
        return null;
    }


    // String for list
    public String groupNameOrderDateDate() {

        int iDate;

        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_CREATED_AT}, null, null, null, null, "note, created_at DESC");

        String result = "";

        if (c.getCount() > 0) {

            iDate = c.getColumnIndex(KEY_CREATED_AT);

            String temp, tmp, temp_m, temp_d, temp_y, dateForm;

            DateTime yo;

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                temp = c.getString(iDate);

                tmp = temp.replace("-", " ");

                temp_y = tmp.substring(0, 4);
                temp_m = tmp.substring(5, 7);
                temp_d = tmp.substring(8, 10);


                yo = DateTime.forDateOnly(Integer.valueOf(temp_y),
                        Integer.valueOf(temp_m), Integer.valueOf(temp_d));

                dateForm = yo.format("MM-DD-YYYY");

                result = result + dateForm + "\n";

            }

            c.close();
            return result;
        }

        c.close();

        return null;
    }














        /*

                Look at this function.


         */


    /**
     * Minimized list for  note
     * <p>
     * <p>
     * Does this return the last or the first date?
     * <p>
     * If last, delete the entry here for 'pop'
     */

    // String for list
    public String MinViewNote() {

        Log.d("Enter MinNote ", " Enter MinNote");

        int iNote;

        //    Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_NOTE}, null, null, "note", null, "note, created_at DESC");

        //    Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_NOTE}, null, null, "note", null, "created_at, created_at ASC");

        //  Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_NOTE}, null, null, "note", null, "created_at ASC");

        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_NOTE}, null, null, "note", null, "created_at ASC");

        //   Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_NOTE}, null, null, null, null, "note, created_at ASC");

        Log.d("MinNote ", " After MinNote");

        String result = "";


        if (c.getCount() > 0) {

            Log.d("MinNote ", " c.getCount() > 0 ");

            iNote = c.getColumnIndex(KEY_NOTE);

            try {

                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {


                    Log.d("MinNote ", " for Looop.");


                    result = result + c.getString(iNote) + "\n";
                }

                c.close();
                return result;

            } catch (Exception e) {

                e.printStackTrace();
                e.printStackTrace();
                e.printStackTrace();

            }
        }

        c.close();

        return null;
    }


    String[] sorted_min_note, sorted_min_date, sorted_min_id;


    public void set_minviewpoplastdate() {

        int iNote, iDate, iID;

        // boolean to ensure only one click at a time.
        boolean truth = true;

        int i = -1, j = -1;

        String[] columns = new String[]{KEY_ROWID, KEY_NOTE, KEY_VALUE,
                KEY_CREATED_AT};


        Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null, null, "note, created_at ASC");

        Cursor b = dbDatabase.query(DATABASE_TABLE, columns, null, null, "note", null, "note, created_at ASC");


        String[] all_str_note = new String[c.getCount()];
        String[] all_str_date = new String[c.getCount()];
        String[] all_str_id = new String[c.getCount()];


        String[] min_str_note = new String[b.getCount()];
        String[] min_str_date = new String[b.getCount()];
        String[] min_str_id = new String[b.getCount()];


        // This is what will be returned.
        sorted_min_note = new String[b.getCount()];
        sorted_min_date = new String[b.getCount()];
        sorted_min_id = new String[b.getCount()];


        // Get the whole list. Group by note and order by date.
        if (c.getCount() > 0) {

            iNote = c.getColumnIndex(KEY_NOTE);
            iDate = c.getColumnIndex(KEY_CREATED_AT);
            iID = c.getColumnIndex(KEY_ROWID);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                all_str_note[++i] = c.getString(iNote);
                all_str_date[i] = c.getString(iDate);
                all_str_id[i] = c.getString(iID);
            }
        }

        c.close();

        // Get a list with no doubles in the notes.
        // This list is topped with the last inserted into the sql table. NOT the stored date.
        // The above [] IS sorted by date so get the first to match then move on. simple i j loop

        if (b.getCount() > 0) {

            iNote = b.getColumnIndex(KEY_NOTE);
            iDate = b.getColumnIndex(KEY_CREATED_AT);
            iID = b.getColumnIndex(KEY_ROWID);

            for (b.moveToFirst(); !b.isAfterLast(); b.moveToNext()) {
                min_str_note[++j] = b.getString(iNote);
                min_str_id[j] = b.getString(iID);
                min_str_date[j] = b.getString(iDate);
            }
        }

        b.close();


        for (int cntr_i = 0; cntr_i < min_str_note.length; cntr_i++)

        {

            for (int cntr_j = 0; cntr_j < all_str_note.length; cntr_j++)

            {

                if (truth && min_str_note[cntr_i].matches(all_str_note[cntr_j])) {


                    truth = false;

                    sorted_min_note[cntr_i] = all_str_note[cntr_j];
                    sorted_min_date[cntr_i] = all_str_date[cntr_j];
                    sorted_min_id[cntr_i] = all_str_id[cntr_j];


                }
                if (!min_str_note[cntr_i].matches(all_str_note[cntr_j])) {

                    truth = true;

                }

            }

        }


    }

    public String[] sorted_min_note() {

        return sorted_min_note;

    }

    public String[] sorted_min_date() {

        return sorted_min_date;
    }

    public String[] sorted_min_id() {

        return sorted_min_id;

    }


    /**
     * String array for notes
     * <p>
     * Sorted by date in ASC
     * <p>
     * Group by notes. Sort by dates
     */


    public String[] groupNameOrderDateNoteA() {

        int iNote;

        int i = -1;

        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_NOTE}, null, null, null, null, "note, created_at ASC");

        String[] str = new String[c.getCount()];

        if (c.getCount() > 0) {

            iNote = c.getColumnIndex(KEY_NOTE);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
                str[++i] = c.getString(iNote);

            c.close();
            return str;
        }

        c.close();
        return null;
    }

    /*

            The notes are grouped together then sorted by date in ASC

            This gets a String array for the 'values'


    */
    // Array values
    public String[] groupNameOrderDateValueA() {

        int iValue;

        int i = -1;

        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_VALUE}, null, null, null, null, "note, created_at ASC");

        String[] str = new String[c.getCount()];

        if (c.getCount() > 0) {

            iValue = c.getColumnIndex(KEY_VALUE);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
                str[++i] = c.getString(iValue);

            c.close();
            return str;
        }

        c.close();
        return null;

    }


    // Array values
    public String[] groupNameOrderDateDateA() {

        int iDate;

        int i = -1;

        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_CREATED_AT}, null, null, null, null, "note, created_at ASC");

        String[] str = new String[c.getCount()];

        if (c.getCount() > 0) {

            iDate = c.getColumnIndex(KEY_CREATED_AT);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
                str[++i] = c.getString(iDate);

            c.close();
            return str;
        }

        c.close();
        return null;

    }


    public String[] NoDuplicatesDates() {

        int iDate;
        int i = -1;

        // Group by date

        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_CREATED_AT}, null, null, "created_at", null, "created_at ASC");

        String[] str = new String[c.getCount()];

        if (c.getCount() > 0) {

            iDate = c.getColumnIndex(KEY_CREATED_AT);

            // Traverse the cursor.
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
                str[++i] = c.getString(iDate);

            c.close();
            return str;
        }

        c.close();
        return null;
    }


    public String[] NoDuplicatesDatesNotes() {

        int iNote;

        int i = -1;

        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_NOTE}, null, null, "created_at", null, "created_at ASC");

        String[] str = new String[c.getCount()];

        if (c.getCount() > 0) {

            iNote = c.getColumnIndex(KEY_NOTE);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
                str[++i] = c.getString(iNote);

            c.close();
            return str;
        }

        c.close();
        return null;
    }


    /*
            Scrap






    public String[] getAllValuesIn() {
        int i = -1;
        int iValue;

        String[] columns = {KEY_ROWID, KEY_NOTE, KEY_VALUE, KEY_CREATED_AT};

        Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
                null, null);

        String[] str = new String[c.getCount()];

        if (c.getCount() > 0) {
            iValue = c.getColumnIndex(KEY_VALUE);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
                str[++i] = c.getString(iValue);

            c.close();
            return str;
        }
        c.close();
        return null;
    }


    public String[] getAllDatesIn() {

        int iDate;

        int i = -1;

        String[] columns = new String[]{KEY_ROWID, KEY_NOTE, KEY_VALUE, KEY_CREATED_AT};


        Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
                null, null);

        String[] str = new String[c.getCount()];

        if (c.getCount() > 0) {

            iDate = c.getColumnIndex(KEY_CREATED_AT);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
                str[++i] = c.getString(iDate);

            c.close();
            return str;
        }
        c.close();
        return null;
    }






    // String for list
    public String MinViewValue() {

        int iValue;

        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_VALUE}, null, null, null, null, "note, created_at ASC");


        //     Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_VALUE}, null, null, null, null, "note, created_at ASC");

        // with ASC I'm getting the last input as saved to the table. It's not sorting true to the user date.


        String result = "";

        if (c.getCount() > 0) {

            iValue = c.getColumnIndex(KEY_VALUE);

            try {

                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {


                    Log.d("MinNote ", " for Looop.");


                    result = result + c.getString(iValue) + "\n";
                }

                c.close();
                return result;

            } catch (Exception e) {

                e.printStackTrace();
                e.printStackTrace();
                e.printStackTrace();

            }
        }

        c.close();

        return null;
    }




    public final class FeedReaderContract {

        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.
        public FeedReaderContract() {}

        // Inner class that defines the table contents
        public abstract class FeedEntry  implements BaseColumns {


            public static final String TABLE_NAME = "entry";
            public static final String COLUMN_NAME_ENTRY_ID = "entryid";
            public static final String COLUMN_NAME_TITLE = "title";
            public static final String COLUMN_NAME_SUBTITLE = "subtitle";


        }
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
     // Any other options for the CREATE command
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


             public long createEntry(String note, String value) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NOTE, note);
        cv.put(KEY_VALUE, value);
        cv.put(KEY_CREATED_AT, currentDateTime());
        return dbDatabase.insert(DATABASE_TABLE, null, cv);

    }


    public long addEntry(String id_a, String note, String value) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROWID, id_a);
        cv.put(KEY_NOTE, note);
        cv.put(KEY_VALUE, value);
        cv.put(KEY_CREATED_AT, currentDateTime());
        return dbDatabase.insert(DATABASE_TABLE, null, cv);

    }



     * Unused

public String[] getAllNotesInString() {

    int i = -1;
    int iNote;

    String[] columns = new String[]{KEY_ROWID, KEY_NOTE, KEY_VALUE,
            KEY_CREATED_AT};

    Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
            null, null);

    String[] str = new String[c.getCount()];

    if (c.getCount() > 0) {

        iNote = c.getColumnIndex(KEY_NOTE);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
            str[++i] = c.getString(iNote);

        return str;
    }
    c.close();
    return null;
}



    // Values in a String[].
    public String[] SetgetAllValuesIn() {

        int i = -1;

        int iValue;


        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_VALUE}, null, null, "note", null, "note ASC");


        String[] str = new String[c.getCount()];


        if (c.getCount() > 0) {

            // Potentially instead of KEY_NOTE could use 1 for an index.
            iValue = c.getColumnIndex(KEY_VALUE);


            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {


                str[++i] = c.getString(iValue);

            }

        }
        c.close();
        return str;
    }


    // Dates in a String[].
    public String[] SetgetAllDatesIn() {

        int i = -1;

        int iDate;


        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_CREATED_AT}, null, null, "note", null, "note ASC");


        String[] str = new String[c.getCount()];


        if (c.getCount() > 0) {

            // Potentially instead of KEY_NOTE could use 1 for an index.
            iDate = c.getColumnIndex(KEY_CREATED_AT);


            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {


                str[++i] = c.getString(iDate);

            }

        }
        c.close();
        return str;
    }

    // ID in a String[].
    public String[] SetgetAllIDIn() {

        int i = -1;

        int iID;


        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID}, null, null, "note", null, "note ASC");


        String[] str = new String[c.getCount()];


        if (c.getCount() > 0) {

            // Potentially instead of KEY_NOTE could use 1 for an index.
            iID = c.getColumnIndex(KEY_ROWID);


            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {


                str[++i] = c.getString(iID);

            }

        }
        c.close();
        return str;
    }






        query(DATABASE_TABLE, new String[]{KEY_CREATED_AT},     "note",         null,                   null,               null,           "note, created_at ASC");
Cursor	query(String table,       String[] columns,         String selection, String[] selectionArgs, String groupBy,  String having,           String orderBy,                 String limit)

Query the given table, returning a Cursor over the result set.


Cursor	query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignal cancellationSignal)

Query the given URL, returning a Cursor over the result set.


Cursor	query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)

Query the given table, returning a Cursor over the result set.


Cursor	query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)

Query the given URL, returning a Cursor over the result set.






    // String for list
    public String MinViewDate() {

        int iDate;


        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_CREATED_AT}, null, null, "note", null, "created_at ASC");


        //    Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_CREATED_AT}, null, null, "note", null, "created_at, ASC");

        //    Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_CREATED_AT}, null, null, null, null, "note, created_at ASC");

        String result = "";


        if (c.getCount() > 0) {

            iDate = c.getColumnIndex(KEY_CREATED_AT);

            try {

                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {


                    Log.d("MinNote ", " for Looop.");


                    result = result + c.getString(iDate) + "\n";
                }

                c.close();
                return result;

            } catch (Exception e) {

                e.printStackTrace();
                e.printStackTrace();
                e.printStackTrace();

            }
        }
        c.close();
        return null;
    }

    // String for list ID
    public String MinViewID() {

        int iID;


        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID}, null, null, "note", null, "created_at ASC");


        //     Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_ROWID}, null, null, "note", null, "created_at DESC");


        String result = "";

        if (c.getCount() > 0) {

            iID = c.getColumnIndex(KEY_ROWID);

            try {

                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {


                    Log.d("MinNote ", " for Looop.");


                    result = result + c.getString(iID) + "\n";
                }

                c.close();
                return result;

            } catch (Exception e) {

                e.printStackTrace();
                e.printStackTrace();
                e.printStackTrace();

            }
        }
        c.close();
        return null;
    }


    String[] value = null, note = null, date = null;

    public void DeleteLastThirtyUpdate() {

        Log.d("DeleteLastpdateOut", "DeleteLastyUpdateOut");

        boolean isGreater = false;

        DateTime firstCntrDate, secondCntrDate;

        int numDays, ko = 0;

        Log.d("DeleteLastThpdateOut", "2");

        Cursor c = dbDatabase.query(DATABASE_TABLE, new String[]{KEY_NOTE, KEY_VALUE, KEY_CREATED_AT},
                null, null, null, null, "created_at ASC");

        Log.d("DeleteLasyUpdateOut", "3");

        int iNote = c.getColumnIndex(KEY_NOTE);
        int iValue = c.getColumnIndex(KEY_VALUE);
        int iDate = c.getColumnIndex(KEY_CREATED_AT);

        String temp, tmp, temp_m, temp_d, temp_y, firstNot;

        Log.d("DeleteateOut", "4");


        if (c.getCount() > 3) {


            value = new String[c.getCount()];

            Log.d("DeleteLyUpdateOut", "45");

            note = new String[c.getCount()];

            Log.d("DeleteLayUpdateOut", "445");

            date = new String[c.getCount()];

            Log.d("DeleteLapdateOut", "4445");

            c.moveToFirst();

            Log.d("DeleteLartyUpdateOut", "44445");

            firstNot = c.getString(iDate);

            Log.d("DeleteLrtyUpdateOut", "444445");

            firstNot = firstNot.replace("-", " ");

            Log.d("DeleteLatyUpdateOut", "444444445");

            temp_m = firstNot.substring(5, 7);
            temp_d = firstNot.substring(8, 10);
            temp_y = firstNot.substring(0, 4);

            Log.d("DeleteLastpdateOut", "4444444444445");

            firstCntrDate = DateTime.forDateOnly(Integer.valueOf(temp_y),
                    Integer.valueOf(temp_m), Integer.valueOf(temp_d));

            Log.d("firstCntrDate", firstCntrDate.format("MM-DD-YYYY"));

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                temp = c.getString(iDate);

                tmp = temp.replace("-", " ");


                temp_m = tmp.substring(5, 7);
                temp_d = tmp.substring(8, 10);
                temp_y = tmp.substring(0, 4);


                Log.d("DeleteLastThateOut", "6");


                secondCntrDate = DateTime.forDateOnly(Integer.valueOf(temp_y),
                        Integer.valueOf(temp_m), Integer.valueOf(temp_d));


                Log.d("secondCntrDate", secondCntrDate.format("MM-DD-YYYY"));


                numDays = firstCntrDate.numDaysFrom(secondCntrDate);

                Log.e("ND " + String.valueOf(numDays), String.valueOf(numDays));

                if (numDays > 25) {

                    Log.d("DeleteLaUpdateOut", String.valueOf(numDays));

                    isGreater = true;

                    note[++ko] = c.getString(iNote);
                    value[ko] = c.getString(iValue);
                    date[ko] = c.getString(iDate);

                    Log.d("note[ko]", note[ko]);

                    Log.d("value[ko]", value[ko]);

                    Log.d("date[ko]", date[ko]);

                    // update by storing into a temp string array for all notes
                    // values and dates.

                }
            }

            Log.d("DeleteLastUpdateOut", "7");

            if (isGreater) {

                dbContext.deleteDatabase(DATABASE_NAME);

                open();

                for (int i = 1; i <= ko; i++) {

                    Log.d("note[ko]", note[i]);

                    Log.d("value[ko]", value[i]);

                    Log.d("date[ko]", date[i]);

                    createEntryThirty(note[i], value[i], date[i]);
                }

            } else if (isGreater == false) {

            }


        }
        c.close();
    }


    public long createEntryThirty(String note, String value, String date) {

        ContentValues cv = new ContentValues();

        cv.put(KEY_NOTE, note);
        cv.put(KEY_VALUE, value);
        cv.put(KEY_CREATED_AT, date);

        return dbDatabase.insert(DATABASE_TABLE, null, cv);

    }*/


}
